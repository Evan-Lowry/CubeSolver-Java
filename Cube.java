
public class Cube {

    private char[][] cube;

    public Cube() {
        cube = new char[3][18];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cube [i][j] = 'w';
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 3; j < 6; j++) {
                cube [i][j] = 'g';
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 6; j < 9; j++) {
                cube [i][j] = 'y';
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 9; j < 12; j++) {
                cube [i][j] = 'b';
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 12; j < 15; j++) {
                cube [i][j] = 'r';
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 15; j < 18; j++) {
                cube [i][j] = 'o';
            }
        }
    }

    public String toString() {
        String o = "";

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 3; j++) {
                for (int j2 = 3*i; j2 < 3+3*i; j2++) {
                    o += "[" + cube[j][j2] + "]";
                }
            }
        }

        return o;
    }
}