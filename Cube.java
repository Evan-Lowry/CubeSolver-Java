public class Cube {

    private byte[] cube = new byte[54];
    byte t;

    public Cube() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 9; j++) {
                this.cube[9*i+j] = (byte) (i);
            }
        }
    }

    public String toString() {

        String output = "";

        for (int i = 0; i < 3; i++) {
            output += "         ";
            for (int j = 0; j < 3; j++) {
                output += "[" + this.cube[i*3 + j] + "]";
            }
            output += "\n";
        }

        for (int i = 3; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 3; k++) {
                    output += "[" + this.cube[j*9 + i*3 + k] + "]";
                }
            }
            output += "\n";
        }

        for (int i = 15; i < 18; i++) {
            output += "         ";
            for (int j = 0; j < 3; j++) {
                output += "[" + this.cube[i*3 + j] + "]";
            }
            output += "\n";
        }

        return output;
    }

    public byte[] getCube() {
        return this.cube;
    }

    public void setCube(byte[] newCube) {
        this.cube = newCube;
    }

    public int getHeuristic() {
        int misplaced = 0;
        for (int i = 0; i < 6; i++) {
            byte color = this.cube[i * 9 + 4]; // center piece color
            for (int j = 0; j < 9; j++) {
                if (this.cube[i * 9 + j] != color) {
                    misplaced++;
                }
            }
        }
        return misplaced;
    }

    public boolean isSolved() {
        for (int i = 0; i < 6; i++) {
            byte color = this.cube[i * 9 + 4]; // center piece color
            for (int j = 0; j < 9; j++) {
                if (this.cube[i * 9 + j] != color) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isOrientated() {

        // Checks if all top stickers are correctly orientated
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 9; j++) {
                if (cube[i * 45 + j] != 0 && cube[i * 45 + j] != 5) return false;
            }
        }

        // Checks if all middle layer edge pieces are correctly orientated
        if (cube[12] != 1 && cube[12] != 3) return false; // Left face edges
        if (cube[14] != 1 && cube[14] != 3) return false; // Left face edges
        if (cube[30] != 1 && cube[30] != 3) return false; // Right face edges
        if (cube[32] != 1 && cube[32] != 3) return false; // Right face edges

        return true;
    }

    public void performMoves(String string) {
        String[] moves = string.split(" ");
        for (String move : moves) {
            switch (move) {
                case "U" -> U();
                case "U'" -> Up();
                case "U2" -> U2();
                case "D" -> D();
                case "D'" -> Dp();
                case "D2" -> D2();
                case "L" -> L();
                case "L'" -> Lp();
                case "L2" -> L2();
                case "R" -> R();
                case "R'" -> Rp();
                case "R2" -> R2();
                case "F" -> F();
                case "F'" -> Fp();
                case "F2" -> F2();
                case "B" -> B();
                case "B'" -> Bp();
                case "B2" -> B2();
                default -> throw new IllegalArgumentException("Invalid move: " + move);
            }
        }
    }

    public void applyMove(int i) {
        switch (i) {
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

    public void undoMove(int i) {
        switch (i) {
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
        }
    }

    // public void applyMove(int i) {
    //     switch (i) {
    //         case 0 -> U();
    //         case 1 -> Up();
    //         case 2 -> U2();
    //         case 3 -> D();
    //         case 4 -> Dp();
    //         case 5 -> D2();
    //         case 6 -> L();
    //         case 7 -> Lp();
    //         case 8 -> L2();
    //         case 9 -> R();
    //         case 10 -> Rp();
    //         case 11 -> R2();
    //         case 12 -> F();
    //         case 13 -> Fp();
    //         case 14 -> F2();
    //         case 15 -> B();
    //         case 16 -> Bp();
    //         case 17 -> B2();
    //     }
    // }

    // public void undoMove(int i) {
    //     switch (i) {
    //         case 0 -> Up();
    //         case 1 -> U();
    //         case 2 -> U2();
    //         case 3 -> Dp();
    //         case 4 -> D();
    //         case 5 -> D2();
    //         case 6 -> Lp();
    //         case 7 -> L();
    //         case 8 -> L2();
    //         case 9 -> Rp();
    //         case 10 -> R();
    //         case 11 -> R2();
    //         case 12 -> Fp();
    //         case 13 -> F();
    //         case 14 -> F2();
    //         case 15 -> Bp();
    //         case 16 -> B();
    //         case 17 -> B2();
    //     }
    // }

    public void F() {
        // Rotate front face clockwise (two 4-cycles)
        t = this.cube[18];
        this.cube[18] = this.cube[24];
        this.cube[24] = this.cube[26];
        this.cube[26] = this.cube[20];
        this.cube[20] = t;

        t = this.cube[19];
        this.cube[19] = this.cube[21];
        this.cube[21] = this.cube[25];
        this.cube[25] = this.cube[23];
        this.cube[23] = t;

        // Rotate adjacent edges: U -> R -> D -> L -> U (performed per column)
        for (int j = 0; j < 3; j++) {
            int U = 6 + j;         // U bottom row indices 6,7,8
            int R = 27 + 3 * j;    // R left column indices 27,30,33
            int D = 47 - j;        // D top row (reversed) indices 47,46,45
            int L = 17 - 3 * j;    // L right column indices 17,14,11

            t = this.cube[U];
            this.cube[U] = this.cube[L];
            this.cube[L] = this.cube[D];
            this.cube[D] = this.cube[R];
            this.cube[R] = t;
        }
    }

    public void Fp() {
        // Rotate front face counter-clockwise (two 4-cycles)
        t = this.cube[18];
        this.cube[18] = this.cube[20];
        this.cube[20] = this.cube[26];
        this.cube[26] = this.cube[24];
        this.cube[24] = t;

        t = this.cube[19];
        this.cube[19] = this.cube[23];
        this.cube[23] = this.cube[25];
        this.cube[25] = this.cube[21];
        this.cube[21] = t;

        // Rotate adjacent edges (reverse of F)
        for (int j = 0; j < 3; j++) {
            int U = 6 + j;         // U bottom row indices 6,7,8
            int R = 27 + 3 * j;    // R left column indices 27,30,33
            int D = 47 - j;        // D top row (reversed) indices 47,46,45
            int L = 17 - 3 * j;    // L right column indices 17,14,11

            t = this.cube[U];
            this.cube[U] = this.cube[R];
            this.cube[R] = this.cube[D];
            this.cube[D] = this.cube[L];
            this.cube[L] = t;
        }
    }

    public void F2() {
        // Rotate front face 180 degrees (swap opposite stickers)
        t = this.cube[18];
        this.cube[18] = this.cube[26];
        this.cube[26] = t;

        t = this.cube[19];
        this.cube[19] = this.cube[25];
        this.cube[25] = t;

        t = this.cube[20];
        this.cube[20] = this.cube[24];
        this.cube[24] = t;

        t = this.cube[21];
        this.cube[21] = this.cube[23];
        this.cube[23] = t;

        // Rotate adjacent edges: swap U <-> D and L <-> R per column
        for (int j = 0; j < 3; j++) {
            int U = 6 + j;         // U bottom row indices 6,7,8
            int R = 27 + 3 * j;    // R left column indices 27,30,33
            int D = 47 - j;        // D top row (reversed) indices 47,46,45
            int L = 17 - 3 * j;    // L right column indices 17,14,11

            t = this.cube[U];
            this.cube[U] = this.cube[D];
            this.cube[D] = t;

            t = this.cube[R];
            this.cube[R] = this.cube[L];
            this.cube[L] = t;
        }
    }

    public void B() {
        // Rotate back face clockwise (two 4-cycles)
        t = this.cube[36];
        this.cube[36] = this.cube[42];
        this.cube[42] = this.cube[44];
        this.cube[44] = this.cube[38];
        this.cube[38] = t;

        t = this.cube[37];
        this.cube[37] = this.cube[39];
        this.cube[39] = this.cube[43];
        this.cube[43] = this.cube[41];
        this.cube[41] = t;

        // Rotate adjacent edges: U <- R <- D <- L <- U (performed per column)
        for (int j = 0; j < 3; j++) {
            int U = 2 - j;        // U top row indices 2,1,0 (reversed)
            int R = 35 - 3 * j;   // R right column indices 35,32,29
            int D = 51 + j;       // D bottom row indices 51,52,53
            int L = 9 + 3 * j;    // L left column indices 9,12,15

            t = this.cube[U];
            this.cube[U] = this.cube[R];
            this.cube[R] = this.cube[D];
            this.cube[D] = this.cube[L];
            this.cube[L] = t;
        }
    }

    public void Bp() {
        // Rotate back face counter-clockwise (reverse of B)
        t = this.cube[36];
        this.cube[36] = this.cube[38];
        this.cube[38] = this.cube[44];
        this.cube[44] = this.cube[42];
        this.cube[42] = t;

        t = this.cube[37];
        this.cube[37] = this.cube[41];
        this.cube[41] = this.cube[43];
        this.cube[43] = this.cube[39];
        this.cube[39] = t;

        // Rotate adjacent edges (reverse of B's cycle)
        for (int j = 0; j < 3; j++) {
            int U = 2 - j;        // U top row indices 2,1,0 (reversed)
            int R = 35 - 3 * j;   // R right column indices 35,32,29
            int D = 51 + j;       // D bottom row indices 51,52,53
            int L = 9 + 3 * j;    // L left column indices 9,12,15

            t = this.cube[U];
            this.cube[U] = this.cube[L];
            this.cube[L] = this.cube[D];
            this.cube[D] = this.cube[R];
            this.cube[R] = t;
        }
    }

    public void B2() {
        // Rotate back face 180 degrees (swap opposite stickers)
        t = this.cube[36];
        this.cube[36] = this.cube[44];
        this.cube[44] = t;

        t = this.cube[37];
        this.cube[37] = this.cube[43];
        this.cube[43] = t;

        t = this.cube[38];
        this.cube[38] = this.cube[42];
        this.cube[42] = t;

        t = this.cube[39];
        this.cube[39] = this.cube[41];
        this.cube[41] = t;

        // Rotate adjacent edges: swap U <-> D and R <-> L per column
        for (int j = 0; j < 3; j++) {
            int U = 2 - j;        // U top row indices 2,1,0 (reversed)
            int R = 35 - 3 * j;   // R right column indices 35,32,29
            int D = 51 + j;       // D bottom row indices 51,52,53
            int L = 9 + 3 * j;    // L left column indices 9,12,15

            t = this.cube[U];
            this.cube[U] = this.cube[D];
            this.cube[D] = t;

            t = this.cube[R];
            this.cube[R] = this.cube[L];
            this.cube[L] = t;
        }
    }

    public void R() {
        // Rotate right face clockwise (two 4-cycles)
        t = this.cube[27];
        this.cube[27] = this.cube[33];
        this.cube[33] = this.cube[35];
        this.cube[35] = this.cube[29];
        this.cube[29] = t;

        t = this.cube[28];
        this.cube[28] = this.cube[30];
        this.cube[30] = this.cube[34];
        this.cube[34] = this.cube[32];
        this.cube[32] = t;

        // Rotate adjacent edges (4-cycles)
        t = this.cube[8];
        this.cube[8] = this.cube[26];
        this.cube[26] = this.cube[53];
        this.cube[53] = this.cube[36];
        this.cube[36] = t;

        t = this.cube[5];
        this.cube[5] = this.cube[23];
        this.cube[23] = this.cube[50];
        this.cube[50] = this.cube[39];
        this.cube[39] = t;

        t = this.cube[2];
        this.cube[2] = this.cube[20];
        this.cube[20] = this.cube[47];
        this.cube[47] = this.cube[42];
        this.cube[42] = t;
    }

    public void Rp() {
        // Rotate right face counter-clockwise (two 4-cycles)
        t = this.cube[27];
        this.cube[27] = this.cube[29];
        this.cube[29] = this.cube[35];
        this.cube[35] = this.cube[33];
        this.cube[33] = t;

        t = this.cube[28];
        this.cube[28] = this.cube[32];
        this.cube[32] = this.cube[34];
        this.cube[34] = this.cube[30];
        this.cube[30] = t;

        // Rotate adjacent edges (reverse 4-cycles)
        t = this.cube[8];
        this.cube[8] = this.cube[36];
        this.cube[36] = this.cube[53];
        this.cube[53] = this.cube[26];
        this.cube[26] = t;

        t = this.cube[5];
        this.cube[5] = this.cube[39];
        this.cube[39] = this.cube[50];
        this.cube[50] = this.cube[23];
        this.cube[23] = t;

        t = this.cube[2];
        this.cube[2] = this.cube[42];
        this.cube[42] = this.cube[47];
        this.cube[47] = this.cube[20];
        this.cube[20] = t;
    }

    public void R2() {
        // Rotate right face 180 degrees (swap opposite stickers)
        t = this.cube[27];
        this.cube[27] = this.cube[35];
        this.cube[35] = t;

        t = this.cube[28];
        this.cube[28] = this.cube[34];
        this.cube[34] = t;

        t = this.cube[29];
        this.cube[29] = this.cube[33];
        this.cube[33] = t;

        t = this.cube[30];
        this.cube[30] = this.cube[32];
        this.cube[32] = t;

        // Rotate adjacent edges: swap U <-> D and F <-> B per row
        t = this.cube[8];
        this.cube[8] = this.cube[53];
        this.cube[53] = t;

        t = this.cube[26];
        this.cube[26] = this.cube[36];
        this.cube[36] = t;

        t = this.cube[5];
        this.cube[5] = this.cube[50];
        this.cube[50] = t;

        t = this.cube[23];
        this.cube[23] = this.cube[39];
        this.cube[39] = t;

        t = this.cube[2];
        this.cube[2] = this.cube[47];
        this.cube[47] = t;

        t = this.cube[20];
        this.cube[20] = this.cube[42];
        this.cube[42] = t;
    }

    public void L() {
        // Rotate left face counter-clockwise (two 4-cycles)
        t = this.cube[9];
        this.cube[9] = this.cube[15];
        this.cube[15] = this.cube[17];
        this.cube[17] = this.cube[11];
        this.cube[11] = t;

        t = this.cube[10];
        this.cube[10] = this.cube[12];
        this.cube[12] = this.cube[16];
        this.cube[16] = this.cube[14];
        this.cube[14] = t;

        // Rotate adjacent edges (U <- B <- D <- F <- U) per column
        t = this.cube[0];
        this.cube[0] = this.cube[44];
        this.cube[44] = this.cube[45];
        this.cube[45] = this.cube[18];
        this.cube[18] = t;

        t = this.cube[3];
        this.cube[3] = this.cube[41];
        this.cube[41] = this.cube[48];
        this.cube[48] = this.cube[21];
        this.cube[21] = t;

        t = this.cube[6];
        this.cube[6] = this.cube[38];
        this.cube[38] = this.cube[51];
        this.cube[51] = this.cube[24];
        this.cube[24] = t;
    }

    public void Lp() {
        // Rotate left face clockwise (two 4-cycles)
        t = this.cube[9];
        this.cube[9] = this.cube[11];
        this.cube[11] = this.cube[17];
        this.cube[17] = this.cube[15];
        this.cube[15] = t;

        t = this.cube[10];
        this.cube[10] = this.cube[14];
        this.cube[14] = this.cube[16];
        this.cube[16] = this.cube[12];
        this.cube[12] = t;

        // Rotate adjacent edges (U -> F -> D -> B -> U) per column
        t = this.cube[0];
        this.cube[0] = this.cube[18];
        this.cube[18] = this.cube[45];
        this.cube[45] = this.cube[44];
        this.cube[44] = t;

        t = this.cube[3];
        this.cube[3] = this.cube[21];
        this.cube[21] = this.cube[48];
        this.cube[48] = this.cube[41];
        this.cube[41] = t;

        t = this.cube[6];
        this.cube[6] = this.cube[24];
        this.cube[24] = this.cube[51];
        this.cube[51] = this.cube[38];
        this.cube[38] = t;
    }

    public void L2() {
        // Rotate left face 180 degrees (swap opposite stickers)
        t = this.cube[9];
        this.cube[9] = this.cube[17];
        this.cube[17] = t;

        t = this.cube[10];
        this.cube[10] = this.cube[16];
        this.cube[16] = t;

        t = this.cube[11];
        this.cube[11] = this.cube[15];
        this.cube[15] = t;

        t = this.cube[12];
        this.cube[12] = this.cube[14];
        this.cube[14] = t;

        // center (13) remains the same

        // Rotate adjacent edges: swap U <-> D and B <-> F per column
        for (int j = 0; j < 3; j++) {
            int U = 0 + 3 * j;     // U left column indices 0,3,6
            int B = 44 - 3 * j;    // B right column indices 44,41,38
            int D = 45 + 3 * j;    // D left column indices 45,48,51
            int F = 18 + 3 * j;    // F left column indices 18,21,24

            t = this.cube[U];
            this.cube[U] = this.cube[D];
            this.cube[D] = t;

            t = this.cube[B];
            this.cube[B] = this.cube[F];
            this.cube[F] = t;
        }
    }

    public void U() {
        // Rotate upper face clockwise (two 4-cycles)
        t = this.cube[0];
        this.cube[0] = this.cube[6];
        this.cube[6] = this.cube[8];
        this.cube[8] = this.cube[2];
        this.cube[2] = t;

        t = this.cube[1];
        this.cube[1] = this.cube[3];
        this.cube[3] = this.cube[7];
        this.cube[7] = this.cube[5];
        this.cube[5] = t;

        // center (4) remains the same

        // Rotate adjacent edges: cycle (27->18->9->36->27) for each of the three columns
        for (int j = 0; j < 3; j++) {
            int A = 27 + j;
            int B = 18 + j;
            int C = 9 + j;
            int D = 36 + j;

            t = this.cube[A];
            this.cube[A] = this.cube[D];
            this.cube[D] = this.cube[C];
            this.cube[C] = this.cube[B];
            this.cube[B] = t;
        }
    }

    public void Up() {
        // Rotate upper face counter-clockwise (reverse of U)
        t = this.cube[0];
        this.cube[0] = this.cube[2];
        this.cube[2] = this.cube[8];
        this.cube[8] = this.cube[6];
        this.cube[6] = t;

        t = this.cube[1];
        this.cube[1] = this.cube[5];
        this.cube[5] = this.cube[7];
        this.cube[7] = this.cube[3];
        this.cube[3] = t;

        // Rotate adjacent edges (reverse of U's cycle)
        for (int j = 0; j < 3; j++) {
            int A = 27 + j;
            int B = 18 + j;
            int C = 9 + j;
            int D = 36 + j;

            t = this.cube[A];
            this.cube[A] = this.cube[B];
            this.cube[B] = this.cube[C];
            this.cube[C] = this.cube[D];
            this.cube[D] = t;
        }
    }

    public void U2() {
        // Rotate upper face 180 degrees (swap opposite stickers)
        t = this.cube[0];
        this.cube[0] = this.cube[8];
        this.cube[8] = t;

        t = this.cube[1];
        this.cube[1] = this.cube[7];
        this.cube[7] = t;

        t = this.cube[2];
        this.cube[2] = this.cube[6];
        this.cube[6] = t;

        t = this.cube[3];
        this.cube[3] = this.cube[5];
        this.cube[5] = t;

        // Rotate adjacent edges: swap (27 <-> 9) and (18 <-> 36) for each of the three columns
        for (int j = 0; j < 3; j++) {
            int A = 27 + j;
            int B = 18 + j;
            int C = 9 + j;
            int D = 36 + j;

            t = this.cube[A];
            this.cube[A] = this.cube[C];
            this.cube[C] = t;

            t = this.cube[B];
            this.cube[B] = this.cube[D];
            this.cube[D] = t;
        }
    }

    public void D() {
        // Rotate down face clockwise (two 4-cycles)
        t = this.cube[45];
        this.cube[45] = this.cube[51];
        this.cube[51] = this.cube[53];
        this.cube[53] = this.cube[47];
        this.cube[47] = t;

        t = this.cube[46];
        this.cube[46] = this.cube[48];
        this.cube[48] = this.cube[52];
        this.cube[52] = this.cube[50];
        this.cube[50] = t;

        // Rotate adjacent edges (F -> R -> B -> L -> F)
        for (int j = 0; j < 3; j++) {
            int A = 24 + j;
            int B = 33 + j;
            int C = 42 + j;
            int D = 15 + j;

            t = this.cube[A];
            this.cube[A] = this.cube[D];
            this.cube[D] = this.cube[C];
            this.cube[C] = this.cube[B];
            this.cube[B] = t;
        }
    }

    public void Dp() {
        // Rotate down face counter-clockwise (reverse of D)
        t = this.cube[45];
        this.cube[45] = this.cube[47];
        this.cube[47] = this.cube[53];
        this.cube[53] = this.cube[51];
        this.cube[51] = t;

        t = this.cube[46];
        this.cube[46] = this.cube[50];
        this.cube[50] = this.cube[52];
        this.cube[52] = this.cube[48];
        this.cube[48] = t;

        // Rotate adjacent edges (reverse of D's cycle)
        for (int j = 0; j < 3; j++) {
            int A = 33 + j;
            int B = 42 + j;
            int C = 15 + j;
            int D = 24 + j;

            t = this.cube[A];
            this.cube[A] = this.cube[B];
            this.cube[B] = this.cube[C];
            this.cube[C] = this.cube[D];
            this.cube[D] = t;
        }
    }

    public void D2() {
        // Rotate down face 180 degrees (swap opposite stickers)
        t = this.cube[45];
        this.cube[45] = this.cube[53];
        this.cube[53] = t;

        t = this.cube[46];
        this.cube[46] = this.cube[52];
        this.cube[52] = t;

        t = this.cube[47];
        this.cube[47] = this.cube[51];
        this.cube[51] = t;

        t = this.cube[48];
        this.cube[48] = this.cube[50];
        this.cube[50] = t;

        // Rotate adjacent edges: swap (F <-> B) and (R <-> L) for each of the three columns
        for (int j = 0; j < 3; j++) {
            int A = 24 + j;
            int B = 33 + j;
            int C = 42 + j;
            int D = 15 + j;

            t = this.cube[A];
            this.cube[A] = this.cube[C];
            this.cube[C] = t;

            t = this.cube[B];
            this.cube[B] = this.cube[D];
            this.cube[D] = t;
        }
    }

    public Cube copy() {
        Cube newCube = new Cube();
        newCube.setCube(this.cube.clone());
        return newCube;
    }
}