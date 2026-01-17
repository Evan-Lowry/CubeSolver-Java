import java.util.ArrayList;

public class Testing {
    public static void main(String[] args) {
        Cube cube = new Cube();
        cube.performMoves("U R2 F B R B2 R U2 L B2 R U' D' R2 F R' L B2 U2 F2");
        String key = CubeKey.encode(cube.getCube());
        System.out.println("Encoded key: " + key);
    }

    private static void testHeuristic() {
        Heuristic heuristic = new Heuristic();
        long startTime = System.currentTimeMillis();
        heuristic.precomputeTables();
        long endTime = System.currentTimeMillis();
        System.out.println("Heuristic precomputation took " + formatTime(endTime - startTime));
    }

    private static void testCubeCorners() {
        CubeCorner testCube = new CubeCorner();
        System.out.println(testCube.isSolved() ? "Cube is solved!" : "Cube is not solved.");
        System.out.println("State:\n" + testCube);
    }

    private static void testSolvingCubeCorners() {
        CubeCorner testCube = new CubeCorner();
        testCube.performMoves("F");
        CubeSolver cubeSolver = new CubeSolver();
        System.out.println("Starting to solve the cube...");
        long startTime = System.currentTimeMillis();
        boolean solved = cubeSolver.solveCubeCorners(testCube, 2);
        long endTime = System.currentTimeMillis();
        System.out.println("Solving took " + formatTime(endTime - startTime));
        if (solved) {
            System.out.println("Total states explored: " + formatNumber(cubeSolver.getStateCounter()));
            System.out.println("Moves taken: " + cubeSolver.getMoves());
        } else {
            System.out.println("Could not solve the cube within the given depth.");
        }
    }

    private static String formatNumber(long number) {
        if (number < 1000) {
            return Long.toString(number);
        }
        int exp = (int) (Math.log10(number) / 3);
        char suffix = "kMGTPE".charAt(exp - 1);
        double scaled = number / Math.pow(1000, exp);
        return String.format("%.1f%c", scaled, suffix);
    }

    private static String formatTime(long timeInMillis) {
        long seconds = timeInMillis / 1000;
        long minutes = seconds / 60;
        seconds %= 60;
        long millis = timeInMillis % 1000;
        return String.format("Time taken: %d min, %d sec, %d ms%n", minutes, seconds, millis);
    }

    private static void testCubeSpeed(int n) {
        Cube testCube = new Cube();

        long numberOfStates = (long) (Math.pow(10, n));

        long startTime = System.currentTimeMillis();
        for (long i = 0; i < numberOfStates; i++) {
            testCube.applyMove(8);
            // testCube.applyMove(1);
            // testCube.applyMove(2);
            // testCube.applyMove(3);
            // testCube.applyMove(4);
            // testCube.applyMove(5);
            // testCube.applyMove(6);
            // testCube.applyMove(7);
            // testCube.applyMove(8);
            // testCube.applyMove(9);
            // testCube.applyMove(10);
            // testCube.applyMove(11);
        }
        long endTime = System.currentTimeMillis();

        System.out.println("Applied 10^" + n + " moves in " + (endTime - startTime)/1000.0 + " s");
    }
}
