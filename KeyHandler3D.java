// ========================================
// AI GENERATED CODE - KeyHandler3D.java
// Created: January 22, 2026
// Purpose: Key handler for the 3D UI version
// Routes commands to CubeUI3D instead of CubeUI
// ========================================

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler3D implements KeyListener {
    
    public boolean spacePressed, shiftPressed, reset, fullscreen;
    
    public KeyHandler3D() {
        spacePressed = false;
        fullscreen = false;
        shiftPressed = false;
        reset = false;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        char keyChar = Character.toLowerCase(e.getKeyChar());
        
        switch (keyChar) {
            case 'j' -> CubeUI3D.performMoves("U");
            case 'f' -> CubeUI3D.performMoves("U'");
            case 'h' -> CubeUI3D.performMoves("F");
            case 'g' -> CubeUI3D.performMoves("F'");
            case 'k' -> CubeUI3D.performMoves("R'");
            case 'd' -> CubeUI3D.performMoves("L");
            case 'l' -> CubeUI3D.performMoves("D'");
            case 's' -> CubeUI3D.performMoves("D");
            case 'i' -> CubeUI3D.performMoves("R");
            case 'e' -> CubeUI3D.performMoves("L'");
            case 'o' -> CubeUI3D.performMoves("B'");
            case 'w' -> CubeUI3D.performMoves("B");
            case 'r' -> {
                Main.reset();
                MessageLog.getInstance().logInfo("Cube reset");
            }
            case ' ' -> {
                MessageLog.getInstance().logSolver("Starting solve...");
                Main3D.solve3D();
            }
            case 'q' -> CubeUI3D.exit();
            case '1' -> CubeUI3D.performMoves("U2");
            case '2' -> CubeUI3D.performMoves("D2");
            case '3' -> CubeUI3D.performMoves("R2");
            case '4' -> CubeUI3D.performMoves("L2");
            case '5' -> CubeUI3D.performMoves("F2");
            case '6' -> CubeUI3D.performMoves("B2");
            default -> {}
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
    }
}
