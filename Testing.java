public class Testing {
    public static void main(String[] args) {
        for (int n = 5; n <= 9; n++) {
            testCubeSpeed(n);
        }
    }
    public static void testCubeSpeed(int n) {
        Cube testCube = new Cube();

        System.out.println(testCube.isSolved());
        long numberOfStates = (long) Math.pow(10, n);

        // int j = 0;

        long startTime = System.currentTimeMillis();
        for (long i = 0; i < numberOfStates; i++) {
            testCube.applyMove(0); // always apply the same move for simplicity
            // j++;
            // if (j == 12) {
            //     j = 0;
            // }
        }
        long endTime = System.currentTimeMillis();

        System.out.println("Applied 10^" + n + " U moves in " + (endTime - startTime)/1000.0 + " s");
    }
}
