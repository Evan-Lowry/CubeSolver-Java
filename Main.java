import javax.swing.JFrame;
import java.awt.*;

public class Main {

    public static CubeUI gamePanel;
    public static Cube cube;
    public static CubeSolver solver;
    public static void main(String[] args) {
        cube = new Cube();
        gamePanel = new CubeUI();
        cube.performMoves("L F' L' U' L U F U' L'"); // example scramble
    }

    public static void solve() {
        solver = new CubeSolver();
        System.out.println("Starting to solve the cube...");
        long startTime = System.currentTimeMillis();
        boolean solved = solver.solveAnyCube(cube, 10);
        long endTime = System.currentTimeMillis();
        System.out.println("Solving took " + (endTime - startTime)/1000.0 + " s");
        if (solved) {
            System.out.println("Total states explored: " + solver.getStateCounter());
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
}