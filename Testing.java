public class Testing {
    public static void main(String[] args) {
        byte[] cubeState = new byte[54];

        System.out.println(cubeState.length);

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 9; j++) {
                cubeState[9*i+j] = (byte) i;
            }
        }
        for (byte b : cubeState) {
            System.out.print(b + " ");
        }
    }
}
