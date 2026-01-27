import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Heuristic {
    private static final int depth = 8; // number of corners
    private static final int edgeDepth = 12; // number of edges
    // Lookup tables for corner and edge positions
    private ArrayList<byte[]> cubeCornerStates;
    private HashMap<Integer, Integer> cornerPositionLookup;
    private HashMap<Integer, Integer> cornerOrientationLookup;
    private Cube cube;
    private HashMap<String, Integer> cornerPositionMap;
    private String cornerPositionLookupFilename = "corner_position_states_lookup.csv";
    private String cornerOrientationLookupFilename = "corner_orientation_states_lookup.csv";
    private String edgeOrientationLookupFilename = "edge_orientation_states_lookup.csv";

    // Precompute heuristic tables for corners and edges
    public void precomputeTables() {
        precomputeCornerPositionLookup();
        precomputeCornerLookup();
        precomputeEdgeOrientationLookup();
    }

    public void precomputeCornerPositionLookup() {
        // Precompute corner position states
        ArrayList<byte[]> cornerPositionStates = new ArrayList<byte[]>();
        byte[] start = new byte[8];
        start[0] = 0;
        cornerPositionStates.add(start);
        cornerPositionStates = generateCornerPositions(cornerPositionStates, 1);
        try { writeStatesToCsvFile(cornerPositionStates, cornerPositionLookupFilename); }
        catch (IOException e) { e.printStackTrace(); }
        System.out.println("Precomputed corner states:");
        System.out.println("Total corner states: " + cornerPositionStates.size());
    }

    public void precomputeCornerLookup() {
        // Precompute corner orientation states
        ArrayList<byte[]> cornerOrientationStates = new ArrayList<byte[]>();
        byte[] startOrientation = new byte[8];
        startOrientation[0] = 0;
        cornerOrientationStates.add(startOrientation);
        cornerOrientationStates = generateCornerOrientations(cornerOrientationStates, 1);
        try { writeStatesToCsvFile(cornerOrientationStates, cornerOrientationLookupFilename); }
        catch (IOException e) { e.printStackTrace(); }
        System.out.println("Total corner orientation states: " + cornerOrientationStates.size());
    }

    public void precomputeEdgeOrientationLookup() {
        // Precompute edge orientation states
        ArrayList<byte[]> edgeOrientationStates = new ArrayList<byte[]>();
        byte[] startOrientation = new byte[12];
        startOrientation[0] = 0;
        edgeOrientationStates.add(startOrientation);
        edgeOrientationStates = generateEdgeOrientations(edgeOrientationStates, 1);
        try { writeStatesToCsvFile(edgeOrientationStates, edgeOrientationLookupFilename); }
        catch (IOException e) { e.printStackTrace(); }
        System.out.println("Total edge orientation states: " + edgeOrientationStates.size());
    }
    
    // Load the precomputed corner lookup table from file
    public void loadCornerLookup() throws IOException {
        generateCornerPositionLookup(cornerPositionLookupFilename);
    }
    
    // Get the solution length for a given corner state key
    public int getCornerSolutionLength(int key) {
        if (cornerPositionLookup == null) return -1;
        Integer result = cornerPositionLookup.get(key);
        return result != null ? result : -1;
    }

    public int getCornerSolutionLength(byte[] key) {
        return getCornerSolutionLength(CubeKey.encodePositionByte(key));
    }

    public void loadCornerOrientationLookup() throws IOException {
        generateCornerOrientationLookup(cornerOrientationLookupFilename);
    }

    public int getCornerOrientationSolutionLength(int key) {
        if (cornerOrientationLookup == null) return -1;
        Integer result = cornerOrientationLookup.get(key);
        return result != null ? result : -1;
    }

    public int getCornerOrientationSolutionLength(byte[] key) {
        return getCornerOrientationSolutionLength(CubeKey.encodeOrientation(key));
    }
    
    private void generateCornerPositionLookup(String filename) throws IOException {
        cornerPositionLookup = new HashMap<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int count = 0;
            
            while ((line = br.readLine()) != null) {
                // Skip empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }
                
                // Split by comma: format is "key,solutionLength"
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    try {
                        int key = Integer.parseInt(parts[0].trim());
                        int solutionLength = Integer.parseInt(parts[1].trim());
                        cornerPositionLookup.put(key, solutionLength);
                        count++;
                        
                        // Print progress every 1 million entries
                        if (count % 1_000_000 == 0) {
                            System.out.println("Loaded " + count + " corner states into lookup table");
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Error parsing line: " + line);
                    }
                }
            }
            
            System.out.println("Finished loading corner lookup table: " + count + " entries");
        }
    }

    private void generateCornerOrientationLookup(String filename) throws IOException {
        cornerOrientationLookup = new HashMap<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int count = 0;
            
            while ((line = br.readLine()) != null) {
                // Skip empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }
                
                // Split by comma: format is "key,solutionLength"
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    try {
                        int key = Integer.parseInt(parts[0].trim());
                        int solutionLength = Integer.parseInt(parts[1].trim());
                        cornerOrientationLookup.put(key, solutionLength);
                        count++;
                        
                        // Print progress every 1 million entries
                        if (count % 1_000_000 == 0) {
                            System.out.println("Loaded " + count + " corner orientation states into lookup table");
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Error parsing line: " + line);
                    }
                }
            }
            
            System.out.println("Finished loading corner orientation lookup table: " + count + " entries");
        }
    }

    private long[][] generateStatesToKeys(ArrayList<byte[]> states) {
        final int rowLen = depth * 3;
        final int n = states.size();
        long[][] keys = new long[1][2];
        byte[] state;
        byte[] stickerArray = new byte[rowLen];
        for (int i = 0; i < n; i++) {
            if (i % 1000000 == 0) System.out.println(String.format("Processed %.2f%% of states.", (100.0 * i) / n));
            state = states.get(i);
            for (int j = 0; j < depth; j++) stickerArray[j] = 0; // reset sticker array
            for (int j = 0; j < depth; j++) {
                byte cornerIndex = state[j];
                byte cornerOrientation = state[j + depth];
                // Each corner has 3 stickers; determine their colors based on orientation
                stickerArray[j * 3] = getStickerColor(cornerIndex, cornerOrientation);
                stickerArray[j * 3 + 1] = getStickerColor(cornerIndex, (cornerOrientation + 1) % 3);
                stickerArray[j * 3 + 2] = getStickerColor(cornerIndex, (cornerOrientation + 2) % 3);
            }
            // Arrays.toString creates a readable, lossless representation of the stickerArray
            // keys[i] = Arrays.stream(stickerArray).mapToLong(b -> b & 0xFF).toArray();
        }
        return keys;
    }

    public void writeStatesToCsvFile(ArrayList<byte[]> states, String filename) throws IOException {
        final int rowLen = depth * 3;
        final int n = states.size();
        StringBuilder sb = new StringBuilder(rowLen * 3);
        Cube cube = new Cube();
        CubeSolver solver = new CubeSolver();
        int invalidStates = 0;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, StandardCharsets.UTF_8), 1 << 20)) {
            for (int i = 0; i < n; i++) {
                if (i % 100 == 0) System.out.println(String.format("Processed %.2f%% of states.", (100.0 * i) / n));
                byte[] state = states.get(i);
                // byte[] cubeArray = CubeKey.indexOrientatedArrayToCube(state);
                // cube.setCube(cubeArray);
                // int solutionLength = 15; // default value for invalid states
                // if (solver.solveCornerOrientation(cube, 14)) {
                //     solutionLength = solver.getNumberOfMoves();
                //     solver.reset();
                // } else {
                //     invalidStates++;
                // }
                sb.setLength(0);
                sb.append(CubeKey.encodeByteArrayToLong(state));
                // sb.append(",");
                // sb.append(solutionLength);
                bw.write(sb.toString());
                bw.newLine();
            }
            System.out.println("Processed 100.00% of states.");
            bw.flush();
        }
        catch (IOException e) { e.printStackTrace(); }
    }

    private byte getStickerColor(byte cornerIndex, int stickerPosition) {
        // Define the color mapping for each corner and its stickers
        // This is a placeholder implementation; actual colors depend on cube design
        byte[][] cornerColors = {
            {0, 1, 4}, // Corner 0 colors
            {0, 4, 3}, // Corner 1 colors
            {0, 3, 2}, // Corner 2 colors
            {0, 2, 1}, // Corner 3 colors
            {5, 4, 1}, // Corner 4 colors
            {5, 3, 4}, // Corner 5 colors
            {5, 2, 3}, // Corner 6 colors
            {5, 1, 2}  // Corner 7 colors
        };
        return cornerColors[cornerIndex][stickerPosition];
    }

    private ArrayList<byte[]> generateCornerPositions(ArrayList<byte[]> currentStates, int index) {

        ArrayList<byte[]> newStates = new ArrayList<byte[]>();
        for (byte[] byteStack : currentStates) {
            byte[] tempStack;

            for (int i = 0; i < index + 1; i++) {
                tempStack = byteStack.clone();
                insert(tempStack,i, (byte) index);
                newStates.add(tempStack);
            }
        }

        if (index == depth - 1) return newStates;

        return generateCornerPositions(newStates, index + 1);
    }

    private ArrayList<byte[]> generateCornerOrientations(ArrayList<byte[]> currentStates, int index) {
        ArrayList<byte[]> newStates = new ArrayList<byte[]>();
        for (byte[] byteStack : currentStates) {
            byte[] tempStack;

            for (int i = 0; i < 3; i++) {
                tempStack = byteStack.clone();
                tempStack[index - 1] = (byte) i;
                newStates.add(tempStack);
            }
        }
        if (index == depth - 1) {
            for (byte[] state : newStates) {
                int sumOfOrientations = 0;
                for (int i = 0; i < depth - 1; i++) {
                    sumOfOrientations += state[i];
                }
                state[depth - 1] = (byte) ((3 - (sumOfOrientations % 3)) % 3);
            }
            return newStates;
        }
        return generateCornerOrientations(newStates, index + 1);
    }

    private ArrayList<byte[]> generateEdgeOrientations(ArrayList<byte[]> currentStates, int index) {
        ArrayList<byte[]> newStates = new ArrayList<byte[]>();
        for (byte[] byteStack : currentStates) {
            byte[] tempStack;
            for (int i = 0; i < 2; i++) {
                tempStack = byteStack.clone();
                tempStack[index - 1] = (byte) i;
                newStates.add(tempStack);
            }
        }
        if (index == edgeDepth - 1) {
            for (byte[] state : newStates) {
                int sumOfOrientations = 0;
                for (int i = 0; i < depth - 1; i++) {
                    sumOfOrientations += state[i];
                }
                state[edgeDepth - 1] = (byte) ((2 - (sumOfOrientations % 2)) % 2);
            }
            return newStates;
        }
        return generateEdgeOrientations(newStates, index + 1);
    }

    private static int factorial(int n) {
        if (n <= 1) return 1;
        return n * factorial(n - 1);
    }
    
    // must have index within bounds of array
    private void insert(byte[] array, int index, byte value) {
        if (index < 0 || index >= array.length) throw new IndexOutOfBoundsException(index);
        for (int i = array.length - 1; i > index; i--) {
            array[i] = array[i - 1];
        }
        array[index] = value;
    }

    private ArrayList<byte[]> generateCornerRotations(ArrayList<byte[]> currentStates) {
        int pow3 = 1;
        for (int k = 0; k < depth - 1; k++) pow3 *= 3; // integer power for the first depth-1 corners

        ArrayList<byte[]> newStates = new ArrayList<>(currentStates.size() * pow3);

        for (byte[] state : currentStates) {
            for (int i = 0; i < pow3; i++) {
                byte[] tempState = state.clone();
                int num = i;
                int sum = 0;
                // set orientations for the first depth-1 corners
                for (int j = 0; j < depth - 1; j++) {
                    int ori = num % 3;
                    tempState[j + depth] = (byte) ori;
                    sum += ori;
                    num /= 3;
                }
                // force the last corner orientation so total sum % 3 == 0
                tempState[(depth - 1) + depth] = (byte) ((3 - (sum % 3)) % 3);
                newStates.add(tempState);
            }
        }
        return newStates;
    }

}