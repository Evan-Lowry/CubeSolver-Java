import javax.swing.JFrame;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Cube cube = new Cube();
        CubeUI gamePanel = new CubeUI(cube);
        cube.performMoves("B B");

        CubeSolver solver = new CubeSolver();
        System.out.println("Starting to solve the cube...");
        System.out.println(solver.solveCube(cube, 2) ? "Cube solved!" : "Could not solve the cube within the given depth.");
        System.out.println("Finished solving the cube.");
        System.out.println("Total states explored: " + solver.getStateCounter());

        gamePanel.refresh();
    }
}