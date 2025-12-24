import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class KeyHandler implements KeyListener{

    // stores all input
    // stored as boolean so you can hold down keys
    public boolean spacePressed, shiftPressed, reset, fullscreen;

    public KeyHandler() {
        spacePressed = false;
        fullscreen = false;
        shiftPressed = false;
        reset = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

        // used for the Microsoft virtual desktop which only detects key typed

        char keyChar = Character.toLowerCase(e.getKeyChar());

        switch (keyChar) {
            case 'j' -> CubeUI.performMoves("U");
            case 'f' -> CubeUI.performMoves("U'");
            case 'h' -> CubeUI.performMoves("F");
            case 'g' -> CubeUI.performMoves("F'");
            case 'k' -> CubeUI.performMoves("R'");
            case 'd' -> CubeUI.performMoves("L");
            case 'l' -> CubeUI.performMoves("D'");
            case 's' -> CubeUI.performMoves("D");
            case 'i' -> CubeUI.performMoves("R");
            case 'e' -> CubeUI.performMoves("L'");
            case 'o' -> CubeUI.performMoves("B'");
            case 'w' -> CubeUI.performMoves("B");
            case 'r' -> Main.reset();
            case ' ' -> Main.solve();
            case 'q' -> CubeUI.exit();
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
