public class Testing {
    public static void main(String[] args) {
        for (int n = 9; n <= 9; n++) {
            testCubeSpeed(n);
        }
    }
    public static void testCubeSpeed(int n) {
        Cube testCube = new Cube();

        long numberOfStates = (long) (Math.pow(10, n));

        long startTime = System.currentTimeMillis();
        for (long i = 0; i < numberOfStates; i++) {
            testCube.applyMove(0);
            // testCube.applyMove(1);
            // testCube.applyMove(2);
            // testCube.applyMove(3);
            // testCube.applyMove(4);
            // testCube.applyMove(5);
            // testCube.applyMove(6);
            // testCube.applyMove(7);
            // testCube.applyMove(8);
            // testCube.applyMove(9);
            // testCube.applyMove(10);
            // testCube.applyMove(11);
        }
        long endTime = System.currentTimeMillis();

        System.out.println("Applied 10^" + n + " moves in " + (endTime - startTime)/1000.0 + " s");
    }
}
