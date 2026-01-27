// ========================================
// AI GENERATED CODE - Drawing3D.java
// Created: January 22, 2026
// Purpose: Draws the Rubik's Cube in an isometric 3D view
// Shows top, front, and right faces in a semi-flat 3D perspective
// ========================================

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.BasicStroke;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

public class Drawing3D {

    // Cube rendering parameters
    // AI GENERATED: Adjusted facelet size for better visibility
    public static final int FACELET_SIZE = 60;
    public static final int FACELET_BORDER_SIZE = 2;
    public static final double ISO_ANGLE = Math.PI / 6; // 30 degrees for isometric view
    
    // Isometric projection ratios
    private static final double COS_30 = Math.cos(ISO_ANGLE);
    private static final double SIN_30 = Math.sin(ISO_ANGLE);
    
    // Base position for the cube (center of main drawing area)
    private int centerX;
    private int centerY;
    
    public Drawing3D() {
    }
    
    // Main drawing method
    public void draw(Graphics2D g2, int windowWidth, int windowHeight) {
        // AI GENERATED: Adjust positioning for larger cube
        this.centerX = (int)(windowWidth * 0.26); // Shift cube left to make room for wider message panel
        this.centerY = windowHeight / 2; // Adjusted for larger cube
        
        // Enable anti-aliasing for smoother graphics
        g2.setRenderingHint(
            java.awt.RenderingHints.KEY_ANTIALIASING,
            java.awt.RenderingHints.VALUE_ANTIALIAS_ON
        );
        
        // Draw the cube in isometric view
        drawIsometricCube(g2);
        
        // Draw message log
        drawMessageLog(g2, windowWidth, windowHeight);
        
        // Draw controls help
        drawControls(g2, windowWidth, windowHeight);
    }
    
    private void drawIsometricCube(Graphics2D g2) {
        // AI GENERATED FIX: Get cube from CubeUI3D instead of Main to support both Main and Main3D
        byte[] cubeArray = CubeUI3D.getCube();
        if (cubeArray == null) {
            return; // Don't draw if cube is null
        }
        
        // Draw in order: bottom faces first (back to front), then top faces
        // This creates proper depth ordering
        
        // Draw back-bottom face first (left face - back part)
        drawLeftFace(g2, cubeArray);

        // Draw bottom face (partially visible from angle)
        drawBottomFace(g2, cubeArray);
        
        // Draw back face
        drawBackFace(g2, cubeArray);
        
        // Draw right face
        drawRightFace(g2, cubeArray);
        
        // Draw top face last (appears on top)
        drawTopFace(g2, cubeArray);

        // Draw front face
        drawFrontFace(g2, cubeArray);
    }
    
    // Convert 3D cube coordinates to 2D isometric screen coordinates
    private int[] toIso(double x, double y, double z) {
        // Isometric projection formula
        int screenX = (int) (centerX + (x - z) * COS_30 * FACELET_SIZE);
        int screenY = (int) (centerY + (y * FACELET_SIZE) - (x + z) * SIN_30 * FACELET_SIZE);
        return new int[]{screenX, screenY};
    }
    
    // Draw top face (white in solved state, index 0-8)
    private void drawTopFace(Graphics2D g2, byte[] cube) {
        double baseY = -1.5; // Top of cube
        
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                double x = col - 1.0;
                double z = row - 1.0;
                
                int index = (2 - row) + (2 - col) * 3;
                drawIsometricSquare(g2, x, baseY, z, getColor(cube[index]), true);
            }
        }
    }
    
    // Draw front face (green in solved state, index 18-26)
    private void drawFrontFace(Graphics2D g2, byte[] cube) {
        double baseX = -1.5; // Left of cube
        
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                double z = 1.0 - col; // Front to back
                double y = 1.5 - row; // Top to bottom
                
                int index = 26 - row * 3 - (2 - col);
                drawIsometricSquare(g2, baseX, y, z, getColor(cube[index]), false);
            }
        }
    }
    
    // Draw right face (red in solved state, index 27-35)
    private void drawRightFace(Graphics2D g2, byte[] cube) {
        double baseZ = -1.5; // Back of cube
        
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                double x = 1.0 - col; // Right to left (reversed)
                double y = 1.5 - row;
                
                int index = 27 + (2 - row) * 3 + (2 - col);
                // Back face is partially hidden, draw with slight transparency
                drawIsometricSquare(g2, x, y, baseZ, getColor(cube[index]), false);
            }
        }
    }
    
    // Draw left face (orange in solved state, index 9-17)
    private void drawLeftFace(Graphics2D g2, byte[] cube) {
        double baseZ = 1.5; // Front of cube
        
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                double x = col - 1.0;
                double y = 1.5 - row; // Top to bottom
                
                int index = 17 - row * 3 - col;
                drawIsometricSquare(g2, x, y, baseZ, getColor(cube[index]), false);
            }
        }
    }
    
    // Draw back face (blue in solved state, index 36-44)
    private void drawBackFace(Graphics2D g2, byte[] cube) {
        double baseX = 1.5; // Right of cube
        
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                double z = col - 1.0;
                double y = 1.5 - row;
                
                int index = 36 + (2 - row) * 3 + col;
                drawIsometricSquare(g2, baseX, y, z, getColor(cube[index]), false);
            }
        }
    }
    
    // Draw bottom face (yellow in solved state, index 45-53)
    private void drawBottomFace(Graphics2D g2, byte[] cube) {
        double baseY = 1.5; // Bottom of cube
        
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                double x = col - 1.0;
                double z = 1.0 - row; // Front to back
                
                int index = 45 + row + col * 3;
                drawIsometricSquare(g2, x, baseY, z, getColor(cube[index]), true);
            }
        }
    }
    
    // Draw a single square facelet in isometric projection
    private void drawIsometricSquare(Graphics2D g2, double x, double y, double z, Color color, boolean isHorizontal) {
        if (isHorizontal) {
            // Horizontal faces (top/bottom)
            int[] p1 = toIso(x - 0.45, y, z - 0.45);
            int[] p2 = toIso(x + 0.45, y, z - 0.45);
            int[] p3 = toIso(x + 0.45, y, z + 0.45);
            int[] p4 = toIso(x - 0.45, y, z + 0.45);
            
            Polygon poly = new Polygon();
            poly.addPoint(p1[0], p1[1]);
            poly.addPoint(p2[0], p2[1]);
            poly.addPoint(p3[0], p3[1]);
            poly.addPoint(p4[0], p4[1]);
            
            // Fill with color
            g2.setColor(color);
            g2.fillPolygon(poly);
            
            // Draw border
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(FACELET_BORDER_SIZE));
            g2.drawPolygon(poly);
        } else {
            // Vertical faces (front/back/left/right)
            int[] p1 = toIso(x - 0.45, y - 0.45, z);
            int[] p2 = toIso(x + 0.45, y - 0.45, z);
            int[] p3 = toIso(x + 0.45, y + 0.45, z);
            int[] p4 = toIso(x - 0.45, y + 0.45, z);
            
            // Check if it's actually a side face (left/right/back)
            if (Math.abs(z) > 0.1 && Math.abs(x) > 0.1) {
                // It's a corner piece, needs special handling
                if (Math.abs(x) > 1.4) {
                    // Right or left face
                    p1 = toIso(x, y - 0.45, z - 0.45);
                    p2 = toIso(x, y - 0.45, z + 0.45);
                    p3 = toIso(x, y + 0.45, z + 0.45);
                    p4 = toIso(x, y + 0.45, z - 0.45);
                } else if (Math.abs(z) > 1.4) {
                    // Front or back face
                    p1 = toIso(x - 0.45, y - 0.45, z);
                    p2 = toIso(x + 0.45, y - 0.45, z);
                    p3 = toIso(x + 0.45, y + 0.45, z);
                    p4 = toIso(x - 0.45, y + 0.45, z);
                }
            } else if (Math.abs(x) > 1.4) {
                // Right or left face
                p1 = toIso(x, y - 0.45, z - 0.45);
                p2 = toIso(x, y - 0.45, z + 0.45);
                p3 = toIso(x, y + 0.45, z + 0.45);
                p4 = toIso(x, y + 0.45, z - 0.45);
            }

            // No idea why but need to shift the polygon by 30 pixels upwards
            p1[1] -= 30;
            p2[1] -= 30;
            p3[1] -= 30;
            p4[1] -= 30;
            
            Polygon poly = new Polygon();
            poly.addPoint(p1[0], p1[1]);
            poly.addPoint(p2[0], p2[1]);
            poly.addPoint(p3[0], p3[1]);
            poly.addPoint(p4[0], p4[1]);
            
            // Fill with color
            g2.setColor(color);
            g2.fillPolygon(poly);
            
            // Draw border
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(FACELET_BORDER_SIZE));
            g2.drawPolygon(poly);
        }
    }
    
    // AI GENERATED ENHANCEMENT: Helper method to wrap text to fit within width
    // Returns pairs of [text, colorTag] to preserve color across wrapped lines
    private List<String[]> wrapTextWithColor(String text, int maxWidth, java.awt.FontMetrics fm) {
        List<String[]> lines = new ArrayList<>();
        
        // Determine the color tag for this message
        String colorTag = "DEFAULT";
        if (text.contains("[SUCCESS]")) {
            colorTag = "SUCCESS";
        } else if (text.contains("[ERROR]")) {
            colorTag = "ERROR";
        } else if (text.contains("[SOLVER]")) {
            colorTag = "SOLVER";
        } else if (text.contains("[INFO]")) {
            colorTag = "INFO";
        }
        
        if (fm.stringWidth(text) <= maxWidth) {
            // Text fits on one line
            lines.add(new String[]{text, colorTag});
            return lines;
        }
        
        // Extract the prefix (like [SUCCESS], [SOLVER], etc.) if present
        String prefix = "";
        String content = text;
        if (text.startsWith("[")) {
            int endBracket = text.indexOf("]");
            if (endBracket > 0) {
                prefix = text.substring(0, endBracket + 2); // Include the space after ]
                content = text.substring(endBracket + 2);
            }
        }
        
        // Split content into words
        String[] words = content.split(" ");
        StringBuilder currentLine = new StringBuilder(prefix);
        boolean firstLine = true;
        
        for (String word : words) {
            String testLine = currentLine.length() == prefix.length() ? 
                currentLine.toString() + word : 
                currentLine.toString() + " " + word;
            
            if (fm.stringWidth(testLine) <= maxWidth) {
                if (currentLine.length() > prefix.length()) {
                    currentLine.append(" ");
                }
                currentLine.append(word);
            } else {
                // Line is full, add it with color tag and start a new line
                if (currentLine.length() > 0) {
                    lines.add(new String[]{currentLine.toString(), colorTag});
                }
                // Continuation lines get indented slightly
                currentLine = new StringBuilder(firstLine ? "  " : "  ");
                currentLine.append(word);
                firstLine = false;
                prefix = "  ";  // Reset prefix to match continuation line indentation
            }
        }
        
        // Add the last line with color tag
        if (currentLine.length() > 0) {
            lines.add(new String[]{currentLine.toString(), colorTag});
        }
        
        return lines;
    }
    
    // Draw the message log panel
    private void drawMessageLog(Graphics2D g2, int windowWidth, int windowHeight) {
        // AI GENERATED: Wider message log panel
        int panelX = (int)(windowWidth * 0.52);
        int panelY = 20;
        int panelWidth = (int)(windowWidth * 0.45);
        int panelHeight = windowHeight - 180;
        
        // Draw panel background
        g2.setColor(new Color(30, 30, 30, 230));
        g2.fillRoundRect(panelX, panelY, panelWidth, panelHeight, 10, 10);
        
        // Draw panel border
        g2.setColor(new Color(100, 100, 255));
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(panelX, panelY, panelWidth, panelHeight, 10, 10);
        
        // Draw title
        g2.setColor(new Color(100, 200, 255));
        g2.setFont(new Font("Monospaced", Font.BOLD, 16));
        g2.drawString("MESSAGE LOG", panelX + 10, panelY + 25);
        
        // Draw separator line
        g2.setColor(new Color(100, 100, 255));
        g2.drawLine(panelX + 10, panelY + 35, panelX + panelWidth - 10, panelY + 35);
        
        // Draw messages
        List<String> messages = MessageLog.getInstance().getMessages();
        g2.setFont(new Font("Monospaced", Font.PLAIN, 11));
        
        int messageY = panelY + 55;
        int lineHeight = 18;
        int maxLines = (panelHeight - 70) / lineHeight;
        
        // AI GENERATED ENHANCEMENT: Flatten messages with word wrapping and color preservation
        List<String[]> wrappedMessages = new ArrayList<>();
        for (String msg : messages) {
            List<String[]> wrapped = wrapTextWithColor(msg, panelWidth - 30, g2.getFontMetrics());
            wrappedMessages.addAll(wrapped);
        }
        
        // Show most recent wrapped lines (from bottom up)
        int startIdx = Math.max(0, wrappedMessages.size() - maxLines);
        
        for (int i = startIdx; i < wrappedMessages.size(); i++) {
            String[] msgWithColor = wrappedMessages.get(i);
            String msg = msgWithColor[0];
            String colorTag = msgWithColor[1];
            
            // AI GENERATED ENHANCEMENT: Color is preserved across wrapped lines
            switch (colorTag) {
                case "SUCCESS" -> g2.setColor(new Color(100, 255, 100));
                case "ERROR" -> g2.setColor(new Color(255, 100, 100));
                case "SOLVER" -> g2.setColor(new Color(255, 255, 100));
                case "INFO" -> g2.setColor(new Color(150, 200, 255));
                default -> g2.setColor(Color.WHITE);
            }
            
            g2.drawString(msg, panelX + 15, messageY);
            messageY += lineHeight;
        }
        
        // If no messages, show help text
        if (messages.isEmpty()) {
            g2.setColor(new Color(150, 150, 150));
            g2.setFont(new Font("Monospaced", Font.ITALIC, 12));
            g2.drawString("No messages yet...", panelX + 15, panelY + 60);
            g2.drawString("Press SPACE to solve", panelX + 15, panelY + 80);
        }
    }
    
    // Draw controls help panel
    private void drawControls(Graphics2D g2, int windowWidth, int windowHeight) {
        // AI GENERATED: Wider controls panel to match message log
        int panelX = (int)(windowWidth * 0.52);
        int panelY = windowHeight - 150;
        int panelWidth = (int)(windowWidth * 0.45);
        int panelHeight = 130;
        
        // Draw panel background
        g2.setColor(new Color(30, 30, 30, 230));
        g2.fillRoundRect(panelX, panelY, panelWidth, panelHeight, 10, 10);
        
        // Draw panel border
        g2.setColor(new Color(100, 255, 100));
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(panelX, panelY, panelWidth, panelHeight, 10, 10);
        
        // Draw title
        g2.setColor(new Color(100, 255, 100));
        g2.setFont(new Font("Monospaced", Font.BOLD, 14));
        g2.drawString("CONTROLS", panelX + 10, panelY + 20);
        
        // Draw controls
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Monospaced", Font.PLAIN, 11));
        
        String[] controls = {
            "SPACE - Solve cube",
            "Q - Quit",
            "R - Reset",
            "1-6 - Double moves",
            "JFHGKDLSIEOW - Face rotations"
        };
        
        int y = panelY + 40;
        for (String control : controls) {
            g2.drawString(control, panelX + 15, y);
            y += 18;
        }
    }
    
    // Get color from byte value
    private Color getColor(byte c) {
        return switch (c) {
            case 0 -> new Color(255, 255, 255); // White
            case 1 -> new Color(255, 140, 0);   // Orange
            case 2 -> new Color(0, 200, 0);     // Green
            case 3 -> new Color(200, 0, 0);     // Red
            case 4 -> new Color(0, 100, 255);   // Blue
            case 5 -> new Color(255, 220, 0);   // Yellow
            default -> Color.BLACK;
        };
    }
}
