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
    private static JFrame frame;

    // creates a drawing object to handle all drawing of objects to screen
    static Drawing drawing = new Drawing();
    // used to store the resolution of the window measured as a percentage of 1920 x 1080
    static float fullscreen = (float) 0.5;
    // sets the resolution variables in accordance with the fullscreen variables
    static final int WINDOW_HEIGHT = (int)(550);
    static final int WINDOW_WIDTH = (int)(700);
    // static final int WINDOW_HEIGHT = (int)(fullscreen*956);
    // static final int WINDOW_WIDTH = (int)(fullscreen*1470);
    // creates a KeyHandler to read and store key inputs
    static KeyHandler keyH = new KeyHandler();

    public CubeUI() {
        cube = Main.cube;
        // sets size of window
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        // sets background color to black
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        // makes sure the key and mouse listeners can detect input
        this.addKeyListener(keyH);
        this.setFocusable(true);
        this.setVisible(true);
        this.setFocusTraversalKeysEnabled(false);
        showWindow(Main.cube);
    }

    public static void refresh() {
        if (frame != null) {
            frame.repaint();
        }
    }

    public static void showWindow(Cube cube) {
        SwingUtilities.invokeLater(() -> {
            if (frame == null) {
                CubeUI ui = new CubeUI();
                frame = new JFrame("Cube Solver");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setContentPane(ui);
                frame.pack();
                frame.setLocation(0, 0);
                frame.setVisible(true);
                frame.setResizable(false);
            } else {
                CubeUI.cube = cube;
                frame.repaint();
            }
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

    public static byte[] getCube() {
        return cube.getCube();
    }

    public static void performMoves(String move) {
        cube.performMoves(move);
        refresh();
    }

    public static void exit() {
        System.exit(0);
    }
}