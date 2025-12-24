import java.util.ArrayList;

public class CubeSolver {

    private int stateCounter = 0;
    private ArrayList<Integer> moves = new ArrayList<>();

    public CubeSolver() {
    }

    public int getStateCounter() {
        return stateCounter;
    }

    public String getMoves() {
        String listOfMoves = "";
        for (int move : moves) {
            switch (move) {
                case 0 -> listOfMoves += "U ";
                case 1 -> listOfMoves += "U' ";
                case 2 -> listOfMoves += "D ";
                case 3 -> listOfMoves += "D' ";
                case 4 -> listOfMoves += "L ";
                case 5 -> listOfMoves += "L' ";
                case 6 -> listOfMoves += "R ";
                case 7 -> listOfMoves += "R' ";
                case 8 -> listOfMoves += "F ";
                case 9 -> listOfMoves += "F' ";
                case 10 -> listOfMoves += "B ";
                case 11 -> listOfMoves += "B' ";
                default -> {}
            }
        }
        return listOfMoves.trim();
    }

    public boolean solveAnyCube(Cube state, int maxDepth) {
        for (int depth = 1; depth <= maxDepth; depth++) {
            if (solveCube(state, depth, -1)) {
                return true;
            }
            System.out.println("Depth " + depth + " completed, states explored: " + stateCounter);
        }
        return false;
    }

    public boolean solveCube(Cube state, int depth, int lastMove) {

        if (stateCounter%1000000 == 0) {
            CubeUI.refresh();
        }


        // 1. BASE CASE: If the cube is solved, we are done
        if (state.isSolved()) {
            return true;
        }

        // 2. STOPPING CONDITION: Stop if we hit the maximum allowed search depth
        if (depth <= 0) {
            return false;
        }

        // 3. EXPLORE CHOICES: Iterate through all possible moves
        for (int i = 0; i < 12; i++) {

            // Avoid immediately reversing the previous move
            // (i ^ 1) flips the last bit to get the inverse move index (e.g., 0 <-> 1, 2 <-> 3, etc.)
            if (lastMove != -1 && i == (lastMove ^ 1)) {
                continue;
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
