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
    
    // AI GENERATED ENHANCEMENT: Append moves to the same line instead of creating new lines
    public void logMove(String move) {
        if (!messages.isEmpty()) {
            String lastMsg = messages.get(messages.size() - 1);
            // If the last message was a move, append to it
            if (lastMsg.startsWith("[INFO] Moves: ")) {
                messages.remove(messages.size() - 1);
                // Extract existing moves and append new one
                String existingMoves = lastMsg.substring("[INFO] Moves: ".length());
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
