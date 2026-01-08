import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class CubeSolver {

    private long stateCounter = 0L;
    private IntStack moves = new IntStack();

    public long getStateCounter() {
        return stateCounter;
    }

    public String getMoves() {
        StringBuilder sb = new StringBuilder();
        String[] faces = {"U", "D", "L", "R", "F", "B"};
        if (moves.size() == 0) return "";

        // Heuristic: if any move index is >= 12 we assume a 3-moves-per-face encoding
        int maxVal = 0;
        for (int k = 0; k < moves.size(); k++) {
            int v = moves.get(k);
            if (v > maxVal) maxVal = v;
        }
        int movesPerFace = (maxVal >= 12) ? 3 : 2;

        int i = 0;
        while (i < moves.size()) {
            int first = moves.get(i);
            int face = first / movesPerFace; // face index 0..5
            int j = i;
            int net = 0; // net quarter-turns clockwise

            // accumulate consecutive moves on the same face
            while (j < moves.size() && moves.get(j) / movesPerFace == face) {
                int mv = moves.get(j);
                if (movesPerFace == 3) {
                    // mapping: 0 => +1 (clockwise), 1 => -1 (prime), 2 => +2 (double)
                    int t = mv % 3;
                    if (t == 0) net += 1;
                    else if (t == 1) net -= 1;
                    else net += 2;
                } else {
                    // 2-per-face mapping: even => +1, odd => -1
                    net += (mv % 2 == 0) ? 1 : -1;
                }
                j++;
            }

            net = ((net % 4) + 4) % 4; // normalize to 0..3

            if (net == 1) {
                sb.append(faces[face]).append("()");
            } else if (net == 2) {
                sb.append(faces[face]).append("2()");
            } else if (net == 3) {
                sb.append(faces[face]).append("p()");
            }

            if (j < moves.size()) sb.append(" ");
            i = j;
        }

        return sb.toString().trim();
    }

    public boolean kociembaSolve (Cube cube, int maxDepth) {
        if (solveAnyCube(cube, maxDepth)) {
            System.out.println("Phase 1 solved the cube to an oriented state.");
            return solveAnyOrientedCube(cube, maxDepth);
        }
        return false;
    }

    public boolean solveAnyOrientedCube(Cube cube, int maxDepth) {

        for (int depth = 1; depth <= maxDepth * 2; depth++) {
            System.out.println("Trying depth " + depth + "...");
            if (solveOrientedCube(cube, depth)) {
                return true;
            }
        }
        return false;
    }

        public boolean solveOrientedCube(Cube state, int depth) {

        // 1. BASE CASE: If the cube is solved, we are done
        if (state.isSolved()) return true;

        // 2. STOPPING CONDITION: Stop if we hit the maximum allowed search depth
        if (depth <= 0) return false;

        // 3. EXPLORE CHOICES: Iterate through all possible moves
        for (int i = 0; i < 8; i++) {

            // Avoid immediately reversing the previous move
            // (i ^ 1) flips the last bit to get the inverse move index (e.g., 0 <-> 1, 2 <-> 3, etc.)
            if (i < 4 && moves.size() > 0 && moves.get(moves.size() - 1) == (i ^ 1)) {
                continue;
            }

            if (i < 4 && moves.size() >= 2) {
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
                if (i < 4) {
                    int lastFace = moves.get(moves.size() - 1) / 2;
                    // If this move is on the face opposite the previous face, enforce a canonical order
                    // to avoid exploring both orders (e.g., L R and R L). Allow only the smaller-face-first.
                    if (lastFace == (face ^ 1) && face > lastFace) {
                        continue;
                    }
                }
            }

            // Apply the choice
            state.applyOrientedMove(i);
            moves.push(i);
            stateCounter++;

            // Continue exploring this path
            // Recurse: Move to the next level
            if (solveOrientedCube(state, depth - 1)) {
                return true;
            }

            // 4. BACKTRACK: Undo the choice to return to the previous state
            state.undoOrientedMove(i);
            moves.removeLast();
        }

        return false;
    }

    public boolean solveAnyCube(Cube cube, int maxDepth) {

        for (int depth = 1; depth <= maxDepth; depth++) {
            System.out.println("Trying depth " + depth + "...");
            if (solveCube(cube, depth)) {
                return true;
            }
        }
        return false;
    }

    public boolean solveCube(Cube state, int depth) {

        // 1. BASE CASE: If the cube is solved, we are done
        if (state.isSolved()) return true;

        // 2. STOPPING CONDITION: Stop if we hit the maximum allowed search depth
        if (depth == 0) return false;

        // 3. EXPLORE CHOICES: Iterate through all possible moves
        for (int i = 0; i < 12; i++) {

            int face = i / 3;

            // Avoid immediately reversing the previous move
            if (moves.size() > 0 && moves.get(moves.size() - 1)/3 == face && moves.get(moves.size() - 1) % 3 + i % 3 == 3) {
                continue;
            }

            if (moves.size() > 0) {
                int lastFace = moves.get(moves.size() - 1) / 3;
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
            if (solveCube(state, depth - 1)) {
                return true;
            }

            // 4. BACKTRACK: Undo the choice to return to the previous state
            state.undoMove(i);
            moves.removeLast();
        }

        return false;
    }

    public boolean solveAnyCubeCorners(CubeCorner cube, int maxDepth) {

        for (int depth = 2; depth <= maxDepth; depth++) {
            System.out.println("Trying depth " + depth + "...");
            if (solveCubeCorners(cube, depth)) {
                return true;
            }
        }
        return false;
    }

    public boolean solveCubeCorners(CubeCorner state, int depth) {

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
            if (solveCubeCorners(state, depth - 1)) {
                return true;
            }

            // 4. BACKTRACK: Undo the choice to return to the previous state
            state.undoMove(i);
            moves.removeLast();
        }

        return false;
    }

    // public boolean solveCubeCorners(CubeCorner state, int depth) {

    //     // 1. BASE CASE: If the cube is solved, we are done
    //     if (state.isSolved()) return true;

    //     // 2. STOPPING CONDITION: Stop if we hit the maximum allowed search depth
    //     if (depth <= 0) return false;

    //     // 3. EXPLORE CHOICES: Iterate through all possible moves
    //     for (int i = 0; i < 12; i++) {

    //         // Avoid immediately reversing the previous move
    //         // (i ^ 1) flips the last bit to get the inverse move index (e.g., 0 <-> 1, 2 <-> 3, etc.)
    //         if (moves.size() > 0 && moves.get(moves.size() - 1) == (i ^ 1)) {
    //             continue;
    //         }

    //         if (moves.size() >= 2) {
    //             int size = moves.size();
    //             // Prevent patterns like "X X X'" or "X X' X" or "X' X X"
    //             int last = moves.get(size - 1);
    //             int secondLast = moves.get(size - 2);
    //             // Prevent three moves on the same face in a row (e.g., X X X)
    //             if ((last / 2 == i / 2) && (secondLast / 2 == i / 2)) {
    //                 continue;
    //             }
    //             // Prevent patterns like "X X' X" or "X' X X"
    //             if ((secondLast == (i ^ 1) && last == i) || (secondLast == i && last == (i ^ 1))) {
    //                 continue;
    //             }
    //         }

    //         int face = i / 2;
    //         if (moves.size() > 0) {
    //             int lastFace = moves.get(moves.size() - 1) / 2;
    //             // If this move is on the face opposite the previous face, enforce a canonical order
    //             // to avoid exploring both orders (e.g., L R and R L). Allow only the smaller-face-first.
    //             if (lastFace == (face ^ 1) && face > lastFace) {
    //                 continue;
    //             }
    //         }

    //         // Apply the choice
    //         state.applyMove(i);
    //         moves.push(i);
    //         stateCounter++;

    //         // Continue exploring this path
    //         // Recurse: Move to the next level
    //         if (solveCubeCorners(state, depth - 1)) {
    //             return true;
    //         }

    //         // 4. BACKTRACK: Undo the choice to return to the previous state
    //         state.undoMove(i);
    //         moves.removeLast();
    //     }

    //     return false;
    // }
}