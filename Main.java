import javax.swing.JFrame;
import java.awt.*;

public class Main {

    public static CubeUI gamePanel;
    public static Cube cube;
    public static CubeSolver solver;
    public static void main(String[] args) {
        cube = new Cube();
        gamePanel = new CubeUI();
        cube.performMoves("R"); // example scramble
    }

    public static void testCubeSpeed() {
        Cube testCube = new Cube();
        long numberOfStates = (long) Math.pow(10, 2); // approximately 4.85 million

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < numberOfStates; i++) {
            testCube.applyMove(i % 12);
        }
        long endTime = System.currentTimeMillis();

        System.out.println("Applied " + numberOfStates + " moves in " + (endTime - startTime)/1000.0 + " s");
    }

    public static void solve() {
        solver = new CubeSolver();
        System.out.println("Starting to solve the cube...");
        long startTime = System.currentTimeMillis();
        boolean solved = solver.solveAnyCube(cube, 10);
        long endTime = System.currentTimeMillis();
        System.out.println("Solving took " + (endTime - startTime)/1000.0 + " s");
        if (solved) {
            System.out.println("Total states explored: " + formatNumber(solver.getStateCounter()));
            System.out.println("Moves taken: " + solver.getMoves());
        } else {
            System.out.println("Could not solve the cube within the given depth.");
        }

        CubeUI.refresh();
    }

    public static void refreshUI() {
        gamePanel.refresh();
    }

    public static void reset() {
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
}