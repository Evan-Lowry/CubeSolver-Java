import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.Arrays;

import org.w3c.dom.css.Rect;

public class Drawing {

    public static final int CUBE_SIZE = 50;
    public static final int FACELET_SIZE = CUBE_SIZE - 2;

    public Drawing() {
    }

    // used to draw the cube to the screen
    public void draw(Graphics2D g2) {
        // set the color to white
        g2.setColor(Color.white);
        // draw the cube
        drawCube(g2);
    }

    private void drawCube(Graphics2D g2) {

        // draw top face
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Rectangle facelet = new Rectangle(CubeUI.WINDOW_WIDTH/2 + CUBE_SIZE * (i - 3), CubeUI.WINDOW_HEIGHT/2 + CUBE_SIZE * j - CUBE_SIZE * 9/2, FACELET_SIZE, FACELET_SIZE);

                switch (CubeUI.getCube()[i + j * 3]) {
                    case 'W' -> g2.setColor(Color.WHITE);
                    case 'R' -> g2.setColor(Color.RED);
                    case 'B' -> g2.setColor(Color.BLUE);
                    case 'O' -> g2.setColor(Color.ORANGE);
                    case 'G' -> g2.setColor(Color.GREEN);
                    case 'Y' -> g2.setColor(Color.YELLOW);
                    default -> g2.setColor(Color.BLACK);
                }

                g2.fill(facelet);
                g2.setColor(Color.BLACK);
                g2.draw(facelet);
            }
        }

        // draw front faces
        for (int k = 0; k < 4; k++) {
            for (int i = 0; i < 3; i++) {
                for (int j = 3; j < 6; j++) {
                    Rectangle facelet = new Rectangle(CubeUI.WINDOW_WIDTH/2 + CUBE_SIZE * (3*k + i - 6), CubeUI.WINDOW_HEIGHT/2 + CUBE_SIZE * j - CUBE_SIZE * 9/2, FACELET_SIZE, FACELET_SIZE);

                    switch (CubeUI.getCube()[i + j * 3 + k * 9]) {
                        case 'W' -> g2.setColor(Color.WHITE);
                        case 'R' -> g2.setColor(Color.RED);
                        case 'B' -> g2.setColor(Color.BLUE);
                        case 'O' -> g2.setColor(Color.ORANGE);
                        case 'G' -> g2.setColor(Color.GREEN);
                        case 'Y' -> g2.setColor(Color.YELLOW);
                        default -> g2.setColor(Color.BLACK);
                    }

                    g2.fill(facelet);
                    g2.setColor(Color.BLACK);
                    g2.draw(facelet);
                }
                
            }
        }

        // draw bottom face
        for (int i = 0; i < 3; i++) {
            for (int j = 6; j < 9; j++) {
                Rectangle facelet = new Rectangle(CubeUI.WINDOW_WIDTH/2 + CUBE_SIZE * (i - 3), CubeUI.WINDOW_HEIGHT/2 + CUBE_SIZE * j - CUBE_SIZE * 9/2, FACELET_SIZE, FACELET_SIZE);

                switch (CubeUI.getCube()[i + j * 3 + 27]) {
                    case 'W' -> g2.setColor(Color.WHITE);
                    case 'R' -> g2.setColor(Color.RED);
                    case 'B' -> g2.setColor(Color.BLUE);
                    case 'O' -> g2.setColor(Color.ORANGE);
                    case 'G' -> g2.setColor(Color.GREEN);
                    case 'Y' -> g2.setColor(Color.YELLOW);
                    default -> g2.setColor(Color.BLACK);
                }

                g2.fill(facelet);
                g2.setColor(Color.BLACK);
                g2.draw(facelet);
            }
        }

        // draw a small dot at the exact center of the window
        // int centerX = CubeUI.WINDOW_WIDTH / 2;
        // int centerY = CubeUI.WINDOW_HEIGHT / 2;
        // int dotDiameter = 20;
        // g2.setColor(Color.CYAN);
        // g2.fillOval(centerX - dotDiameter / 2, centerY - dotDiameter / 2, dotDiameter, dotDiameter);
    }
}