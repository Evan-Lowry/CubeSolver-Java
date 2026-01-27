// ========================================
// AI GENERATED CODE - MessageLog.java
// Created: January 22, 2026
// Purpose: Singleton class to capture and store console messages for on-screen display
// ========================================

import java.util.ArrayList;
import java.util.List;

public class MessageLog {
    private static MessageLog instance;
    private List<String> messages;
    private static final int MAX_MESSAGES = 20;
    
    private MessageLog() {
        messages = new ArrayList<>();
    }
    
    public static MessageLog getInstance() {
        if (instance == null) {
            instance = new MessageLog();
        }
        return instance;
    }
    
    public void addMessage(String message) {
        // Add timestamp for better context
        messages.add(message);
        
        // Keep only the most recent messages
        while (messages.size() > MAX_MESSAGES) {
            messages.remove(0);
        }
    }
    
    public List<String> getMessages() {
        return new ArrayList<>(messages);
    }
    
    public void clear() {
        messages.clear();
    }
    
    // Helper methods to log common message types
    public void logInfo(String message) {
        addMessage("[INFO] " + message);
    }
    
    public void logSuccess(String message) {
        addMessage("[SUCCESS] " + message);
    }
    
    public void logError(String message) {
        addMessage("[ERROR] " + message);
    }
    
    public void logSolver(String message) {
        addMessage("[SOLVER] " + message);
    }
    
    // AI GENERATED ENHANCEMENT: Update progress messages in place (overwrite last progress)
    public void logSolverProgress(String message) {
        String fullMessage = "[SOLVER] " + message;
        
        // If the last message was a progress update, replace it
        if (!messages.isEmpty()) {
            String lastMsg = messages.get(messages.size() - 1);
            if (lastMsg.contains("Progress:") || lastMsg.contains("Depth:")) {
                // Remove the last progress message
                messages.remove(messages.size() - 1);
            }
        }
        
        // Add the new progress message
        messages.add(fullMessage);
        
        // Keep only the most recent messages
        while (messages.size() > MAX_MESSAGES) {
            messages.remove(0);
        }
    }
    
    // Helper method to check if two moves are inverses (e.g., R and R')
    private boolean areInverseMoves(String move1, String move2) {
        // R and R' are inverses
        if (move1.equals("R") && move2.equals("R'")) return true;
        if (move1.equals("R'") && move2.equals("R")) return true;
        
        // Same for all other faces
        if (move1.equals("L") && move2.equals("L'")) return true;
        if (move1.equals("L'") && move2.equals("L")) return true;
        
        if (move1.equals("U") && move2.equals("U'")) return true;
        if (move1.equals("U'") && move2.equals("U")) return true;
        
        if (move1.equals("D") && move2.equals("D'")) return true;
        if (move1.equals("D'") && move2.equals("D")) return true;
        
        if (move1.equals("F") && move2.equals("F'")) return true;
        if (move1.equals("F'") && move2.equals("F")) return true;
        
        if (move1.equals("B") && move2.equals("B'")) return true;
        if (move1.equals("B'") && move2.equals("B")) return true;
        
        // Double moves cancel with themselves
        if (move1.equals("R2") && move2.equals("R2")) return true;
        if (move1.equals("L2") && move2.equals("L2")) return true;
        if (move1.equals("U2") && move2.equals("U2")) return true;
        if (move1.equals("D2") && move2.equals("D2")) return true;
        if (move1.equals("F2") && move2.equals("F2")) return true;
        if (move1.equals("B2") && move2.equals("B2")) return true;
        
        return false;
    }
    
    // AI GENERATED ENHANCEMENT: Append moves to the same line instead of creating new lines
    // Also cancels out opposite moves (e.g., R followed by R')
    public void logMove(String move) {
        if (!messages.isEmpty()) {
            String lastMsg = messages.get(messages.size() - 1);
            // If the last message was a move, append to it
            if (lastMsg.startsWith("[INFO] Moves: ")) {
                messages.remove(messages.size() - 1);
                // Extract existing moves and append new one
                String existingMoves = lastMsg.substring("[INFO] Moves: ".length());
                
                // Split the existing moves to get the last move
                String[] movesArray = existingMoves.trim().split("\\s+");
                
                if (movesArray.length > 0) {
                    String lastMove = movesArray[movesArray.length - 1];
                    
                    // Check if the new move cancels the last move
                    if (areInverseMoves(lastMove, move)) {
                        // Remove the last move (cancel out)
                        if (movesArray.length > 1) {
                            // Rebuild the moves string without the last move
                            StringBuilder rebuiltMoves = new StringBuilder();
                            for (int i = 0; i < movesArray.length - 1; i++) {
                                if (i > 0) rebuiltMoves.append(" ");
                                rebuiltMoves.append(movesArray[i]);
                            }
                            messages.add("[INFO] Moves: " + rebuiltMoves.toString());
                        }
                        // If that was the only move, don't add anything back
                        // (the message was already removed)
                        return; // Don't add the new move
                    }
                }
                
                // Not an inverse, append normally
                messages.add("[INFO] Moves: " + existingMoves + " " + move);
            } else {
                // Start a new move sequence
                messages.add("[INFO] Moves: " + move);
            }
        } else {
            // First move
            messages.add("[INFO] Moves: " + move);
        }
        
        // Keep only the most recent messages
        while (messages.size() > MAX_MESSAGES) {
            messages.remove(0);
        }
    }
}
