import java.util.Arrays;

public class CubeMatrix {

    private Corner[] corners = new Corner[8];
    private Edge[] edges = new Edge[12];

    public CubeMatrix() {
        // creates top face
        this.corners[0] = new Corner(-1, 1, -1, 0);
        this.corners[1] = new Corner(1, 1, -1, 0);
        this.corners[2] = new Corner(1, 1, 1, 0);
        this.corners[3] = new Corner(-1, 1, 1, 0);

        // creates bottom face
        this.corners[4] = new Corner(-1, -1, -1, 0);
        this.corners[5] = new Corner(1, -1, -1, 0);
        this.corners[6] = new Corner(1, -1, 1, 0);
        this.corners[7] = new Corner(-1, -1, 1, 0);

        //creates top face
        this.edges[0] = new Edge(0, 1, -1, 0);
        this.edges[1] = new Edge(1, 1, 0, 0);
        this.edges[2] = new Edge(0, 1, 1, 0);
        this.edges[3] = new Edge(-1, 1, 0, 0);

        //creates middle layer
        this.edges[4] = new Edge(-1, 0, -1, 0);
        this.edges[5] = new Edge(1, 0, -1, 0);
        this.edges[6] = new Edge(1, 0, 1, 0);
        this.edges[7] = new Edge(-1, 0, 1, 0);

        //creates bottom face
        this.edges[8] = new Edge(0, -1, -1, 0);
        this.edges[9] = new Edge(1, -1, 0, 0);
        this.edges[10] = new Edge(0, -1, 1, 0);
        this.edges[11] = new Edge(-1, -1, 0, 0);
    }

    public Corner[] getCorners() {
        return this.corners;
    }

    public Edge[] getEdges() {
        return this.edges;
    }

    public byte[] getCube() {
        byte[] cubeState = new byte[54];

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 9; j++) {
                cubeState[9*i+j] = (byte) i;
            }
        }

        return cubeState;
    }

    public void performMoves(String moves) {

        if (moves == null || moves.isEmpty()) {
            return;
        }

        String[] moveList = moves.split(" ");

        for (String move : moveList) {
            switch (move) {
                case "F" -> F();
                case "F'" -> Fp();
                case "F2" -> F2();
                case "B" -> B();
                case "B'" -> Bp();
                case "B2" -> B2();
                case "R" -> R();
                case "R'" -> Rp();
                case "R2" -> R2();
                case "L" -> L();
                case "L'" -> Lp();
                case "L2" -> L2();
                case "U" -> U();
                case "U'" -> Up();
                case "U2" -> U2();
                case "D" -> D();
                case "D'" -> Dp();
                case "D2" -> D2();
                default -> System.out.println("Invalid move: " + move);
            }
        }
    }

    public void applyMove(int move) {
        switch (move) {
            case 0 -> U();
            case 1 -> Up();
            case 2 -> D();
            case 3 -> Dp();
            case 4 -> L();
            case 5 -> Lp();
            case 6 -> R();
            case 7 -> Rp();
            case 8 -> F();
            case 9 -> Fp();
            case 10 -> B();
            case 11 -> Bp();
            default -> throw new IllegalArgumentException("Invalid move: " + move);
        }
    }

    public void undoMove(int move) {
        switch (move) {
            case 0 -> Up();
            case 1 -> U();
            case 2 -> Dp();
            case 3 -> D();
            case 4 -> Lp();
            case 5 -> L();
            case 6 -> Rp();
            case 7 -> R();
            case 8 -> Fp();
            case 9 -> F();
            case 10 -> Bp();
            case 11 -> B();
            default -> throw new IllegalArgumentException("Invalid move: " + move);
        }
    }

    public boolean isSolved() {
        for (Corner corner : corners) {
            if (!corner.isSolved()) {
                return false;
            }
        }

        for (Edge edge : edges) {
            if (!edge.isSolved()) {
                return false;
            }
        }

        return true;
    }

    public int getHeuristic() {
        return 0;
    }

    public byte[] toByteArray() {
        byte[] cube = new byte[54];

        for (int i = 0; i < 6; i++) {
            cube[9 * i + 4] = (byte) i;
        }

        for (int i = 0; i < 8; i++) {

            Corner corner = this.corners[i];
            int[] index = indexCorners(corner);
            int rot = corner.getRot();

            cube[index[0]] = colorCorners(i, (0 + rot) % 3);
            cube[index[1]] = colorCorners(i, (1 + rot) % 3);
            cube[index[2]] = colorCorners(i, (2 + rot) % 3);
        }

        for (int i = 0; i < 12; i++) {

            Edge edge = this.edges[i];
            int[] index = indexEdges(edge);
            int rot = edge.getRot();

            cube[index[0]] = colorEdges(i, (0 + rot) % 2);
            cube[index[1]] = colorEdges(i, (1 + rot) % 2);
        }

        return cube;
    }

    private byte colorCorners(int n, int c) {
        byte[] tileColors0 = {0, 0, 0, 0, 5, 5, 5, 5};
        byte[] tileColors1 = {1, 4, 3, 2, 4, 3, 2, 1};
        byte[] tileColors2 = {4, 3, 2, 1, 1, 4, 3, 2};

        switch (c) {
            case 0: return tileColors0[n];
            case 1: return tileColors1[n];
            case 2: return tileColors2[n];
            default: return (byte) -1;
        }
    }

    private byte colorEdges(int n, int c) {
        byte[] tileColors0 = {0, 0, 0, 0, 4, 4, 2, 2, 5, 5, 5, 5};
        byte[] tileColors1 = {4, 3, 2, 1, 1, 3, 3, 1, 4, 3, 2, 1};

        return switch (c) {
            case 0 -> tileColors0[n];
            case 1 -> tileColors1[n];
            default -> (byte) -1;
        };
    }

    private int[] indexCorners(Cubit c) {
        int[] index = new int[3];

        // for top corners of cube
        int x = c.getX();
        int y = c.getY();
        int z = c.getZ();

        switch (x + " " + y + " " + z) {
            // for top corners of cube
            case "-1 1 -1": return new int[] {0, 9, 38};
            case "1 1 -1": return new int[] {2, 36, 29};
            case "1 1 1": return new int[] {8, 27, 20};
            case "-1 1 1": return new int[] {6, 18, 11};
            // for bottom corners of cube
            case "-1 -1 -1": return new int[] {51, 44, 15};
            case "1 -1 -1": return new int[] {53, 35, 42};
            case "1 -1 1":  return new int[] {47, 26, 33};
            case "-1 -1 1": return new int[] {45, 17, 24};
            default: return index;
        }
    }

    private int[] indexEdges(Cubit c) {
        int[] index = new int[2];

        int x = c.getX();
        int y = c.getY();
        int z = c.getZ();

        switch (x + " " + y + " " + z) {
            // top edges
            case "0 1 -1": return new int[] {1, 37};
            case "1 1 0":  return new int[] {5, 28};
            case "0 1 1":  return new int[] {7, 19};
            case "-1 1 0": return new int[] {3, 10};

            // middle layer edges
            case "-1 0 -1": return new int[] {41, 12};
            case "1 0 -1":  return new int[] {39, 32};
            case "1 0 1":   return new int[] {23, 30};
            case "-1 0 1":  return new int[] {21, 14};

            // bottom edges
            case "0 -1 -1": return new int[] {52, 43};
            case "1 -1 0":  return new int[] {50, 34};
            case "0 -1 1":  return new int[] {46, 25};
            case "-1 -1 0": return new int[] {48, 16};

            default: return index;
        }
    }

    public void U() {
        for (Corner cubit : corners) {
            cubit.U();
        }
        for (Edge cubit : edges) {
            cubit.U();
        }
    }

    public void Up() {
        for (Corner cubit : corners) {
            cubit.Up();
        }
        for (Edge cubit : edges) {
            cubit.Up();
        }
    }

    public void U2() {
        for (Corner cubit : corners) {
            cubit.U();
            cubit.U();
        }
        for (Edge cubit : edges) {
            cubit.U();
            cubit.U();
        }
    }

    public void D() {
        for (Corner cubit : corners) {
            cubit.D();
        }
        for (Edge cubit : edges) {
            cubit.D();
        }
    }

    public void Dp() {
        for (Corner cubit : corners) {
            cubit.Dp();
        }
        for (Edge cubit : edges) {
            cubit.Dp();
        }
    }

    public void D2() {
        for (Corner cubit : corners) {
            cubit.D();
            cubit.D();
        }
        for (Edge cubit : edges) {
            cubit.D();
            cubit.D();
        }
    }

    public void R() {
        for (Corner cubit : corners) {
            cubit.R();
        }
        for (Edge cubit : edges) {
            cubit.R();
        }
    }

    public void Rp() {
        for (Corner cubit : corners) {
            cubit.Rp();
        }
        for (Edge cubit : edges) {
            cubit.Rp();
        }
    }

    public void R2() {
        for (Corner cubit : corners) {
            cubit.R();
            cubit.R();
        }
        for (Edge cubit : edges) {
            cubit.R();
            cubit.R();
        }
    }

    public void L() {
        for (Corner cubit : corners) {
            cubit.L();
        }
        for (Edge cubit : edges) {
            cubit.L();
        }
    }

    public void Lp() {
        for (Corner cubit : corners) {
            cubit.Lp();
        }
        for (Edge cubit : edges) {
            cubit.Lp();
        }
    }

    public void L2() {
        for (Corner cubit : corners) {
            cubit.L();
            cubit.L();
        }
        for (Edge cubit : edges) {
            cubit.L();
            cubit.L();
        }
    }

    public void F() {
        for (Corner cubit : corners) {
            cubit.F();
        }
        for (Edge cubit : edges) {
            cubit.F();
        }
    }

    public void Fp() {
        for (Corner cubit : corners) {
            cubit.Fp();
        }
        for (Edge cubit : edges) {
            cubit.Fp();
        }
    }

    public void F2() {
        for (Corner cubit : corners) {
            cubit.F();
            cubit.F();
        }
        for (Edge cubit : edges) {
            cubit.F();
            cubit.F();
        }
    }

    public void B() {
        for (Corner cubit : corners) {
            cubit.B();
        }
        for (Edge cubit : edges) {
            cubit.B();
        }
    }

    public void Bp() {
        for (Corner cubit : corners) {
            cubit.Bp();
        }
        for (Edge cubit : edges) {
            cubit.Bp();
        }
    }

    public void B2() {
        for (Corner cubit : corners) {
            cubit.B();
            cubit.B();
        }
        for (Edge cubit : edges) {
            cubit.B();
            cubit.B();
        }
    }
}