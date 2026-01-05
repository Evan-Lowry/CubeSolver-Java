import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Heuristic {
    // Lookup tables for corner and edge positions
    private ArrayList<byte[]> cubeCornerStates;
    private long[][] cornerKeys; // store state keys as longs
    private static final int depth = 8; // number of corners
    private static final int factorialDepth = factorial(depth); // 8! possible corner positions
    private static final int pow3 = (int) Math.pow(3, depth - 1); // 3^7 possible corner orientations

    private CubeCorner cubeCorner;
    private HashMap<String, Integer> cornerPositionMap;

    public void setCube(CubeCorner cubeCorner) {
        this.cubeCorner = cubeCorner;
    }

    // Precompute heuristic tables for corners and edges
    public void precomputeTables() {
        ArrayList<byte[]> cornerStates = new ArrayList<byte[]>();
        byte[] start = new byte[depth * 2];
        start[0] = 0;
        cornerStates.add(start);
        cornerStates = generateCornerPositions(cornerStates, 1);
        cornerStates = generateCornerRotations(cornerStates);
    
        // try { writeStatesToBinaryFile(cornerStates, "corner_states.bin"); }
        try { writeStatesToCsvFile(cornerStates, "corner_states_testing.csv"); }
        catch (IOException e) { e.printStackTrace(); }
        System.out.println("Precomputed corner states:");
        System.out.println("Total corner states: " + cornerStates.size());
    }

    private String stateToString(byte[] state) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < state.length; i++) {
            if (i > 0) sb.append(", ");
            sb.append(state[i]);
        }
        sb.append("]");
        return sb.toString();
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

    public void writeStatesToBinaryFile(ArrayList<byte[]> states, String filename) throws IOException {
        final int rowLen = depth * 3;
        byte[] stickerArray = new byte[rowLen];

        // large buffer to reduce syscalls
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(filename), 1 << 20)) {
            final int n = states.size();
            for (int i = 0; i < n; i++) {
                if ((i % 1_000_000) == 0) System.out.println(String.format("Processed %.2f%% of states.", (100.0 * i) / n));
                byte[] state = states.get(i);
                // build sticker row into single buffer (reused)
                for (int j = 0; j < depth; j++) {
                    byte cornerIndex = state[j];
                    byte cornerOrientation = state[j + depth];
                    stickerArray[j * 3]     = getStickerColor(cornerIndex, cornerOrientation);
                    stickerArray[j * 3 + 1] = getStickerColor(cornerIndex, (cornerOrientation + 1) % 3);
                    stickerArray[j * 3 + 2] = getStickerColor(cornerIndex, (cornerOrientation + 2) % 3);
                }
                out.write(stickerArray); // write fixed-length row
            }
            System.out.println("Processed 100.00% of states.");
            out.flush();
        }
    }

    public void writeStatesToCsvFile(ArrayList<byte[]> states, String filename) throws IOException {
        final int rowLen = depth * 3;
        final int n = states.size();
        StringBuilder sb = new StringBuilder(rowLen * 3);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, StandardCharsets.UTF_8), 1 << 20)) {
            for (int i = 0; i < n; i++) {
                if ((i % 1_000_000) == 0) System.out.println(String.format("Processed %.2f%% of states.", (100.0 * i) / n));
                byte[] state = states.get(i);
                sb.setLength(0);
                for (int j = 0; j < depth; j++) {
                    byte cornerIndex = state[j];
                    byte cornerOrientation = state[j + depth];
                    // append three sticker values
                    if (j > 0) sb.append(',');
                    sb.append(getStickerColor(cornerIndex, cornerOrientation)).append(',');
                    sb.append(getStickerColor(cornerIndex, (cornerOrientation + 1) % 3)).append(',');
                    sb.append(getStickerColor(cornerIndex, (cornerOrientation + 2) % 3));
                }
                bw.write(sb.toString());
                bw.newLine();
            }
            System.out.println("Processed 100.00% of states.");
            bw.flush();
        }
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

    private void generateCornerLookup(CubeCorner cubeCorner, int depth) {
        // Recursive or iterative logic to generate corner permutations

        // Base case: all corners generated
        if (depth == 8) return;

        for (int i = 0; i < 12; i++) {
            cubeCorner.applyMove(i);
            // Generate permutations for the current corner
            cubeCorner.setCornerPosition(depth, i);
            generateCornerLookup(cubeCorner, depth + 1);


            cubeCorner.undoMove(i);
        }
    }

    private void createEdgeLookupTable() {
        // Logic to create a lookup table for edge positions
        for (int i = 0; i < 12; i++) {
            // Generate all possible edge permutations
            generateEdgePermutations(i);
        }
    }

    private void generateEdgePermutations(int index) {
        // Recursive or iterative logic to generate edge permutations
        if (index == 12) {
            // Base case: all edges generated
            return;
        }
        // Generate permutations for the current edge
        generateEdgePermutations(index + 1);
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
}
