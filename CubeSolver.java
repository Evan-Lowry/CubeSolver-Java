import java.util.ArrayList;

public class CubeSolver {

    private long stateCounter = 0L;
    private ArrayList<Integer> moves = new ArrayList<>();

    public CubeSolver() {
    }

    public long getStateCounter() {
        return stateCounter;
    }

        public String getMoves() {
        StringBuilder sb = new StringBuilder();
        String[] faces = {"U", "D", "L", "R", "F", "B"};
        int i = 0;
        while (i < moves.size()) {
            int first = moves.get(i);
            int face = first / 2; // 0..5
            int j = i;
            int net = 0; // net quarter-turns clockwise
            // accumulate consecutive moves on the same face
            while (j < moves.size() && moves.get(j) / 2 == face) {
                int mv = moves.get(j);
                net += (mv % 2 == 0) ? 1 : -1; // even = clockwise, odd = counter-clockwise
                j++;
            }
            net = ((net % 4) + 4) % 4; // normalize to 0..3
            if (net == 1) {
                sb.append(faces[face]).append(" ");
            } else if (net == 2) {
                sb.append(faces[face]).append("2 ");
            } else if (net == 3) {
                sb.append(faces[face]).append("' ");
            }
            i = j;
        }
        return sb.toString().trim();
    }



    // public String getMoves() {
    //     String listOfMoves = "";
    //     for (int move : moves) {
    //         switch (move) {
    //             case 0 -> listOfMoves += "U ";
    //             case 1 -> listOfMoves += "U' ";
    //             case 2 -> listOfMoves += "D ";
    //             case 3 -> listOfMoves += "D' ";
    //             case 4 -> listOfMoves += "L ";
    //             case 5 -> listOfMoves += "L' ";
    //             case 6 -> listOfMoves += "R ";
    //             case 7 -> listOfMoves += "R' ";
    //             case 8 -> listOfMoves += "F ";
    //             case 9 -> listOfMoves += "F' ";
    //             case 10 -> listOfMoves += "B ";
    //             case 11 -> listOfMoves += "B' ";
    //             default -> {}
    //         }
    //     }
    //     return listOfMoves.trim();
    // }

    public boolean solveAnyCube(Cube state, int maxDepth) {
        for (int depth = 1; depth <= maxDepth; depth++) {
            if (solveCube(state, depth, -1)) {
                return true;
            }
        }
        return false;
    }

    public boolean solveCube(Cube state, int depth, int lastMove) {

        // 1. BASE CASE: If the cube is solved, we are done
        if (state.isSolved()) return true;

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

            moves.add(i);

            stateCounter++;

            // Recurse: Move to the next level
            if (solveCube(state, depth - 1, i)) {
                return true;
            }

            // 4. BACKTRACK: Undo the choice to return to the previous state
            state.undoMove(i);

            moves.remove(moves.size() - 1);
        }

        return false;
    }
}
