import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
            case '1' -> CubeUI.performMoves("U2");
            case '2' -> CubeUI.performMoves("D2");
            case '3' -> CubeUI.performMoves("R2");
            case '4' -> CubeUI.performMoves("L2");
            case '5' -> CubeUI.performMoves("F2");
            case '6' -> CubeUI.performMoves("B2");
            default -> {}
        }

        // switch (keyChar) {
        //     case 'j' -> CubeCornersUI.applyMove(0);
        //     case 'f' -> CubeCornersUI.applyMove(1);
        //     case 'h' -> CubeCornersUI.applyMove(8);
        //     case 'g' -> CubeCornersUI.applyMove(9);
        //     case 'k' -> CubeCornersUI.applyMove(7);
        //     case 'd' -> CubeCornersUI.applyMove(4);
        //     case 'l' -> CubeCornersUI.applyMove(3);
        //     case 's' -> CubeCornersUI.applyMove(2);
        //     case 'i' -> CubeCornersUI.applyMove(6);
        //     case 'e' -> CubeCornersUI.applyMove(5);
        //     case 'o' -> CubeCornersUI.applyMove(11);
        //     case 'w' -> CubeCornersUI.applyMove(10);
        //     case 'r' -> Main.reset();
        //     case ' ' -> Main.solveCubeCorner();
        //     case 'q' -> CubeCornersUI.exit();
        //     default -> {}
        // }
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
