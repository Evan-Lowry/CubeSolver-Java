import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class CubeUI extends JPanel {
    private static Cube cube;

    // creates a drawing object to handle all drawing of objects to screen
    static Drawing drawing = new Drawing();
    // used to store the resolution of the window measured as a percentage of 1920 x 1080
    static float fullscreen = (float) 1;
    // sets the resolution variables in accordance with the fullscreen variables
    static int windowHeight = (int)(fullscreen*956);
    static int windowWidth = (int)(fullscreen*1470);
    // creates a KeyHandler to read and store key inputs
    static KeyHandler keyH = new KeyHandler();

    public CubeUI(Cube cube) {
        this.cube = cube;
        // sets size of window
        this.setPreferredSize(new Dimension(windowWidth, windowHeight));
        // sets background color to black
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        // makes sure the key and mouse listeners can detect input
        this.addKeyListener(keyH);
        this.setFocusable(true);
        this.setVisible(true);
        this.setFocusTraversalKeysEnabled(false);
    }

    private static void refresh() {
        SwingUtilities.invokeLater(() -> {
            CubeUI ui = new CubeUI(cube);
            ui.repaint();
            ui.setVisible(true);
        });
    }

    public static void showWindow(Cube cube) {
        SwingUtilities.invokeLater(() -> {
            CubeUI ui = new CubeUI(cube);
            ui.setVisible(true);
        });
    }

    // the actual drawing method
    public void paintComponent(Graphics g) {
        // adds in a graphics object to draw with
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // calls drawing object to draw everything to screen
        drawing.draw(g2);

        g2.dispose();
    }

    public static char[] getCube() {
        return cube.getCube();
    }

    public static void performMoves(String move) {
        cube.performMoves(move);
    }

    public static void exit() {
        System.exit(0);
    }
}