import java.util.Arrays;

public class CubeKey {
    private int[] positions;  // Stores positions for each cubie
    private static final int[][] cornerPositions = {{0, 9, 38}, {2, 36, 29}, {8, 27, 20}, {6, 18, 11}, {51, 44, 15}, {53, 35, 42}, {47, 26, 33}, {45, 17, 24}};
    private static final int[][] cornerOrientations = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}};

    private static final byte[][] cornerColors = {{0, 1, 4}, {0, 4, 3}, {0, 3, 2}, {0, 2, 1}, {5, 4, 1}, {5, 3, 4}, {5, 2, 3}, {5, 1, 2}};
    private static final int[] edgePositions = {8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30};
    
    private static final byte[][] keyMoveLookup = {{3, 2, 1, 0},   // U
                                                   {4, 5, 6, 7},   // D
                                                   {0, 4, 7, 3},   // L
                                                   {2, 6, 5, 1},   // R
                                                   {3, 7, 6, 2},   // F
                                                   {1, 5, 4, 0},}; // B

    public static long encodeByteArrayToLong(byte[] array) {
        long key = 0;
        for (int i = 0; i < array.length; i++) {
            key += array[i] * Math.pow(10, i);
        }
        return key;
    }
    
    public static int encodePosition(byte[] cube) {
        int key = 0;
        int pow = 1;
        for (int i = 7; i >= 0; i--) {
            key += getCorner(cube, cornerPositions[i][0], cornerPositions[i][1], cornerPositions[i][2]) * pow;
            pow *= 10;
        }
        return key;
    }
    
    public static int encodeOrientation(byte[] cube) {
        int key = 0;
        int pow = 1;
        for (int i = 7; i >= 0; i--) {
            key += cube[i] * pow;
            pow *= 10;
        }
        return key;
    }

    public static byte[] encodePositionToByte(byte[] cube) {
        byte[] key = new byte[8];
        for (int i = 0; i < 8; i++) {
            key[i] = (byte) getCorner(cube, cornerPositions[i][0], cornerPositions[i][1], cornerPositions[i][2]);
        }
        return key;
    }


    public static byte[] encodeOrientationToByte(byte[] cube) {
        byte[] key = new byte[8];
        for (int i = 0; i < 8; i++) {
            int[]corner = cornerPositions[i];
            if (cube[corner[0]] == 0 || cube[corner[0]] == 5) key[i] = 0;
            if (cube[corner[1]] == 0 || cube[corner[1]] == 5) key[i] = 1;
            if (cube[corner[2]] == 0 || cube[corner[2]] == 5) key[i] = 2;
        }
        return key;
    }

    public static int encodePositionByte(byte[] cube) {
        int key = 0;
        int pow = 1;
        for (int i = 7; i >= 0; i--) {
            key += cube[i] * pow;
            pow *= 10;
        }
        return key;
    }

    private static int[] byteArrayToIndexArray(byte[] cube) {
        int[] index = new int[8];
        for (int i = 0; i < 8; i++) {
            index[i] = getCorner(cube, cornerPositions[i][0], cornerPositions[i][1], cornerPositions[i][2]);
        }
        return index;
    }

    public static byte[] indexArrayToCube(byte[] index) {
        byte[] cube = new byte[54];
        for (int i = 0; i < 54; i++) {
            cube[i] = (byte) (i / 9);
        }
        for (int i = 0; i < 8; i++) {
            cube[cornerPositions[i][0]] = cornerColors[index[i]][0];
            cube[cornerPositions[i][1]] = cornerColors[index[i]][1];
            cube[cornerPositions[i][2]] = cornerColors[index[i]][2];
        }
        return cube;
    }

    public static byte[] indexOrientatedArrayToCube(byte[] index) {
        byte[] cube = new byte[54];
        for (int i = 0; i < 54; i++) {
            cube[i] = (byte) (1);
        }
        for (int i = 0; i < 8; i++) {
            cube[cornerPositions[i][index[i]]] = (byte) 0;
        }
        return cube;
    }

    private static int getCorner(byte[] cube, int i1, int i2, int i3) {

        // System.out.println("i1: " + i1 + " i2: " + i2 + " i3: " + i3);
        // System.out.println("cube: " + Arrays.toString(cube));
        if (cube[i1] == 0 && cube[i2] == 1 && cube[i3] == 4) {
            return 0;
        } else if(cube[i1] == 0 && cube[i2] == 4 && cube[i3] == 3) {
            return 1;
        } else if(cube[i1] == 0 && cube[i2] == 3 && cube[i3] == 2) {
            return 2;
        } else if(cube[i1] == 0 && cube[i2] == 2 && cube[i3] == 1) {
            return 3;
        } else if(cube[i1] == 5 && cube[i2] == 4 && cube[i3] == 1) {
            return 4;
        } else if(cube[i1] == 5 && cube[i2] == 3 && cube[i3] == 4) {
            return 5;
        } else if(cube[i1] == 5 && cube[i2] == 2 && cube[i3] == 3) {
            return 6;
        } else if(cube[i1] == 5 && cube[i2] == 1 && cube[i3] == 2) {
            return 7;
        } else {
            return -1;
        }
    }

    static byte[] applyMove(byte[] key, int move) {
        int type = move % 3;
        int face = move / 3;
        byte t;
        if (type == 0) {
            t = key[keyMoveLookup[face][0]];
            key[keyMoveLookup[face][0]] = key[keyMoveLookup[face][1]];
            key[keyMoveLookup[face][1]] = key[keyMoveLookup[face][2]];
            key[keyMoveLookup[face][2]] = key[keyMoveLookup[face][3]];
            key[keyMoveLookup[face][3]] = t;
        } else if (type == 1) {
            t = key[keyMoveLookup[face][0]];
            key[keyMoveLookup[face][0]] = key[keyMoveLookup[face][3]];
            key[keyMoveLookup[face][3]] = key[keyMoveLookup[face][2]];
            key[keyMoveLookup[face][2]] = key[keyMoveLookup[face][1]];
            key[keyMoveLookup[face][1]] = t;
        } else if (type == 2) {
            t = key[keyMoveLookup[face][0]];
            key[keyMoveLookup[face][0]] = key[keyMoveLookup[face][2]];
            key[keyMoveLookup[face][2]] = t;
            t = key[keyMoveLookup[face][1]];
            key[keyMoveLookup[face][1]] = key[keyMoveLookup[face][3]];
            key[keyMoveLookup[face][3]] = t;
        }
        return key;
    }

    static byte[] undoMove(byte[] key, int move) {
        int type = move % 3;
        int face = move / 3;
        byte t;
        if (type == 0) {
            t = key[keyMoveLookup[face][3]];
            key[keyMoveLookup[face][3]] = key[keyMoveLookup[face][2]];
            key[keyMoveLookup[face][2]] = key[keyMoveLookup[face][1]];
            key[keyMoveLookup[face][1]] = key[keyMoveLookup[face][0]];
            key[keyMoveLookup[face][0]] = t;
        } else if (type == 1) {
            t = key[keyMoveLookup[face][3]];
            key[keyMoveLookup[face][3]] = key[keyMoveLookup[face][0]];
            key[keyMoveLookup[face][0]] = key[keyMoveLookup[face][1]];
            key[keyMoveLookup[face][1]] = key[keyMoveLookup[face][2]];
            key[keyMoveLookup[face][2]] = t;
        } else if (type == 2) {
            t = key[keyMoveLookup[face][0]];
            key[keyMoveLookup[face][0]] = key[keyMoveLookup[face][2]];
            key[keyMoveLookup[face][2]] = t;
            t = key[keyMoveLookup[face][1]];
            key[keyMoveLookup[face][1]] = key[keyMoveLookup[face][3]];
            key[keyMoveLookup[face][3]] = t;
        }
        return key;
    }

    static byte[] applyOrientationMove(byte[] key, int move) {
        int type = move % 3;
        int face = move / 3;

        if (face < 3 || type == 0) return key; // if move is not an orientation move, return the key unchanged

        if (type == 1) { // clockwise
            key[keyMoveLookup[face][0]] = (byte) ((key[keyMoveLookup[face][0]] + 1) % 3);
            key[keyMoveLookup[face][1]] = (byte) ((key[keyMoveLookup[face][1]] + 2) % 3);
            key[keyMoveLookup[face][2]] = (byte) ((key[keyMoveLookup[face][2]] + 1) % 3);
            key[keyMoveLookup[face][3]] = (byte) ((key[keyMoveLookup[face][3]] + 2) % 3);
        } else if (type == 2) {
            key[keyMoveLookup[face][0]] = (byte) ((key[keyMoveLookup[face][0]] + 2) % 3);
            key[keyMoveLookup[face][1]] = (byte) ((key[keyMoveLookup[face][1]] + 1) % 3);
            key[keyMoveLookup[face][2]] = (byte) ((key[keyMoveLookup[face][2]] + 2) % 3);
            key[keyMoveLookup[face][3]] = (byte) ((key[keyMoveLookup[face][3]] + 1) % 3);
        }
        return key;
    }

    static byte[] undoOrientationMove(byte[] key, int move) {
        int type = move % 3;
        int face = move / 3;
        byte t;
        if (type == 0) {
            t = key[keyMoveLookup[face][3]];
            key[keyMoveLookup[face][3]] = key[keyMoveLookup[face][2]];
            key[keyMoveLookup[face][2]] = key[keyMoveLookup[face][1]];
            key[keyMoveLookup[face][1]] = key[keyMoveLookup[face][0]];
            key[keyMoveLookup[face][0]] = t;
        } else if (type == 1) {
            t = key[keyMoveLookup[face][3]];
            key[keyMoveLookup[face][3]] = key[keyMoveLookup[face][0]];
            key[keyMoveLookup[face][0]] = key[keyMoveLookup[face][1]];
            key[keyMoveLookup[face][1]] = key[keyMoveLookup[face][2]];
            key[keyMoveLookup[face][2]] = t;
        } else if (type == 2) {
            t = key[keyMoveLookup[face][0]];
            key[keyMoveLookup[face][0]] = key[keyMoveLookup[face][2]];
            key[keyMoveLookup[face][2]] = t;
            t = key[keyMoveLookup[face][1]];
            key[keyMoveLookup[face][1]] = key[keyMoveLookup[face][3]];
            key[keyMoveLookup[face][3]] = t;
        }
        return key;
    }
}