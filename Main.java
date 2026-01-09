import javax.swing.JFrame;
import java.awt.*;

public class Main {

    public static CubeUI gamePanel;
    // public static Cube cube;
    public static Cube cube;
    public static CubeSolver solver;
    public static Heuristic heuristic;
    public static int maxDepth = 10;
    public static void main(String[] args) {
        // cube = new Cube();
        // gamePanel = new CubeUI();
        // cube.performMoves(" "); // example scramble

        cube = new Cube();
        gamePanel = new CubeUI();
        // cube.performMoves("L2 F2 D2 U' R2 U F2 D2 U' R2 U' B' L2 R' B' D2 U B2");
        cube.performMoves("U2 B2 F R2 B L2 R2 D2 B'");
    }

    public static void solveCubeCorner() {
        solver = new CubeSolver();
        System.out.println("Starting to solve the cube corners...");
        long startTime = System.currentTimeMillis();
        boolean solved = solver.solveAnyCubeCorners(cube, 11);
        long endTime = System.currentTimeMillis();
        System.out.print("Solving took " + formatTime(endTime - startTime));
        if (solved) {
            System.out.println("Total states explored: " + formatNumber(solver.getStateCounter()));
            System.out.println("Moves taken: " + solver.getMoves());
        } else {
            System.out.println("Could not solve the cube within the given depth.");
        }
        System.out.println();

        CubeCornersUI.refresh();
    }

    public static void solve() {
        new Thread(() -> {
            solver = new CubeSolver();
            System.out.println("Starting to solve the cube...");
            long startTime = System.currentTimeMillis();

            boolean solved = solver.solveCube(cube, maxDepth);

            // Update UI and print results on the EDT
            javax.swing.SwingUtilities.invokeLater(() -> {
                CubeUI.refresh();
            });

            solved = solver.solvePhase2(cube, maxDepth);

            long endTime = System.currentTimeMillis();
            String timeStr = formatTime(endTime - startTime);
            long states = solver.getStateCounter();
            String moves = solver.getMoves();

            System.out.print("Solving took " + timeStr);
            if (solved) {
                System.out.println("Total states explored: " + formatNumber(states));
                System.out.println("Moves taken: " + moves);
            } else {
                System.out.println("Could not solve the cube within the given depth.");
            }

            System.out.println();

            CubeUI.refresh();

        }, "solver-thread").start();
    }

    private static String formatNumber(long number) {
        long abs = Math.abs(number);
        if (abs < 1000) {
            return Long.toString(number);
        }
        // exp: 1 -> K, 2 -> M, 3 -> B, 4 -> T
        int exp = (int) (Math.log10(abs) / 3);
        if (exp < 1) exp = 1;
        if (exp > 4) exp = 4; // cap at T (trillion)

        char[] suffixes = {'K', 'M', 'B', 'T'};
        char suffix = suffixes[exp - 1];

        double scaled = (double) number / Math.pow(1000, exp);
        String num = String.format("%.1f", scaled);
        if (num.endsWith(".0")) {
            num = num.substring(0, num.length() - 2);
        }
        return num + suffix;
    }

    private static String formatTime(long timeInMillis) {
        long totalSeconds = timeInMillis / 1000;
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        long millis = timeInMillis % 1000;

        if (timeInMillis < 1000) {
            return String.format("Time taken: %d ms%n", millis);
        } else if (timeInMillis < 60_000) {
            return String.format("Time taken: %d sec, %d ms%n", totalSeconds, millis);
        } else {
            return String.format("Time taken: %d min, %d sec, %d ms%n", minutes, seconds, millis);
        }
    }

    public static void refreshUI() {
        gamePanel.refresh();
    }

    public static void reset() {
    }
}