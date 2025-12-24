public class CubeSolver {

    int stateCounter = 0;

    public CubeSolver() {
    }

    public int getStateCounter() {
        return stateCounter;
    }

    public boolean solveCube(Cube state, int depth) {


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
            // Apply the choice
            state.applyMove(i);

            stateCounter++;

            // Recurse: Move to the next level
            if (solveCube(state, depth - 1)) {
                return true;
            }

            // 4. BACKTRACK: Undo the choice to return to the previous state
            state.undoMove(i);
        }

        return false;
    }
}
