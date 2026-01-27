import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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
    private int disqualifiedStates = 0;
    private int[] phase2AllowedMoves = {0, 1, 2, 3, 4, 5, 8, 11, 14, 17};

    public long getStateCounter() {
        return stateCounter;
    }

    public long getDisqualifiedStates() {
        return disqualifiedStates;
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

    public int getNumberOfMoves() {
        return phase1Moves.size() + phase2Moves.size();
    }

    public boolean solvePhase2(Cube cube, int maxDepth) {

        for (int depth = 1; depth <= 2 * maxDepth; depth++) {
            this.currentDepth = depth;
            this.currentStateCounter = 0L;
            this.estimatedStates = (long) Math.pow(7, depth);
            byte[] key = CubeKey.encodePositionToByte(cube.getCube());
            if (solvePhase2Rec(cube, key, depth)) {
                System.out.println("\rSolution for phase twofound at depth " + depth + "  ");
                // AI GENERATED ENHANCEMENT: Send to GUI
                MessageLog.getInstance().logSuccess("Phase 2 complete at depth " + depth);
                javax.swing.SwingUtilities.invokeLater(() -> CubeUI3D.refresh());
                return true;
            }
            System.out.flush();
        }
        return false;
    }

    public boolean solvePhase2Rec(Cube state, byte[] key, int depth) {

        // 1. BASE CASE: If the cube is solved, we are done
        if (state.isSolved()) return true;

        // 2. STOPPING CONDITION: Stop if we hit the maximum allowed search depth
        if (depth <= 0) return false;

        int minSolutionLength = Main3D.heuristic.getCornerSolutionLength(key);
        if (minSolutionLength == -1) {
            System.out.println("Invalid key: " + Arrays.toString(key));
        } else if (minSolutionLength > depth) {
            disqualifiedStates++;
            return false;
        }

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
            key = CubeKey.applyMove(key, i);
            phase2Moves.push(i);
            stateCounter++;
            this.currentStateCounter++;

            // Progress update
            // AI GENERATED ENHANCEMENT: Progress overwrites itself in GUI
            if (this.currentStateCounter % 50000000 == 0) {
                String pct = String.format(java.util.Locale.US, "%.2f", (this.currentStateCounter * 100.0) / this.estimatedStates);
                System.out.print("\rDepth: " + this.currentDepth + " Processed: " + pct + "% "); // '\r' returns to start of line
                MessageLog.getInstance().logSolverProgress("Phase 2 - Depth: " + this.currentDepth + " Progress: " + pct + "%");
                javax.swing.SwingUtilities.invokeLater(() -> CubeUI3D.refresh());
            }

            // Continue exploring this path
            // Recurse: Move to the next level
            if (solvePhase2Rec(state, key, depth - 1)) {
                return true;
            }

            // 4. BACKTRACK: Undo the choice to return to the previous state
            state.undoMove(i);
            key = CubeKey.undoMove(key, i);
            phase2Moves.removeLast();
        }

        return false;
    }

    public boolean solveCube(Cube cube, int maxDepth) {

        for (int depth = 1; depth <= maxDepth; depth++) {
            // System.out.println("Trying depth " + depth + "...");
            this.currentDepth = depth;
            this.currentStateCounter = 0L;
            this.estimatedStates = (long) Math.pow(8, depth);
            byte[] key = CubeKey.encodeOrientationToByte(cube.getCube());
            if (solveCubeRec(cube, key, depth)) {
                System.out.println("\rSolution for phase one found at depth " + depth + "  ");
                // AI GENERATED ENHANCEMENT: Send to GUI
                MessageLog.getInstance().logSuccess("Phase 1 complete at depth " + depth);
                javax.swing.SwingUtilities.invokeLater(() -> CubeUI3D.refresh());
                return true;
            }
            System.out.flush();
        }
        return false;
    }

    public boolean solveCubeRec(Cube state, byte[] key, int depth) {

        // 1. BASE CASE: If the cube is solved, we are done
        if (state.isOrientated()) return true;

        // 2. STOPPING CONDITION: Stop if we hit the maximum allowed search depth
        if (depth == 0) return false;

        int minSolutionLength = Main3D.heuristic.getCornerOrientationSolutionLength(key);

        if (minSolutionLength == -1) {
            System.out.println("Invalid key: " + Arrays.toString(key));
        } else if (minSolutionLength > depth) {
            disqualifiedStates++;
            return false;
        }

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
            key = CubeKey.applyMove(key, i);
            this.phase1Moves.push(i);
            this.stateCounter++;
            this.currentStateCounter++;

            // Progress update
            // AI GENERATED ENHANCEMENT: Progress overwrites itself in GUI
            if (this.currentStateCounter % 50000000 == 0) {
                String pct = String.format(java.util.Locale.US, "%.2f", (this.currentStateCounter * 100.0) / this.estimatedStates);
                System.out.print("\rDepth: " + this.currentDepth + " Processed: " + pct + "% "); // '\r' returns to start of line
                MessageLog.getInstance().logSolverProgress("Phase 1 - Depth: " + this.currentDepth + " Progress: " + pct + "%");
                javax.swing.SwingUtilities.invokeLater(() -> CubeUI3D.refresh());
            }

            // Continue exploring this path
            // Recurse: Move to the next level
            if (solveCubeRec(state, key, depth - 1)) {
                return true;
            }

            // 4. BACKTRACK: Undo the choice to return to the previous state
            state.undoMove(i);
            key = CubeKey.undoMove(key, i);
            this.phase1Moves.removeLast();
        }

        return false;
    }

    public boolean solveCorner(Cube cube, int maxDepth) {
        for (int depth = 1; depth <= maxDepth; depth++) {
            this.currentDepth = depth;
            byte[] key = CubeKey.encodePositionToByte(cube.getCube());
            if (solveCornerRec(cube, key, depth)) {
                return true;
            }
        }
        return false;
    }

    public boolean solveCornerRec(Cube state, byte[] key, int depth) {
        // 1. BASE CASE: If the cube is solved, we are done
        if (state.isCornersSolved()) return true;

        // 2. STOPPING CONDITION: Stop if we hit the maximum allowed search depth
        if (depth == 0) return false;

        int minSolutionLength = Testing.heuristic.getCornerSolutionLength(key);
        if (minSolutionLength != -1 && minSolutionLength > depth) return false;

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
            key = CubeKey.applyMove(key, i);
            this.phase2Moves.push(i);
            this.stateCounter++;
            this.currentStateCounter++;

            // Continue exploring this path
            // Recurse: Move to the next level
            if (solveCornerRec(state, key, depth - 1)) {
                return true;
            }

            // 4. BACKTRACK: Undo the choice to return to the previous state
            state.undoMove(i);
            key = CubeKey.undoMove(key, i);
            phase2Moves.removeLast();
        }

        return false;
    }

    public boolean solveCornerOrientation(Cube cube, int maxDepth) {
        for (int depth = 1; depth <= maxDepth; depth++) {
            this.currentDepth = depth;
            byte[] key = new byte[8];
            if (solveCornerOrientationRec(cube, key, depth)) {
                return true;
            }
        }
        return false;
    }

    public boolean solveCornerOrientationRec(Cube state, byte[] key, int depth) {
        // 1. BASE CASE: If the cube is solved, we are done
        if (state.isCornersOrientated()) return true;

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
            // key = CubeKey.applyOrientationMove(key, i);
            this.phase1Moves.push(i);
            this.stateCounter++;
            this.currentStateCounter++;

            // Continue exploring this path
            // Recurse: Move to the next level
            if (solveCornerOrientationRec(state, key, depth - 1)) {
                return true;
            }

            // 4. BACKTRACK: Undo the choice to return to the previous state
            state.undoMove(i);
            // key = CubeKey.undoOrientationMove(key, i);
            phase1Moves.removeLast();
        }

        return false;
    }

    public void reset() {
        this.phase1Moves = new IntStack();
        this.phase2Moves = new IntStack();
        this.stateCounter = 0L;
        this.currentStateCounter = 0L;
        this.currentDepth = 0;
        this.disqualifiedStates = 0;
    }
}