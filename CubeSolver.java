import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class CubeSolver {

    private long stateCounter = 0L;
    private long currentStateCounter = 0L;
    private long estimatedStates;
    private HashSet<String> visitedStates = new HashSet<>();    
    private int currentDepth = 0;
    private IntStack phase1Moves = new IntStack();
    private IntStack phase2Moves = new IntStack();
    private int[] phase2AllowedMoves = {0, 1, 2, 3, 4, 5, 8, 11, 14, 17};

    public long getStateCounter() {
        return stateCounter;
    }

    public String getMoves() {
        StringBuilder sb = new StringBuilder();
        String[] faces = {"U", "D", "L", "R", "F", "B"};

        for (int i = 0; i < phase1Moves.size() + phase2Moves.size(); i++) {
            int mv;
            if (i < phase1Moves.size()) {
                mv = phase1Moves.get(i);
            } else {
                mv = phase2Moves.get(i - phase1Moves.size());
            }
            int face = mv / 3; // 0..5
            int turn = mv % 3; // 0=clockwise,1=counter-clockwise,2=180
            sb.append(faces[face]);
            if (turn == 1) {
                sb.append("'");
            } else if (turn == 2) {
                sb.append("2");
            }
            sb.append(" ");
        }
        return sb.toString().trim();
    }

    public boolean solvePhase2(Cube cube, int maxDepth) {

        for (int depth = 1; depth <= maxDepth; depth++) {
            this.currentDepth = depth;
            this.currentStateCounter = 0L;
            this.estimatedStates = (long) Math.pow(7, depth);
            if (solvePhase2Rec(cube, depth)) {
                System.out.println("\rSolution for phase twofound at depth " + depth + "  ");
                return true;
            }
            System.out.flush();
        }
        return false;
    }

    public boolean solvePhase2Rec(Cube state, int depth) {

        // 1. BASE CASE: If the cube is solved, we are done
        if (state.isSolved()) return true;

        // 2. STOPPING CONDITION: Stop if we hit the maximum allowed search depth
        if (depth <= 0) return false;

        // 3. EXPLORE CHOICES: Iterate through all possible moves
        for (int j = 0; j < 10; j++) {

            int i = phase2AllowedMoves[j];

            int movesSize = phase2Moves.size();

            if (movesSize > 0) {
                int face = i / 3;
                int lastMove = phase2Moves.get(movesSize - 1);
                int lastFace = lastMove / 3;

                // Avoid immediately reversing the previous move
                if (lastFace == face) {
                    continue;
                }

                // If this move is on the face opposite the previous face, enforce a canonical order
                // to avoid exploring both orders (e.g., L R and R L). Allow only the smaller-face-first.
                if (lastFace == (face ^ 1) && face > lastFace) {
                    continue;
                }

                // Avoid creating an alternating two-face cycle like A B A B (e.g., R L R L or R L R' L')
                if (movesSize >= 3) {
                    int faceA = phase2Moves.get(movesSize - 3) / 3;
                    int faceB = phase2Moves.get(movesSize - 2) / 3;
                    int faceC = phase2Moves.get(movesSize - 1) / 3;
                    // pattern A B A and trying to play B would create A B A B — skip it
                    if (faceA == faceC && faceB == face) {
                        continue;
                    }
                }
            }

            // Apply the choice
            state.applyMove(i);
            phase2Moves.push(i);
            stateCounter++;
            this.currentStateCounter++;

            // Progress update
            if (this.currentStateCounter % 50000000 == 0) {
                String pct = String.format(java.util.Locale.US, "%.2f", (this.currentStateCounter * 100.0) / this.estimatedStates);
                System.out.print("\rDepth: " + this.currentDepth + " Processed: " + pct + "% "); // '\r' returns to start of line
            }

            // Continue exploring this path
            // Recurse: Move to the next level
            if (solvePhase2Rec(state, depth - 1)) {
                return true;
            }

            // 4. BACKTRACK: Undo the choice to return to the previous state
            state.undoMove(i);
            phase2Moves.removeLast();
        }

        return false;
    }

    public boolean solveCube(Cube cube, int maxDepth) {

        for (int depth = 1; depth <= maxDepth; depth++) {
            // System.out.println("Trying depth " + depth + "...");
            this.currentDepth = depth;
            this.currentStateCounter = 0L;
            this.estimatedStates = (long) Math.pow(13.7, depth);
            if (solveCubeRec(cube, depth)) {
                System.out.println("\rSolution for phase one found at depth " + depth + "  ");
                return true;
            }
            System.out.flush();
        }
        return false;
    }

    public boolean solveCubeRec(Cube state, int depth) {

        // 1. BASE CASE: If the cube is solved, we are done
        if (state.isOrientated()) return true;

        // 2. STOPPING CONDITION: Stop if we hit the maximum allowed search depth
        if (depth == 0) return false;

        // 3. EXPLORE CHOICES: Iterate through all possible moves
        for (int i = 0; i < 18; i++) {

            int movesSize = phase1Moves.size();

            if (movesSize > 0) {
                int face = i / 3;
                int lastMove = phase1Moves.get(movesSize - 1);
                int lastFace = lastMove / 3;

                // Avoid immediately reversing the previous move
                if (lastFace == face) {
                    continue;
                }

                // If this move is on the face opposite the previous face, enforce a canonical order
                // to avoid exploring both orders (e.g., L R and R L). Allow only the smaller-face-first.
                if (lastFace == (face ^ 1) && face > lastFace) {
                    continue;
                }

                // Avoid creating an alternating two-face cycle like A B A B (e.g., R L R L or R L R' L')
                if (movesSize >= 3) {
                    int faceA = phase1Moves.get(movesSize - 3) / 3;
                    int faceB = phase1Moves.get(movesSize - 2) / 3;
                    int faceC = phase1Moves.get(movesSize - 1) / 3;
                    // pattern A B A and trying to play B would create A B A B — skip it
                    if (faceA == faceC && faceB == face) {
                        continue;
                    }
                }
            }

            // Apply the choice
            state.applyMove(i);
            this.phase1Moves.push(i);
            this.stateCounter++;
            this.currentStateCounter++;

            // Progress update
            if (this.currentStateCounter % 50000000 == 0) {
                String pct = String.format(java.util.Locale.US, "%.2f", (this.currentStateCounter * 100.0) / this.estimatedStates);
                System.out.print("\rDepth: " + this.currentDepth + " Processed: " + pct + "% "); // '\r' returns to start of line
            }

            // Continue exploring this path
            // Recurse: Move to the next level
            if (solveCubeRec(state, depth - 1)) {
                return true;
            }

            // 4. BACKTRACK: Undo the choice to return to the previous state
            state.undoMove(i);
            phase1Moves.removeLast();
        }

        return false;
    }

    public boolean solveCorners(CubeCorner cube, int maxDepth) {

        for (int depth = 2; depth <= maxDepth; depth++) {
            System.out.println("Trying depth " + depth + "...");
            if (solveCornersRec(cube, depth)) {
                return true;
            }
        }
        return false;
    }

    public boolean solveCornersRec(CubeCorner state, int depth) {

        // 1. BASE CASE: If the cube is solved, we are done
        if (state.isOriented()) return true;

        // 2. STOPPING CONDITION: Stop if we hit the maximum allowed search depth
        if (depth <= 0) return false;

        // 3. EXPLORE CHOICES: Iterate through all possible moves
        for (int i = 0; i < 12; i++) {

            // Avoid immediately reversing the previous move
            // (i ^ 1) flips the last bit to get the inverse move index (e.g., 0 <-> 1, 2 <-> 3, etc.)
            if (moves.size() > 0 && moves.get(moves.size() - 1) == (i ^ 1)) {
                continue;
            }

            if (moves.size() >= 2) {
                int size = moves.size();
                // Prevent patterns like "X X X'" or "X X' X" or "X' X X"
                int last = moves.get(size - 1);
                int secondLast = moves.get(size - 2);
                // Prevent three moves on the same face in a row (e.g., X X X)
                if ((last / 2 == i / 2) && (secondLast / 2 == i / 2)) {
                    continue;
                }
                // Prevent patterns like "X X' X" or "X' X X"
                if ((secondLast == (i ^ 1) && last == i) || (secondLast == i && last == (i ^ 1))) {
                    continue;
                }
            }

            int face = i / 2;
            if (moves.size() > 0) {
                int lastFace = moves.get(moves.size() - 1) / 2;
                // If this move is on the face opposite the previous face, enforce a canonical order
                // to avoid exploring both orders (e.g., L R and R L). Allow only the smaller-face-first.
                if (lastFace == (face ^ 1) && face > lastFace) {
                    continue;
                }
            }

            // Apply the choice
            state.applyMove(i);
            moves.push(i);
            stateCounter++;

            // Continue exploring this path
            // Recurse: Move to the next level
            if (solveCornersRec(state, depth - 1)) {
                return true;
            }

            // 4. BACKTRACK: Undo the choice to return to the previous state
            state.undoMove(i);
            moves.removeLast();
        }

        return false;
    }

}