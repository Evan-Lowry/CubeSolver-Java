import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class CubeSolver {

    private long stateCounter = 0L;
    private IntStack moves = new IntStack();
    private ArrayList<byte[]> states;
    private ArrayList<IntStack> moveLists;
    private Cube placeHolderCube = new Cube();

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

    public boolean solveAnyCube(Cube cube, int maxDepth) {
        this.states = new ArrayList<>();
        this.states.add(cube.getCube().clone());
        this.moveLists = new ArrayList<>();
        this.moveLists.add(new IntStack());

        for (int depth = 1; depth <= maxDepth; depth++) {
            ArrayList<byte[]> newStates = new ArrayList<>();
            ArrayList<IntStack> newMoveLists = new ArrayList<>();
            for (int i = 0; i < this.states.size(); i++) {
                byte[] s = this.states.get(i);
                IntStack ml = this.moveLists.get(i);
                if (makeMove(s, ml, newStates, newMoveLists)) {
                    this.moves = ml;
                    return true;
                }
            }
            this.states = newStates;
            this.moveLists = newMoveLists;
        }

        for (int i = 0; i < this.states.size(); i++) {
            byte[] ns = this.states.get(i);
            IntStack nml = this.moveLists.get(i);
            if (isSolved(ns)) {
                this.moves = nml;
                return true;
            }
        }
        return false;
    }

    public boolean makeMove(byte[] state, IntStack moveList, ArrayList<byte[]> newStates, ArrayList<IntStack> newMoveLists) {

        // 1. BASE CASE: If the cube is solved, we are done
        if (isSolved(state)) return true;

        // 3. EXPLORE CHOICES: Iterate through all possible moves
        for (int i = 0; i < 12; i++) {

            byte[] newState = state.clone();
            applyMove(newState, i);

            IntStack newMoveList = moveList.copy();
            newMoveList.push(i);

            newStates.add(newState);
            newMoveLists.add(newMoveList);

            stateCounter++;
        }

        return false;
    }

    private boolean isSolved(byte[] cube) {
        for (int face = 0; face < 6; face++) {
            byte color = cube[face * 9 + 4]; // center piece color
            for (int i = 0; i < 9; i++) {
                if (cube[face * 9 + i] != color) {
                    return false;
                }
            }
        }
        return true;
    }

    private void applyMove(byte[] cube, int move) {
        placeHolderCube.setCube(cube);
        placeHolderCube.applyMove(move);
        byte[] newCube = placeHolderCube.getCube();
        System.arraycopy(newCube, 0, cube, 0, 54);
    }

    public boolean solveCube(Cube state, int depth) {

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

            moves.push(i);

            stateCounter++;

            // seen.put(state.getString(), stateCounter);

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
}