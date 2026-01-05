public class CubeCorner {

    private byte[] cube = new byte[24];
    byte t;

    public CubeCorner() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                this.cube[4*i+j] = (byte) (i);
            }
        }
    }

    public String toString() {

        String output = "";

        for (int i = 0; i < 2; i++) {
            output += "      ";
            for (int j = 0; j < 2; j++) {
                output += "[" + this.cube[i*2 + j] + "]";
            }
            output += "\n";
        }

        for (int i = 2; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 2; k++) {
                    output += "[" + this.cube[j*4 + i*2 + k] + "]";
                }
            }
            output += "\n";
        }

        for (int i = 10; i < 12; i++) {
            output += "      ";
            for (int j = 0; j < 2; j++) {
                output += "[" + this.cube[i*2 + j] + "]";
            }
            output += "\n";
        }

        return output;
    }

    public String toShortString() {
        StringBuilder sb = new StringBuilder();
        for (byte b : cube) {
            sb.append(b);
        }
        return sb.toString();
    }

    public byte[] getCube() {
        return this.cube;
    }

    public void setCube(byte[] newCube) {
        this.cube = newCube;
    }

    public boolean isSolved() {
        for (int i = 0; i < 6; i++) {
            byte color = (byte) i; // expected color for face i
            for (int j = 0; j < 4; j++) {
                if (this.cube[i * 4 + j] != color) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isOriented() {
        // Check if all corners are in correct orientation
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                if (this.cube[i * 20 + j] != 0 && this.cube[i * 20 + j] != 5) {
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

    public void F() {
        // For 2x2 cube layout:
        // faces: 0: U(0-3), 1: L(4-7), 2: F(8-11), 3: R(12-15), 4: B(16-19), 5: D(20-23)
        // Rotate front face (face 2) clockwise (2x2 face rotation)
        int f = 8;
        t = this.cube[f + 2];
        this.cube[f + 2] = this.cube[f + 3];
        this.cube[f + 3] = this.cube[f + 1];
        this.cube[f + 1] = this.cube[f + 0];
        this.cube[f + 0] = t;

        // Rotate adjacent edges: U bottom row -> R left col -> D top row -> L right col -> U
        // Indices chosen to match the 2x2 net described above
        for (int j = 0; j < 2; j++) {
            int U = 2 + j;         // U bottom row indices: 2,3 (left->right)
            int L = 7 - 2 * j;     // L right column indices: 5,7 (top->bottom)
            int D = 21 - j;        // D top row reversed: 21,20 (right->left)
            int R = 12 + 2 * j;    // R left column indices: 12,14 (top->bottom)

            t = this.cube[U];
            this.cube[U] = this.cube[L];
            this.cube[L] = this.cube[D];
            this.cube[D] = this.cube[R];
            this.cube[R] = t;
        }
    }

    public void Fp() {
        // Rotate front face counter-clockwise (2x2)
        int f = 8;
        t = this.cube[f + 0];
        this.cube[f + 0] = this.cube[f + 1];
        this.cube[f + 1] = this.cube[f + 3];
        this.cube[f + 3] = this.cube[f + 2];
        this.cube[f + 2] = t;

        // Reverse of F's adjacent cycle: U <-> R <-> D <-> L
        for (int j = 0; j < 2; j++) {
            int U = 2 + j;         // U bottom row: 2,3
            int R = 12 + 2 * j;    // R left column: 12,14
            int D = 21 - j;        // D top row reversed: 21,20
            int L = 7 - 2 * j;     // L right column: 7,5

            t = this.cube[U];
            this.cube[U] = this.cube[R];
            this.cube[R] = this.cube[D];
            this.cube[D] = this.cube[L];
            this.cube[L] = t;
        }
    }

    public void B() {
        // Rotate back face clockwise (2x2)
        int b = 16;
        t = this.cube[b + 0];
        this.cube[b + 0] = this.cube[b + 2];
        this.cube[b + 2] = this.cube[b + 3];
        this.cube[b + 3] = this.cube[b + 1];
        this.cube[b + 1] = t;

        // Adjacent edges: U top row -> L left col -> D bottom row -> R right col -> U
        for (int j = 0; j < 2; j++) {
            int U = 0 + j;         // U top row: 0,1
            int L = 6 - 2 * j;     // L left column: 6,4
            int D = 23 - j;        // D bottom row: 23,22
            int R = 13 + 2 * j;    // R right column: 13,15

            t = this.cube[U];
            this.cube[U] = this.cube[R];
            this.cube[R] = this.cube[D];
            this.cube[D] = this.cube[L];
            this.cube[L] = t;
        }
    }

    public void Bp() {
        // Rotate back face counter-clockwise (2x2)
        int b = 16;
        t = this.cube[b + 0];
        this.cube[b + 0] = this.cube[b + 1];
        this.cube[b + 1] = this.cube[b + 3];
        this.cube[b + 3] = this.cube[b + 2];
        this.cube[b + 2] = t;

        // Reverse of B's adjacent cycle
        for (int j = 0; j < 2; j++) {
            int U = 0 + j;         // U top row: 0,1
            int L = 6 - 2 * j;     // L left column: 4,6
            int D = 23 - j;        // D bottom row: 22,23
            int R = 13 + 2 * j;    // R right column: 13,15

            t = this.cube[U];
            this.cube[U] = this.cube[L];
            this.cube[L] = this.cube[D];
            this.cube[D] = this.cube[R];
            this.cube[R] = t;
        }
    }

    public void R() {
        // Rotate right face clockwise (2x2)
        int r = 12;
        t = this.cube[r + 2];
        this.cube[r + 2] = this.cube[r + 3];
        this.cube[r + 3] = this.cube[r + 1];
        this.cube[r + 1] = this.cube[r + 0];
        this.cube[r + 0] = t;

        // Adjacent edges: U right col -> F right col -> D right col -> B left col -> U
        for (int j = 0; j < 2; j++) {
            int U = 1 + 2 * j;     // U right column: 1,3
            int F = 9 + 2 * j;     // F right column: 9,11
            int D = 21 + 2 * j;    // D right column: 21,23
            int B = 18 - 2 * j;    // B left column: 18,16

            t = this.cube[U];
            this.cube[U] = this.cube[F];
            this.cube[F] = this.cube[D];
            this.cube[D] = this.cube[B];
            this.cube[B] = t;
        }
    }

    public void Rp() {
        // Rotate right face counter-clockwise (2x2)
        int r = 12;
        t = this.cube[r + 0];
        this.cube[r + 0] = this.cube[r + 1];
        this.cube[r + 1] = this.cube[r + 3];
        this.cube[r + 3] = this.cube[r + 2];
        this.cube[r + 2] = t;

        // Reverse of R's adjacent cycle
        for (int j = 0; j < 2; j++) {
            int U = 1 + 2 * j;     // U right column: 1,3
            int F = 9 + 2 * j;     // F right column: 9,11
            int D = 21 + 2 * j;    // D right column: 21,23
            int B = 18 - 2 * j;    // B left column: 18,16

            t = this.cube[U];
            this.cube[U] = this.cube[B];
            this.cube[B] = this.cube[D];
            this.cube[D] = this.cube[F];
            this.cube[F] = t;
        }
    }

    public void L() {
        // Rotate left face clockwise (2x2)
        int l = 4;
        t = this.cube[l + 2];
        this.cube[l + 2] = this.cube[l + 3];
        this.cube[l + 3] = this.cube[l + 1];
        this.cube[l + 1] = this.cube[l + 0];
        this.cube[l + 0] = t;

        // Adjacent edges: U left col -> B right col -> D left col -> F left col -> U
        for (int j = 0; j < 2; j++) {
            int U = 0 + 2 * j;     // U left column: 0,2
            int B = 19 - 2 * j;    // B right column: 19,17
            int D = 20 + 2 * j;    // D left column: 20,22
            int F = 8 + 2 * j;     // F left column: 8,10

            t = this.cube[U];
            this.cube[U] = this.cube[B];
            this.cube[B] = this.cube[D];
            this.cube[D] = this.cube[F];
            this.cube[F] = t;
        }
    }

    public void Lp() {
        // Rotate left face counter-clockwise (2x2)
        int l = 4;
        t = this.cube[l + 0];
        this.cube[l + 0] = this.cube[l + 1];
        this.cube[l + 1] = this.cube[l + 3];
        this.cube[l + 3] = this.cube[l + 2];
        this.cube[l + 2] = t;

        // Reverse of L's adjacent cycle
        for (int j = 0; j < 2; j++) {
            int U = 0 + 2 * j;     // U left column: 0,2
            int B = 19 - 2 * j;    // B right column: 19,17
            int D = 20 + 2 * j;    // D left column: 20,22
            int F = 8 + 2 * j;     // F left column: 8,10

            t = this.cube[U];
            this.cube[U] = this.cube[F];
            this.cube[F] = this.cube[D];
            this.cube[D] = this.cube[B];
            this.cube[B] = t;
        }
    }

    public void U() {
        // Rotate upper face clockwise (2x2)
        int u = 0;
        t = this.cube[u + 2];
        this.cube[u + 2] = this.cube[u + 3];
        this.cube[u + 3] = this.cube[u + 1];
        this.cube[u + 1] = this.cube[u + 0];
        this.cube[u + 0] = t;

        // Adjacent edges: L top row -> F top row -> R top row -> B top row -> L
        for (int j = 0; j < 2; j++) {
            int L = 4 + j;     // L top row: 4,5
            int F = 8 + j;     // F top row: 8,9
            int R = 12 + j;    // R top row: 12,13
            int B = 16 + j;    // B top row: 16,17

            t = this.cube[L];
            this.cube[L] = this.cube[F];
            this.cube[F] = this.cube[R];
            this.cube[R] = this.cube[B];
            this.cube[B] = t;
        }
    }

    public void Up() {
        // Rotate upper face counter-clockwise (2x2)
        int u = 0;
        t = this.cube[u + 0];
        this.cube[u + 0] = this.cube[u + 1];
        this.cube[u + 1] = this.cube[u + 3];
        this.cube[u + 3] = this.cube[u + 2];
        this.cube[u + 2] = t;

        // Reverse of U's adjacent cycle
        for (int j = 0; j < 2; j++) {
            int L = 4 + j;     // L top row: 4,5
            int F = 8 + j;     // F top row: 8,9
            int R = 12 + j;    // R top row: 12,13
            int B = 16 + j;    // B top row: 16,17

            t = this.cube[L];
            this.cube[L] = this.cube[B];
            this.cube[B] = this.cube[R];
            this.cube[R] = this.cube[F];
            this.cube[F] = t;
        }
    }

    public void D() {
        // Rotate down face clockwise (2x2)
        int d = 20;
        t = this.cube[d + 2];
        this.cube[d + 2] = this.cube[d + 3];
        this.cube[d + 3] = this.cube[d + 1];
        this.cube[d + 1] = this.cube[d + 0];
        this.cube[d + 0] = t;

        // Adjacent edges: F bottom row -> R bottom row -> B bottom row -> L bottom row -> F
        for (int j = 0; j < 2; j++) {
            int F = 10 + j;    // F bottom row: 10,11
            int R = 14 + j;    // R bottom row: 14,15
            int B = 18 + j;    // B bottom row: 18,19
            int L = 6 + j;     // L bottom row: 6,7

            t = this.cube[F];
            this.cube[F] = this.cube[L];
            this.cube[L] = this.cube[B];
            this.cube[B] = this.cube[R];
            this.cube[R] = t;
        }
    }

    public void Dp() {
        // Rotate down face counter-clockwise (2x2)
        int d = 20;
        t = this.cube[d + 0];
        this.cube[d + 0] = this.cube[d + 1];
        this.cube[d + 1] = this.cube[d + 3];
        this.cube[d + 3] = this.cube[d + 2];
        this.cube[d + 2] = t;

        // Reverse of D's adjacent cycle
        for (int j = 0; j < 2; j++) {
            int F = 10 + j;    // F bottom row: 10,11
            int R = 14 + j;    // R bottom row: 14,15
            int B = 18 + j;    // B bottom row: 18,19
            int L = 6 + j;     // L bottom row: 6,7

            t = this.cube[F];
            this.cube[F] = this.cube[R];
            this.cube[R] = this.cube[B];
            this.cube[B] = this.cube[L];
            this.cube[L] = t;
        }
    }

    public CubeCorner copy() {
        CubeCorner newCube = new CubeCorner();
        newCube.setCube(this.cube.clone());
        return newCube;
    }
}