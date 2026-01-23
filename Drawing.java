import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

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

    public void drawCorners(Graphics2D g2) {
        // set the color to white
        g2.setColor(Color.white);
        // draw the cube
        drawCubeCorners(g2);
    }

    private void drawCubeCorners(Graphics2D g2) {
        // draw top face
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                Rectangle facelet = new Rectangle(CubeCornersUI.WINDOW_WIDTH/2 + CUBE_SIZE * (i - 2), CubeCornersUI.WINDOW_HEIGHT/2 + CUBE_SIZE * j - CUBE_SIZE * 3, FACELET_SIZE, FACELET_SIZE);

                g2.setColor(getColor(CubeCornersUI.getCube()[i + j * 2]));
                g2.fill(facelet);
                g2.setColor(Color.BLACK);
                g2.draw(facelet);
            }
        }

        // draw front faces
        for (int k = 0; k < 4; k++) {
            for (int i = 0; i < 2; i++) {
                for (int j = 2; j < 4; j++) {
                    Rectangle facelet = new Rectangle(CubeCornersUI.WINDOW_WIDTH/2 + CUBE_SIZE * (2*k + i - 4), CubeCornersUI.WINDOW_HEIGHT/2 + CUBE_SIZE * j - CUBE_SIZE * 3, FACELET_SIZE, FACELET_SIZE);

                    g2.setColor(getColor(CubeCornersUI.getCube()[k * 4 + i + j * 2]));
                    g2.fill(facelet);
                    g2.setColor(Color.BLACK);
                    g2.draw(facelet);
                }
                
            }
        }

        // draw bottom face
        for (int i = 0; i < 2; i++) {
            for (int j = 4; j < 6; j++) {
                Rectangle facelet = new Rectangle(CubeCornersUI.WINDOW_WIDTH/2 + CUBE_SIZE * (i - 2), CubeCornersUI.WINDOW_HEIGHT/2 + CUBE_SIZE * j - CUBE_SIZE * 3, FACELET_SIZE, FACELET_SIZE);

                g2.setColor(getColor(CubeCornersUI.getCube()[i + j * 2 + 12]));

                g2.fill(facelet);
                g2.setColor(Color.BLACK);
                g2.draw(facelet);
            }
        }
    }

    private void drawCube(Graphics2D g2) {

        byte[] cubeArray = Main.cube.getCube();

        // draw top face
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Rectangle facelet = new Rectangle(CubeUI.WINDOW_WIDTH/2 + CUBE_SIZE * (i - 3), CubeUI.WINDOW_HEIGHT/2 + CUBE_SIZE * j - CUBE_SIZE * 9/2, FACELET_SIZE, FACELET_SIZE);

                g2.setColor(getColor(cubeArray[i + j * 3]));
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

                    g2.setColor(getColor(cubeArray[k * 9 + i + j * 3]));
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

                g2.setColor(getColor(cubeArray[i + j * 3 + 27]));

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

    private Color getColor(byte c) {
        return switch (c) {
            case 0 -> Color.WHITE;
            case 1 -> Color.ORANGE;
            case 2 -> Color.GREEN;
            case 3 -> Color.RED;
            case 4 -> Color.BLUE;
            case 5 -> Color.YELLOW;
            default -> Color.BLACK;
        };
    }
}