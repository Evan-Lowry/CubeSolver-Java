import javax.swing.JFrame;
import java.awt.*;

public class Main {

    public static CubeUI gamePanel;
    public static Cube cube;
    public static void main(String[] args) {
        cube = new Cube();
        gamePanel = new CubeUI();
        cube.performMoves("U R D L B U' R' L"); // example scramble
    }

    public static void solve() {
        CubeSolver solver = new CubeSolver();
        System.out.println("Starting to solve the cube...");
        System.out.println(solver.solveAnyCube(cube, 8) ? "Cube solved!" : "Could not solve the cube within the given depth.");
        System.out.println("Total states explored: " + solver.getStateCounter());
        System.out.println("Moves taken: " + solver.getMoves());

        CubeUI.refresh();
    }

    public static void refreshUI() {
        gamePanel.refresh();
    }

    public static void reset() {
    }
}