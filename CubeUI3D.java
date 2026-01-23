// ========================================
// AI GENERATED CODE - CubeUI3D.java
// Created: January 22, 2026
// Purpose: Alternative 3D UI for the Rubik's Cube solver
// Displays cube in isometric 3D view with on-screen message log
// This is an alternative to the original CubeUI.java (which is preserved)
// ========================================

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class CubeUI3D extends JPanel {
    private static Cube cube;
    private static JFrame frame;
    
    // AI GENERATED: 3D drawing object
    static Drawing3D drawing3D = new Drawing3D();
    
    // Window dimensions optimized for 3D view + message panel
    // AI GENERATED: Increased window size for bigger cube and wider message log
    static final int WINDOW_HEIGHT = 650;
    static final int WINDOW_WIDTH = 1000;
    
    // AI GENERATED: KeyHandler for input (reuses existing one)
    static KeyHandler3D keyH = new KeyHandler3D();
    
    public CubeUI3D() {
        // AI GENERATED FIX: Try to get cube from Main3D first, then fall back to Main
        if (Main3D.cube != null) {
            cube = Main3D.cube;
        } else if (Main.cube != null) {
            cube = Main.cube;
        }
        
        // Set up window properties
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setBackground(new Color(20, 20, 20)); // Dark background
        this.setDoubleBuffered(true);
        
        // Set up input listeners
        this.addKeyListener(keyH);
        this.setFocusable(true);
        this.setVisible(true);
        this.setFocusTraversalKeysEnabled(false);
        
        // Initialize message log
        MessageLog.getInstance().clear();
        MessageLog.getInstance().logInfo("3D Cube UI initialized");
        MessageLog.getInstance().logInfo("Press SPACE to solve the cube");
        
        showWindow(cube);
    }
    
    public static void refresh() {
        if (frame != null) {
            frame.repaint();
        }
    }
    
    public static void showWindow(Cube cube) {
        SwingUtilities.invokeLater(() -> {
            if (frame == null) {
                CubeUI3D ui = new CubeUI3D();
                frame = new JFrame("Cube Solver - 3D View (AI Generated)");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setContentPane(ui);
                frame.pack();
                frame.setLocation(100, 50);
                frame.setVisible(true);
                frame.setResizable(false);
            } else {
                CubeUI3D.cube = cube;
                frame.repaint();
            }
        });
    }
    
    // AI GENERATED: Main drawing method
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        // Draw the 3D cube and UI elements
        drawing3D.draw(g2, WINDOW_WIDTH, WINDOW_HEIGHT);
        
        g2.dispose();
    }
    
    public static byte[] getCube() {
        if (cube == null) {
            return null;
        }
        return cube.getCube();
    }
    
    public static void performMoves(String move) {
        cube.performMoves(move);
        // AI GENERATED ENHANCEMENT: Append moves to same line
        MessageLog.getInstance().logMove(move);
        refresh();
    }
    
    public static void exit() {
        MessageLog.getInstance().logInfo("Exiting...");
        System.exit(0);
    }
    
    // AI GENERATED: Method to add messages to the log
    public static void logMessage(String message) {
        MessageLog.getInstance().addMessage(message);
        refresh();
    }
}
