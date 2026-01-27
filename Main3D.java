// ========================================
// AI GENERATED CODE - Main3D.java
// Created: January 22, 2026
// Purpose: Alternative main class to launch the 3D UI version
// Usage: Run this instead of Main.java to use the 3D visualization
// ========================================

import java.io.IOException;

public class Main3D {

    public static CubeUI3D gamePanel3D;
    public static Cube cube;
    public static CubeSolver solver;
    public static Heuristic heuristic;
    public static int maxDepth = 15;
    
    public static void main(String[] args) {
        cube = new Cube();
        gamePanel3D = new CubeUI3D();
        heuristic = new Heuristic();
        try {
            heuristic.loadCornerLookup();
            heuristic.loadCornerOrientationLookup();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Example scramble
        String scramble = "F2 D2 U' R2 U F2 D2 U' R2 U' B' L2 R' B' D2 U B2";
        // String scramble = "U R2 F B R B2 R U2 L B2 R U' D' R2 F R' L B2 U2 F2"; // Superflip
        cube.performMoves(scramble);
        MessageLog.getInstance().logInfo("Scramble: " + scramble);
        
        // Log initial state to message log
        MessageLog.getInstance().logInfo("Cube scrambled and ready!");
        MessageLog.getInstance().logInfo("Controls available on screen");
        
        CubeUI3D.refresh();
    }

    // AI GENERATED: This method is commented out because solveAnyCubeCorners doesn't exist in CubeSolver
    // Uncomment and update if that method is added to CubeSolver in the future
    /*
    public static void solveCubeCorner() {
        solver = new CubeSolver();
        MessageLog.getInstance().logSolver("Starting to solve cube corners...");
        long startTime = System.currentTimeMillis();
        boolean solved = solver.solveAnyCubeCorners(cube, 11);
        long endTime = System.currentTimeMillis();
        
        String timeStr = formatTime(endTime - startTime);
        MessageLog.getInstance().logSolver(timeStr.replace("Time taken: ", "").replace("\n", ""));
        
        if (solved) {
            MessageLog.getInstance().logSuccess("States: " + formatNumber(solver.getStateCounter()));
            MessageLog.getInstance().logSuccess("Moves: " + solver.getMoves());
        } else {
            MessageLog.getInstance().logError("Could not solve within depth");
        }

        CubeUI3D.refresh();
    }
    */

    public static void solve3D() {
        new Thread(() -> {
            solver = new CubeSolver();
            MessageLog.getInstance().logSolver("Starting to solve the cube...");
            long startTime = System.currentTimeMillis();

            boolean solved = solver.solveCube(cube, maxDepth);

            javax.swing.SwingUtilities.invokeLater(() -> CubeUI3D.refresh());

            if (solved) {
                solved = solver.solvePhase2(cube, maxDepth);
            }

            long endTime = System.currentTimeMillis();
            String timeStr = formatTime(endTime - startTime);
            long states = solver.getStateCounter();
            String moves = solver.getMoves();

            MessageLog.getInstance().logSolver(timeStr.replace("Time taken: ", "").replace("\n", ""));
            if (solved) {
                MessageLog.getInstance().logSuccess("States explored: " + formatNumber(states));
                MessageLog.getInstance().logSuccess("Pruned states: " + formatNumber(solver.getDisqualifiedStates()));
                MessageLog.getInstance().logSuccess("Solution: " + moves);
            } else {
                MessageLog.getInstance().logError("Could not solve within depth " + maxDepth);
            }

            CubeUI3D.refresh();

        }, "solver-thread-3d").start();
    }

    private static String formatNumber(long number) {
        long abs = Math.abs(number);
        if (abs < 1000) {
            return Long.toString(number);
        }
        int exp = (int) (Math.log10(abs) / 3);
        if (exp < 1) exp = 1;
        if (exp > 4) exp = 4;

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
            return String.format("Time: %d ms", millis);
        } else if (timeInMillis < 60_000) {
            return String.format("Time: %d sec, %d ms", totalSeconds, millis);
        } else {
            return String.format("Time: %d min, %d sec, %d ms", minutes, seconds, millis);
        }
    }

    public static void refreshUI() {
        CubeUI3D.refresh();
    }

    public static void reset() {
        cube = new Cube();
        MessageLog.getInstance().logInfo("Cube reset to solved state");
        CubeUI3D.refresh();
    }
}
