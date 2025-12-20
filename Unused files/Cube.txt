import java.util.ArrayList;

public class Cube {

    private Cubit[] corners = new Cubit[8];
    private Cubit[] edges = new Cubit[12];

    public Cube() {
        // creates top face
        this.corners[0] = new Cubit(-1, 1, -1, 0);
        this.corners[1] = new Cubit(1, 1, -1, 0);
        this.corners[2] = new Cubit(1, 1, 1, 0);
        this.corners[3] = new Cubit(-1, 1, 1, 0);

        // creates bottom face
        this.corners[4] = new Cubit(-1, -1, -1, 0);
        this.corners[5] = new Cubit(1, -1, -1, 0);
        this.corners[6] = new Cubit(1, -1, 1, 0);
        this.corners[7] = new Cubit(-1, -1, 1, 0);

        //creates top face
        this.edges[0] = new Cubit(0, 1, -1, 0);
        this.edges[1] = new Cubit(1, 1, 0, 0);
        this.edges[2] = new Cubit(0, 1, 1, 0);
        this.edges[3] = new Cubit(-1, 1, 0, 0);

        //creates middle layer
        this.edges[4] = new Cubit(-1, 0, -1, 0);
        this.edges[5] = new Cubit(1, 0, -1, 0);
        this.edges[6] = new Cubit(1, 0, 1, 0);
        this.edges[7] = new Cubit(-1, 0, 1, 0);

        //creates bottom face
        this.edges[8] = new Cubit(0, -1, -1, 0);
        this.edges[9] = new Cubit(1, -1, 0, 0);
        this.edges[10] = new Cubit(0, -1, 1, 0);
        this.edges[11] = new Cubit(-1, -1, 0, 0);
    }

    public void F() {
        for (Cubit cubit : corners) {
        }
    }

    public Cubit[] getCorners() {
        return this.corners;
    }

    public Cubit[] getEdges() {
        return this.edges;
    }


    public String toString() {
        char[] corn = new char[24];

        for (int i = 0; i < 8; i++) {

            int[] index = index(this.corners[i]);

            corn[index[0]] = color(i, 0);
            corn[index[1]] = color(i, 1);
            corn[index[2]] = color(i, 2);
        }

        String output = "";

        for (char c : corn) {
            output += c + " ";
        }

        return output;
    }

    private char color(int n, int c) {
        char[] tileColors0 = {'w', 'w', 'w', 'w', 'y', 'y', 'y', 'y'};
        char[] tileColors1 = {'o', 'b', 'r', 'g', 'b', 'r', 'g', 'o'};
        char[] tileColors2 = {'b', 'r', 'g', 'o', 'o', 'b', 'r', 'g'};


        if (c == 0) {
            return tileColors0[n];
        }

        if (c == 1) {
            return tileColors1[n];
        }

        if (c == 2) {
            return tileColors2[n];
        }

        return 'E';
    }

    private int[] index(Cubit c) {
        int[] index = new int[3];

        // for top corners of cube
        if (c.getX() == -1 && c.getY() == 1 && c.getZ() == -1) {
            return new int[] {1, 48, 34};
        }

        if (c.getX() == 1 && c.getY() == 1 && c.getZ() == -1) {
            return new int[] {3, 36, 37};
        }

        if (c.getX() == 1 && c.getY() == 1 && c.getZ() == 1) {
            return new int[] {9, 43, 12};
        }

        if (c.getX() == -1 && c.getY() == 1 && c.getZ() == 1) {
            return new int[] {7, 10, 54};
        }

        // for bottom corners of cube
        if (c.getX() == -1 && c.getY() == -1 && c.getZ() == -1) {
            return new int[] {25, 28, 46};
        }

        if (c.getX() == 1 && c.getY() == -1 && c.getZ() == -1) {
            return new int[] {27, 45, 30};
        }

        if (c.getX() == 1 && c.getY() == -1 && c.getZ() == 1) {
            return new int[] {21, 18, 43};
        }

        if (c.getX() == -1 && c.getY() == -1 && c.getZ() == 1) {
            return new int[] {19, 52, 16};
        }

        return index;
    }
}