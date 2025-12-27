public class Cube3D {

    private byte[] cornerPos = new byte[8];
    private byte[] cornerOri = new byte[8];
    private byte[] edgePos = new byte[12];
    private byte[] edgeOri = new byte[12];

    

    public Cube3D() {

        for (byte i = 0; i < 8; i++) {
            this.cornerPos[i] = i;
            this.cornerOri[i] = 0;
        }

        for (byte i = 0; i < 12; i++) {
            this.edgePos[i] = i;
            this.edgeOri[i] = 0;
        }
    }

    public byte[] getCorners() {
        return this.cornerPos;
    }

    public byte[] getEdges() {
        return this.edgePos;
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
        for (byte i = 0; i < 8; i++) {
            if (this.cornerPos[i] !=  i || this.cornerOri[i] != 0) {
                return false;
            }
        }
        for (byte i = 0; i < 12; i++) {
            if (this.edgePos[i] !=  i || this.edgeOri[i] != 0) {
                return false;
            }
        }
        return true;
    }

    public byte getHeuristic() {
        return 0;
    }

    public byte[] getCube() {
        byte[] cube = new byte[54];

        for (int i = 0; i < 6; i++) {
            cube[9 * i + 4] =  (byte) i;
        }

        for (int i = 0; i < 8; i++) {

            byte cp = this.cornerPos[i];
            byte co = this.cornerOri[i];
            byte[] index = indexCorners(cp);
            byte rot = co;

            cube[index[0]] = colorCorners(i, (byte) ((0 + rot) % 3));
            cube[index[1]] = colorCorners(i, (byte) ((1 + rot) % 3));
            cube[index[2]] = colorCorners(i, (byte) ((2 + rot) % 3));
        }

        for (int i = 0; i < 12; i++) {

            byte edge = this.edgePos[i];
            byte eo = this.edgeOri[i];
            byte[] index = indexEdges(edge);
            byte rot = eo;

            cube[index[0]] = colorEdges(i, (byte) ((0 + rot) % 2));
            cube[index[1]] = colorEdges(i, (byte) ((1 + rot) % 2));
        }

        return cube;
    }

    private byte colorCorners(int n, byte c) {
        byte[] tileColors0 = {0, 0, 0, 0, 5, 5, 5, 5};
        byte[] tileColors1 = {1, 4, 3, 2, 4, 3, 2, 1};
        byte[] tileColors2 = {4, 3, 2, 1, 1, 4, 3, 2};

        switch (c) {
            case 0: return tileColors0[n];
            case 1: return tileColors1[n];
            case 2: return tileColors2[n];
            default: return -1;
        }
    }

    private byte colorEdges(int n, byte c) {
        byte[] tileColors0 = {0, 0, 0, 0, 4, 4, 2, 2, 5, 5, 5, 5};
        byte[] tileColors1 = {4, 3, 2, 1, 1, 3, 3, 1, 4, 3, 2, 1};

        return switch (c) {
            case 0 -> tileColors0[n];
            case 1 -> tileColors1[n];
            default ->  -1;
        };
    }

    private byte[] indexCorners(byte c) {
        byte[] index = new byte[3];

        switch (c) {
            // for top corners of cube
            case 0: return new byte[] {0, 9, 38};
            case 1: return new byte[] {2, 36, 29};
            case 2: return new byte[] {8, 27, 20};
            case 3: return new byte[] {6, 18, 11};
            // for bottom corners of cube
            case 4: return new byte[] {51, 44, 15};
            case 5: return new byte[] {53, 35, 42};
            case 6: return new byte[] {47, 26, 33};
            case 7: return new byte[] {45, 17, 24};
            default: return index;
        }
    }

    private byte[] indexEdges(byte e) {
        byte[] index = new byte[2];

        switch (e) {
            // top edges
            case 0: return new byte[] {1, 37};
            case 1:  return new byte[] {5, 28};
            case 2:  return new byte[] {7, 19};
            case 3: return new byte[] {3, 10};

            // middle layer edges
            case 4: return new byte[] {41, 12};
            case 5:  return new byte[] {39, 32};
            case 6:   return new byte[] {23, 30};
            case 7:  return new byte[] {21, 14};

            // bottom edges
            case 8: return new byte[] {52, 43};
            case 9:  return new byte[] {50, 34};
            case 10:  return new byte[] {46, 25};
            case 11: return new byte[] {48, 16};

            default: return index;
        }
    }

    public void U() {
        // rotate corner positions 0->1->2->3
        byte t = cornerPos[0];
        cornerPos[0] = cornerPos[1];
        cornerPos[1] = cornerPos[2];
        cornerPos[2] = cornerPos[3];
        cornerPos[3] = t;

        // rotate edge positions 0->1->2->3
        t = edgePos[0];
        edgePos[0] = edgePos[1];
        edgePos[1] = edgePos[2];
        edgePos[2] = edgePos[3];
        edgePos[3] = t;
    }

    public void Up() {
        for (byte[] corner : corners) {
            // if corner is in the top layer
            if (corner[0] < 4) corner[0] = (byte) ((corner[0] + 3) % 4);
        }
        for (byte[] edge : edges) {
            // if edge is in the top layer
            if (edge[0] < 4) edge[0] = (byte) ((edge[0] + 3) % 4);
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
            if (corner[0] >= 4) corner[0] = (byte) (((corner[0] + 3) % 4) + 4);
        }
        for (byte[] edge : edges) {
            // if edge is in the bottom layer
            if (edge[0] >= 8) edge[0] = (byte) (((edge[0] + 3) % 4) + 8);
        }
    }

    public void Dp() {
        for (byte[] corner : corners) {
            // if corner is in the bottom layer
            if (corner[0] > 3) corner[0] = (byte) (((corner[0] + 1) % 4) + 4);
        }
        for (byte[] edge : edges) {
            // if edge is in the bottom layer
            if (edge[0] > 7) edge[0] = (byte) (((edge[0] + 1) % 4) + 8);
        }
    }

    public void D2() {
        for (byte[] corner : corners) {
            // if corner is in the bottom layer
            if (corner[0] > 3) corner[0] = (byte) (((corner[0] + 2) % 4) + 4);
        }
        for (byte[] edge : edges) {
            // if edge is in the bottom layer
            if (edge[0] > 7) edge[0] = (byte) (((edge[0] + 2) % 4) + 8);
        }
    }

    public void R() {

        for (byte[] corner : corners) {
            // if corner is in right layer
            switch (corner[0]) {
            case 1 -> { corner[0] = 5; corner[1] = (byte) ((corner[1] + 1) % 3); }
            case 2 -> { corner[0] = 1; corner[1] = (byte) ((corner[1] + 2) % 3); }
            case 6 -> { corner[0] = 2; corner[1] = (byte) ((corner[1] + 1) % 3); }
            case 5 -> { corner[0] = 6; corner[1] = (byte) ((corner[1] + 2) % 3); }
            default -> { }
            }
        }

        for (byte[] edge : edges) {
            // if edge is in right layer
            edge[0] = switch (edge[0]) {
                case 1 -> 5;
                case 5 -> 9;
                case 9 -> 6;
                case 6 -> 1;
                default -> edge[0];
            };
        }
    }

    public void Rp() {
        for (byte[] corner : corners) {
            // if corner is in right layer
            switch (corner[0]) {
            case 1 -> { corner[0] = 2; corner[1] = (byte) ((corner[1] + 1) % 3); }
            case 2 -> { corner[0] = 6; corner[1] = (byte) ((corner[1] + 2) % 3); }
            case 6 -> { corner[0] = 5; corner[1] = (byte) ((corner[1] + 1) % 3); }
            case 5 -> { corner[0] = 1; corner[1] = (byte) ((corner[1] + 2) % 3); }
            default -> { }
            }
        }

        for (byte[] edge : edges) {
            // if edge is in right layer
            edge[0] = switch (edge[0]) {
                case 1 -> 6;
                case 5 -> 1;
                case 9 -> 5;
                case 6 -> 9;
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
            switch (corner[0]) {
            case 0 -> { corner[0] = 3; corner[1] = (byte) ((corner[1] + 2) % 3); }
            case 3 -> { corner[0] = 7; corner[1] = (byte) ((corner[1] + 1) % 3); }
            case 7 -> { corner[0] = 4; corner[1] = (byte) ((corner[1] + 2) % 3); }
            case 4 -> { corner[0] = 0; corner[1] = (byte) ((corner[1] + 1) % 3); }
            default -> { }
            }
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
            switch (corner[0]) {
            case 0 -> { corner[0] = 4; corner[1] = (byte) ((corner[1] + 2) % 3); }
            case 4 -> { corner[0] = 7; corner[1] = (byte) ((corner[1] + 1) % 3); }
            case 7 -> { corner[0] = 3; corner[1] = (byte) ((corner[1] + 2) % 3); }
            case 3 -> { corner[0] = 0; corner[1] = (byte) ((corner[1] + 1) % 3); }
            default -> { }
            }
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
            switch (corner[0]) {
                case 2 -> { corner[0] = 6; corner[1] = (byte) ((corner[1] + 1) % 3); }
                case 3 -> { corner[0] = 2; corner[1] = (byte) ((corner[1] + 2) % 3); }
                case 6 -> { corner[0] = 7; corner[1] = (byte) ((corner[1] + 2) % 3); }
                case 7 -> { corner[0] = 3; corner[1] = (byte) ((corner[1] + 1) % 3); }
                default -> { }
            }
        }

        for (byte[] edge : edges) {
            // if edge is in front layer
            switch (edge[0]) {
            case 2 -> { edge[0] = 6; edge[1] = (byte) (edge[1] ^ 1); }
            case 6 -> { edge[0] = 10; edge[1] = (byte) (edge[1] ^ 1); }
            case 10 -> { edge[0] = 7; edge[1] = (byte) (edge[1] ^ 1); }
            case 7 -> { edge[0] = 2; edge[1] = (byte) (edge[1] ^ 1); }
            default -> { }
            }
        }
    }

    public void Fp() {
        for (byte[] corner : corners) {
            // if corner is in front layer (inverse of F)
            switch (corner[0]) {
                case 2 -> { corner[0] = 3; corner[1] = (byte) ((corner[1] + 1) % 3); }
                case 3 -> { corner[0] = 7; corner[1] = (byte) ((corner[1] + 2) % 3); }
                case 7 -> { corner[0] = 6; corner[1] = (byte) ((corner[1] + 1) % 3); }
                case 6 -> { corner[0] = 2; corner[1] = (byte) ((corner[1] + 2) % 3); }
                default -> { }
            }
        }

        for (byte[] edge : edges) {
            // if edge is in front layer (inverse of F)
            switch (edge[0]) {
                case 2 -> { edge[0] = 7; edge[1] = (byte) (edge[1] ^ 1); }
                case 6 -> { edge[0] = 2; edge[1] = (byte) (edge[1] ^ 1); }
                case 10 -> { edge[0] = 6; edge[1] = (byte) (edge[1] ^ 1); }
                case 7 -> { edge[0] = 10; edge[1] = (byte) (edge[1] ^ 1); }
                default -> { }
            }
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
            // if corner is in back layer
            switch (corner[0]) {
                case 0 -> { corner[0] = 4; corner[1] = (byte) ((corner[1] + 1) % 3); }
                case 1 -> { corner[0] = 0; corner[1] = (byte) ((corner[1] + 2) % 3); }
                case 4 -> { corner[0] = 5; corner[1] = (byte) ((corner[1] + 2) % 3); }
                case 5 -> { corner[0] = 1; corner[1] = (byte) ((corner[1] + 1) % 3); }
                default -> { }
            }
        }

        for (byte[] edge : edges) {
            // if edge is in back layer
            switch (edge[0]) {
                case 0 -> { edge[0] = 4; edge[1] = (byte) (edge[1] ^ 1); }
                case 4 -> { edge[0] = 8; edge[1] = (byte) (edge[1] ^ 1); }
                case 8 -> { edge[0] = 5; edge[1] = (byte) (edge[1] ^ 1); }
                case 5 -> { edge[0] = 0; edge[1] = (byte) (edge[1] ^ 1); }
                default -> { }
            }
        }
    }

    public void Bp() {
        for (byte[] corner : corners) {
            // inverse of B: move corners back and undo orientation
            switch (corner[0]) {
                case 0 -> { corner[0] = 1; corner[1] = (byte) ((corner[1] + 1) % 3); }
                case 1 -> { corner[0] = 5; corner[1] = (byte) ((corner[1] + 2) % 3); }
                case 4 -> { corner[0] = 0; corner[1] = (byte) ((corner[1] + 2) % 3); }
                case 5 -> { corner[0] = 4; corner[1] = (byte) ((corner[1] + 1) % 3); }
                default -> { }
            }
        }

        for (byte[] edge : edges) {
            // inverse of B: move edges back and undo flip (XOR again)
            switch (edge[0]) {
                case 0 -> { edge[0] = 5; edge[1] = (byte) (edge[1] ^ 1); }
                case 4 -> { edge[0] = 0; edge[1] = (byte) (edge[1] ^ 1); }
                case 8 -> { edge[0] = 4; edge[1] = (byte) (edge[1] ^ 1); }
                case 5 -> { edge[0] = 8; edge[1] = (byte) (edge[1] ^ 1); }
                default -> { }
            }
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