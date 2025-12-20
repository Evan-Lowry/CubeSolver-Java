import javax.swing.JFrame;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Cube cube = new Cube();
        // sets the name of the window to Half Life 3 (But way worse)
        JFrame frame = new JFrame("Half Life 3");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setResizable(false);
        // creates the GamePanel to run game logic and game loop through
        CubeUI gamePanel = new CubeUI(cube);
        // removes top bar
        // frame.setUndecorated(true);
        // adds the GamePanel to the frame so you can see whats happening
        frame.add(gamePanel);
        frame.requestFocusInWindow();
        frame.setVisible(true);
        frame.setFocusTraversalKeysEnabled(false);
        frame.pack();

        cube.performMoves("R U R' U' F2 D L B2 R F' L2 D' B U2");
    }
}