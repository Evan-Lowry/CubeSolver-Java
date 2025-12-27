import java.util.Arrays;

public class Cube3D {

    private byte[][] corners = new byte[8][2];
    private byte[][] edges = new byte[12][2];

    public Cube3D() {

        for (int i = 0; i < 8; i++) {
            this.corners[i] = new byte[] {(byte) i, 0};
        }

        for (int i = 0; i < 12; i++) {
            this.edges[i] = new byte[] {(byte) i, 0};
        }
    }

    public byte[][] getCorners() {
        return this.corners;
    }

    public byte[][] getEdges() {
        return this.edges;
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
        for (int i = 0; i < 8; i++) {
            if (this.corners[i][0] != (byte) i || this.corners[i][1] != 0) {
                return false;
                
            }
        }
        for (int i = 0; i < 12; i++) {
            if (this.edges[i][0] != (byte) i || this.edges[i][1] != 0) {
                return false;
            }
        }
        return true;
    }

    public int getHeuristic() {
        return 0;
    }

    public byte[] getCube() {
        byte[] cube = new byte[54];

        for (int i = 0; i < 6; i++) {
            cube[9 * i + 4] = (byte) i;
        }

        for (int i = 0; i < 8; i++) {

            byte[] corner = this.corners[i];
            int[] index = indexCorners(corner);
            int rot = corner[1];

            cube[index[0]] = colorCorners(i, (0 + rot) % 3);
            cube[index[1]] = colorCorners(i, (1 + rot) % 3);
            cube[index[2]] = colorCorners(i, (2 + rot) % 3);
        }

        for (int i = 0; i < 12; i++) {

            byte[] edge = this.edges[i];
            int[] index = indexEdges(edge);
            int rot = edge[1];

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

    private int[] indexCorners(byte[] c) {
        int[] index = new int[3];

        switch (c[0]) {
            // for top corners of cube
            case 0: return new int[] {0, 9, 38};
            case 1: return new int[] {2, 36, 29};
            case 2: return new int[] {8, 27, 20};
            case 3: return new int[] {6, 18, 11};
            // for bottom corners of cube
            case 4: return new int[] {51, 44, 15};
            case 5: return new int[] {53, 35, 42};
            case 6: return new int[] {47, 26, 33};
            case 7: return new int[] {45, 17, 24};
            default: return index;
        }
    }

    private int[] indexEdges(byte[] e) {
        int[] index = new int[2];

        switch (e[0]) {
            // top edges
            case 0: return new int[] {1, 37};
            case 1:  return new int[] {5, 28};
            case 2:  return new int[] {7, 19};
            case 3: return new int[] {3, 10};

            // middle layer edges
            case 4: return new int[] {41, 12};
            case 5:  return new int[] {39, 32};
            case 6:   return new int[] {23, 30};
            case 7:  return new int[] {21, 14};

            // bottom edges
            case 8: return new int[] {52, 43};
            case 9:  return new int[] {50, 34};
            case 10:  return new int[] {46, 25};
            case 11: return new int[] {48, 16};

            default: return index;
        }
    }

    public void U() {
        for (byte[] corner : corners) {
            // if corner is in the top layer
            if (corner[0] < 4) corner[0] = (byte) ((corner[0] + 1) % 4);
        }
        for (byte[] edge : edges) {
            // if edge is in the top layer
            if (edge[0] < 4) edge[0] = (byte) ((edge[0] + 1) % 4);
        }
    }

    public void Up() {
        for (byte[] corner : corners) {
            // if corner is in the top layer
            if (corner[0] < 4) corner[0] = (byte) ((corner[0] - 1) % 4);
        }
        for (byte[] edge : edges) {
            // if edge is in the top layer
            if (edge[0] < 4) edge[0] = (byte) ((edge[0] - 1) % 4);
        }
    }

    public void U2() {
        for (byte[] corner : corners) {
            // if corner is in the top layer
            if (corner[0] < 4) corner[0] = (byte) ((corner[0] + 2) % 4);
        }
        for (byte[] edge : edges) {
            // if edge is in the top layer
            if (edge[0] < 4) edge[0] = (byte) ((edge[0] + 2) % 4);
        }
    }

    public void D() {
        for (byte[] corner : corners) {
            // if corner is in the bottom layer
            if (corner[0] > 3) corner[0] = (byte) ((corner[0] - 1) % 4);
        }
        for (byte[] edge : edges) {
            // if edge is in the bottom layer
            if (edge[0] > 7) edge[0] = (byte) ((edge[0] - 1) % 4);
        }
    }

    public void Dp() {
        for (byte[] corner : corners) {
            // if corner is in the bottom layer
            if (corner[0] > 3) corner[0] = (byte) ((corner[0] + 1) % 4);
        }
        for (byte[] edge : edges) {
            // if edge is in the bottom layer
            if (edge[0] > 7) edge[0] = (byte) ((edge[0] + 1) % 4);
        }
    }

    public void D2() {
        for (byte[] corner : corners) {
            // if corner is in the bottom layer
            if (corner[0] > 3) corner[0] = (byte) ((corner[0] + 2) % 4);
        }
        for (byte[] edge : edges) {
            // if edge is in the bottom layer
            if (edge[0] > 7) edge[0] = (byte) ((edge[0] + 2) % 4);
        }
    }

    public void R() {
        for (byte[] corner : corners) {
            // if corner is in right layer
            corner[0] = switch (corner[0]) {
                case 1 -> 2;
                case 2 -> 6;
                case 6 -> 5;
                case 5 -> 1;
                default -> corner[0];
            };
        }

        for (byte[] edge : edges) {
            // if edge is in right layer
            edge[0] = switch (edge[0]) {
                case 1 -> 5;
                case 5 -> 9;
                case 9 -> 6;
                case 6 -> 2;
                default -> edge[0];
            };
        }
    }

    public void Rp() {
        for (byte[] corner : corners) {
            // if corner is in right layer
            corner[0] = switch (corner[0]) {
                case 1 -> 2;
                case 2 -> 6;
                case 6 -> 5;
                case 5 -> 1;
                default -> corner[0];
            };
        }

        for (byte[] edge : edges) {
            // if edge is in right layer
            edge[0] = switch (edge[0]) {
                case 1 -> 5;
                case 5 -> 9;
                case 9 -> 6;
                case 6 -> 2;
                default -> edge[0];
            };
        }
    }

    public void R2() {
        for (byte[] corner : corners) {
            // if corner is in right layer
            corner[0] = switch (corner[0]) {
                case 1 -> 6;
                case 2 -> 5;
                case 6 -> 1;
                case 5 -> 2;
                default -> corner[0];
            };
        }
        for (byte[] edge : edges) {
            // if edge is in right layer
            edge[0] = switch (edge[0]) {
                case 1 -> 9;
                case 5 -> 6;
                case 9 -> 1;
                case 6 -> 5;
                default -> edge[0];
            };
        }
    }

    public void L() {
        for (byte[] corner : corners) {
            // if corner is in left layer
            corner[0] = switch (corner[0]) {
                case 0 -> 3;
                case 3 -> 7;
                case 7 -> 4;
                case 4 -> 0;
                default -> corner[0];
            };
        }

        for (byte[] edge : edges) {
            // if edge is in left layer
            edge[0] = switch (edge[0]) {
                case 3 -> 7;
                case 7 -> 11;
                case 11 -> 4;
                case 4 -> 3;
                default -> edge[0];
            };
        }
    }

    public void Lp() {
        for (byte[] corner : corners) {
            // if corner is in left layer
            corner[0] = switch (corner[0]) {
                case 0 -> 4;
                case 4 -> 7;
                case 7 -> 3;
                case 3 -> 0;
                default -> corner[0];
            };
        }

        for (byte[] edge : edges) {
            // if edge is in left layer
            edge[0] = switch (edge[0]) {
                case 3 -> 4;
                case 4 -> 11;
                case 11 -> 7;
                case 7 -> 3;
                default -> edge[0];
            };
        }
    }

    public void L2() {
        for (byte[] corner : corners) {
            // if corner is in left layer
            corner[0] = switch (corner[0]) {
                case 0 -> 7;
                case 3 -> 4;
                case 4 -> 3;
                case 7 -> 0;
                default -> corner[0];
            };
        }

        for (byte[] edge : edges) {
            // if edge is in left layer
            edge[0] = switch (edge[0]) {
                case 3 -> 11;
                case 4 -> 7;
                case 11 -> 3;
                case 7 -> 4;
                default -> edge[0];
            };
        }
    }

    public void F() {
        for (byte[] corner : corners) {
            // if corner is in front layer
            corner[0] = switch (corner[0]) {
                case 2 -> 6;
                case 3 -> 2;
                case 6 -> 7;
                case 7 -> 2;
                default -> corner[0];
            };
        }

        for (byte[] edge : edges) {
            // if edge is in front layer
            edge[0] = switch (edge[0]) {
                case 2 -> 6;
                case 6 -> 10;
                case 10 -> 7;
                case 7 -> 6;
                default -> edge[0];
            };
        }
    }

    public void Fp() {
        for (byte[] corner : corners) {
            // if corner is in front layer
            corner[0] = switch (corner[0]) {
                case 2 -> 3;
                case 3 -> 7;
                case 7 -> 6;
                case 6 -> 2;
                default -> corner[0];
            };
        }

        for (byte[] edge : edges) {
            // if edge is in front layer
            edge[0] = switch (edge[0]) {
                case 2 -> 7;
                case 6 -> 2;
                case 10 -> 6;
                case 7 -> 10;
                default -> edge[0];
            };
        }
    }

    public void F2() {
        for (byte[] corner : corners) {
            // if corner is in front layer
            corner[0] = switch (corner[0]) {
                case 2 -> 7;
                case 3 -> 6;
                case 6 -> 3;
                case 7 -> 2;
                default -> corner[0];
            };
        }

        for (byte[] edge : edges) {
            // if edge is in front layer
            edge[0] = switch (edge[0]) {
                case 2 -> 10;
                case 6 -> 7;
                case 10 -> 2;
                case 7 -> 6;
                default -> edge[0];
            };
        }
    }

    public void B() {
        for (byte[] corner : corners) {
            corner[0] = switch (corner[0]) {
                case 0 -> 4;
                case 1 -> 0;
                case 4 -> 5;
                case 5 -> 1;
                default -> corner[0];
            };
        }

        for (byte[] edge : edges) {
            edge[0] = switch (edge[0]) {
                case 0 -> 4;
                case 4 -> 8;
                case 8 -> 5;
                case 5 -> 0;
                default -> edge[0];
            };
        }
    }

    public void Bp() {
        for (byte[] corner : corners) {
            corner[0] = switch (corner[0]) {
                case 0 -> 1;
                case 1 -> 5;
                case 4 -> 0;
                case 5 -> 4;
                default -> corner[0];
            };
        }

        for (byte[] edge : edges) {
            edge[0] = switch (edge[0]) {
                case 0 -> 5;
                case 4 -> 0;
                case 8 -> 4;
                case 5 -> 8;
                default -> edge[0];
            };
        }
    }

    public void B2() {
        for (byte[] corner : corners) {
            corner[0] = switch (corner[0]) {
                case 0 -> 5;
                case 1 -> 4;
                case 4 -> 1;
                case 5 -> 0;
                default -> corner[0];
            };
        }

        for (byte[] edge : edges) {
            edge[0] = switch (edge[0]) {
                case 0 -> 8;
                case 4 -> 5;
                case 8 -> 0;
                case 5 -> 4;
                default -> edge[0];
            };
        }
    }
}