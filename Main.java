import javax.swing.JFrame;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Cube cube = new Cube();
        CubeUI gamePanel = new CubeUI(cube);
        CubeSolver solver = new CubeSolver(cube);
        // solver.solveCube();
    }
}