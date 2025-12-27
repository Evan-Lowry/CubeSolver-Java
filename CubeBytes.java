import java.util.ArrayList;

public class CubeBytes {

    private byte[] cube = new byte[54];

    public CubeBytes() {

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

    public int[] getCube() {
        int[] intCube = new int[54];
        for (int i = 0; i < 54; i++) {
            intCube[i] = this.cube[i];
        }
        return intCube;
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

    public void performMoves(String string) {
        String[] moves = string.split(" ");
        for (String move : moves) {
            switch (move) {
                case "U" -> U();
                case "U'" -> Up();
                case "U2" -> { U(); U(); }
                case "D" -> D();
                case "D'" -> Dp();
                case "D2" -> { D(); D(); }
                case "L" -> L();
                case "L'" -> Lp();
                case "L2" -> { L(); L(); }
                case "R" -> R();
                case "R'" -> Rp();
                case "R2" -> { R(); R(); }
                case "F" -> F();
                case "F'" -> Fp();
                case "F2" -> { F(); F(); }
                case "B" -> B();
                case "B'" -> Bp();
                case "B2" -> { B(); B(); }
                default -> throw new IllegalArgumentException("Invalid move: " + move);
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
        }
    }

    public void F() {
        byte[] newCube = this.cube.clone();

        // Rotate front face clockwise
        newCube[18] = this.cube[24];
        newCube[19] = this.cube[21];
        newCube[20] = this.cube[18];
        newCube[21] = this.cube[25];
        newCube[22] = this.cube[22];
        newCube[23] = this.cube[19];
        newCube[24] = this.cube[26];
        newCube[25] = this.cube[23];
        newCube[26] = this.cube[20];

        // Rotate adjacent edges
        newCube[6] = this.cube[17];
        newCube[7] = this.cube[14];
        newCube[8] = this.cube[11];

        newCube[27] = this.cube[6];
        newCube[30] = this.cube[7];
        newCube[33] = this.cube[8];

        newCube[47] = this.cube[27];
        newCube[46] = this.cube[30];
        newCube[45] = this.cube[33];

        newCube[17] = this.cube[47];
        newCube[14] = this.cube[46];
        newCube[11] = this.cube[45];

        this.cube = newCube;
    }

    public void Fp() {
        byte[] newCube = this.cube.clone();

        // Rotate front face counter-clockwise
        newCube[18] = this.cube[20];
        newCube[19] = this.cube[23];
        newCube[20] = this.cube[26];
        newCube[21] = this.cube[19];
        newCube[22] = this.cube[22];
        newCube[23] = this.cube[25];
        newCube[24] = this.cube[18];
        newCube[25] = this.cube[21];
        newCube[26] = this.cube[24];

        // Rotate adjacent edges
        newCube[6] = this.cube[27];
        newCube[7] = this.cube[30];
        newCube[8] = this.cube[33];

        newCube[27] = this.cube[47];
        newCube[30] = this.cube[46];
        newCube[33] = this.cube[45];

        newCube[47] = this.cube[17];
        newCube[46] = this.cube[14];
        newCube[45] = this.cube[11];

        newCube[17] = this.cube[6];
        newCube[14] = this.cube[7];
        newCube[11] = this.cube[8];

        this.cube = newCube;
    }

    public void B () {
        byte[] newCube = this.cube.clone();

        // Rotate back face clockwise
        newCube[36] = this.cube[42];
        newCube[37] = this.cube[39];
        newCube[38] = this.cube[36];
        newCube[39] = this.cube[43];
        newCube[40] = this.cube[40];
        newCube[41] = this.cube[37];
        newCube[42] = this.cube[44];
        newCube[43] = this.cube[41];
        newCube[44] = this.cube[38];

        // Rotate adjacent edges
        newCube[2] = this.cube[35];
        newCube[1] = this.cube[32];
        newCube[0] = this.cube[29];

        newCube[9] = this.cube[2];
        newCube[12] = this.cube[1];
        newCube[15] = this.cube[0];

        newCube[51] = this.cube[9];
        newCube[52] = this.cube[12];
        newCube[53] = this.cube[15];

        newCube[35] = this.cube[51];
        newCube[32] = this.cube[52];
        newCube[29] = this.cube[53];

        this.cube = newCube;
    }

    public void Bp() {
        byte[] newCube = this.cube.clone();

        // Rotate back face counter-clockwise
        newCube[36] = this.cube[38];
        newCube[37] = this.cube[41];
        newCube[38] = this.cube[44];
        newCube[39] = this.cube[37];
        newCube[40] = this.cube[40];
        newCube[41] = this.cube[43];
        newCube[42] = this.cube[36];
        newCube[43] = this.cube[39];
        newCube[44] = this.cube[42];

        // Rotate adjacent edges
        newCube[2] = this.cube[9];
        newCube[1] = this.cube[12];
        newCube[0] = this.cube[15];

        newCube[9] = this.cube[51];
        newCube[12] = this.cube[52];
        newCube[15] = this.cube[53];

        newCube[51] = this.cube[35];
        newCube[52] = this.cube[32];
        newCube[53] = this.cube[29];

        newCube[35] = this.cube[2];
        newCube[32] = this.cube[1];
        newCube[29] = this.cube[0];

        this.cube = newCube;
    }

    public void R() {
        byte[] newCube = this.cube.clone();

        // Rotate right face clockwise
        newCube[27] = this.cube[33];
        newCube[28] = this.cube[30];
        newCube[29] = this.cube[27];
        newCube[30] = this.cube[34];
        newCube[31] = this.cube[31];
        newCube[32] = this.cube[28];
        newCube[33] = this.cube[35];
        newCube[34] = this.cube[32];
        newCube[35] = this.cube[29];

        // Rotate adjacent edges
        newCube[8] = this.cube[26];
        newCube[5] = this.cube[23];
        newCube[2] = this.cube[20];

        newCube[36] = this.cube[8];
        newCube[39] = this.cube[5];
        newCube[42] = this.cube[2];

        newCube[53] = this.cube[36];
        newCube[50] = this.cube[39];
        newCube[47] = this.cube[42];

        newCube[26] = this.cube[53];
        newCube[23] = this.cube[50];
        newCube[20] = this.cube[47];

        this.cube = newCube;
    }

    public void Rp() {
        byte[] newCube = this.cube.clone();

        // Rotate right face counter-clockwise
        newCube[27] = this.cube[29];
        newCube[28] = this.cube[32];
        newCube[29] = this.cube[35];
        newCube[30] = this.cube[28];
        newCube[31] = this.cube[31];
        newCube[32] = this.cube[34];
        newCube[33] = this.cube[27];
        newCube[34] = this.cube[30];
        newCube[35] = this.cube[33];

        // Rotate adjacent edges
        newCube[8] = this.cube[36];
        newCube[5] = this.cube[39];
        newCube[2] = this.cube[42];

        newCube[36] = this.cube[53];
        newCube[39] = this.cube[50];
        newCube[42] = this.cube[47];

        newCube[53] = this.cube[26];
        newCube[50] = this.cube[23];
        newCube[47] = this.cube[20];

        newCube[26] = this.cube[8];
        newCube[23] = this.cube[5];
        newCube[20] = this.cube[2];

        this.cube = newCube;
    }

    public void L() {
        byte[] newCube = this.cube.clone();

        // Rotate left face counter-clockwise
        newCube[9]  = this.cube[15];
        newCube[10] = this.cube[12];
        newCube[11] = this.cube[9];
        newCube[12] = this.cube[16];
        newCube[13] = this.cube[13];
        newCube[14] = this.cube[10];
        newCube[15] = this.cube[17];
        newCube[16] = this.cube[14];
        newCube[17] = this.cube[11];

        // Rotate adjacent edges (U <- B <- D <- F <- U)
        newCube[0] = this.cube[44];
        newCube[3] = this.cube[41];
        newCube[6] = this.cube[38];

        newCube[38] = this.cube[51];
        newCube[41] = this.cube[48];
        newCube[44] = this.cube[45];

        newCube[51] = this.cube[24];
        newCube[48] = this.cube[21];
        newCube[45] = this.cube[18];

        newCube[18] = this.cube[0];
        newCube[21] = this.cube[3];
        newCube[24] = this.cube[6];

        this.cube = newCube;
    }

    public void Lp() {
        byte[] newCube = this.cube.clone();

        // Rotate left face clockwise
        newCube[9]  = this.cube[11];
        newCube[10] = this.cube[14];
        newCube[11] = this.cube[17];
        newCube[12] = this.cube[10];
        newCube[13] = this.cube[13];
        newCube[14] = this.cube[16];
        newCube[15] = this.cube[9];
        newCube[16] = this.cube[12];
        newCube[17] = this.cube[15];

        // Rotate adjacent edges (U -> F -> D -> B -> U)
        newCube[0] = this.cube[18];
        newCube[3] = this.cube[21];
        newCube[6] = this.cube[24];

        newCube[18] = this.cube[45];
        newCube[21] = this.cube[48];
        newCube[24] = this.cube[51];

        newCube[45] = this.cube[44];
        newCube[48] = this.cube[41];
        newCube[51] = this.cube[38];

        newCube[44] = this.cube[0];
        newCube[41] = this.cube[3];
        newCube[38] = this.cube[6];

        this.cube = newCube;
    }

    public void U() {
        byte[] newCube = this.cube.clone();

        // Rotate upper face clockwise
        newCube[0] = this.cube[6];
        newCube[1] = this.cube[3];
        newCube[2] = this.cube[0];
        newCube[3] = this.cube[7];
        newCube[4] = this.cube[4];
        newCube[5] = this.cube[1];
        newCube[6] = this.cube[8];
        newCube[7] = this.cube[5];
        newCube[8] = this.cube[2];

        // Rotate adjacent edges
        newCube[18] = this.cube[27];
        newCube[19] = this.cube[28];
        newCube[20] = this.cube[29];

        newCube[9] = this.cube[18];
        newCube[10] = this.cube[19];
        newCube[11] = this.cube[20];

        newCube[36] = this.cube[9];
        newCube[37] = this.cube[10];
        newCube[38] = this.cube[11];

        newCube[27] = this.cube[36];
        newCube[28] = this.cube[37];
        newCube[29] = this.cube[38];

        this.cube = newCube;
    }

    public void Up() {
        byte[] newCube = this.cube.clone();

        // Rotate upper face counter-clockwise
        newCube[0] = this.cube[2];
        newCube[1] = this.cube[5];
        newCube[2] = this.cube[8];
        newCube[3] = this.cube[1];
        newCube[4] = this.cube[4];
        newCube[5] = this.cube[7];
        newCube[6] = this.cube[0];
        newCube[7] = this.cube[3];
        newCube[8] = this.cube[6];

        // Rotate adjacent edges
        newCube[18] = this.cube[9];
        newCube[19] = this.cube[10];
        newCube[20] = this.cube[11];

        newCube[9] = this.cube[36];
        newCube[10] = this.cube[37];
        newCube[11] = this.cube[38];

        newCube[36] = this.cube[27];
        newCube[37] = this.cube[28];
        newCube[38] = this.cube[29];

        newCube[27] = this.cube[18];
        newCube[28] = this.cube[19];
        newCube[29] = this.cube[20];

        this.cube = newCube;
    }

    public void D() {
        byte[] newCube = this.cube.clone();

        // Rotate down face clockwise (indices 45..53)
        newCube[45] = this.cube[51];
        newCube[46] = this.cube[48];
        newCube[47] = this.cube[45];
        newCube[48] = this.cube[52];
        newCube[49] = this.cube[49];
        newCube[50] = this.cube[46];
        newCube[51] = this.cube[53];
        newCube[52] = this.cube[50];
        newCube[53] = this.cube[47];

        // Rotate adjacent edges (F -> R -> B -> L -> F)
        newCube[33] = this.cube[24];
        newCube[34] = this.cube[25];
        newCube[35] = this.cube[26];

        newCube[42] = this.cube[33];
        newCube[43] = this.cube[34];
        newCube[44] = this.cube[35];

        newCube[15] = this.cube[42];
        newCube[16] = this.cube[43];
        newCube[17] = this.cube[44];

        newCube[24] = this.cube[15];
        newCube[25] = this.cube[16];
        newCube[26] = this.cube[17];

        this.cube = newCube;
    }

    public void Dp() {
        byte[] newCube = this.cube.clone();

        // Rotate down face counter-clockwise
        newCube[45] = this.cube[47];
        newCube[46] = this.cube[50];
        newCube[47] = this.cube[53];
        newCube[48] = this.cube[46];
        newCube[49] = this.cube[49];
        newCube[50] = this.cube[52];
        newCube[51] = this.cube[45];
        newCube[52] = this.cube[48];
        newCube[53] = this.cube[51];

        // Rotate adjacent edges
        newCube[33] = this.cube[42];
        newCube[34] = this.cube[43];
        newCube[35] = this.cube[44];

        newCube[42] = this.cube[15];
        newCube[43] = this.cube[16];
        newCube[44] = this.cube[17];

        newCube[15] = this.cube[24];
        newCube[16] = this.cube[25];
        newCube[17] = this.cube[26];

        newCube[24] = this.cube[33];
        newCube[25] = this.cube[34];
        newCube[26] = this.cube[35];

        this.cube = newCube;
    }

    public CubeBytes copy() {
        CubeBytes newCube = new CubeBytes();
        newCube.setCube(this.cube.clone());
        return newCube;
    }
}