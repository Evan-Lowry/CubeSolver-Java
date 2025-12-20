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
        if (keyChar == 'j') {
            CubeUI.performMoves("U");
        } else if (keyChar == 'f') {
            CubeUI.performMoves("U'");
        } else if (keyChar == 'r') {
            reset = true;
        } else if (keyChar == ' ') {
            spacePressed = true;
        } else if (keyChar == 'q') {
            CubeUI.exit();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
