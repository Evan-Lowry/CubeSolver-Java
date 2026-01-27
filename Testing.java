import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Testing {
    public static Heuristic heuristic;
    public static void main(String[] args) {
        testHeuristic();
    }

    private static void testHeuristic() {
        Heuristic heuristic = new Heuristic();
        long startTime = System.currentTimeMillis();
        heuristic.precomputeTables();
        long endTime = System.currentTimeMillis();
        System.out.println("Heuristic precomputation took " + formatTime(endTime - startTime));
    }

    private static String formatNumber(long number) {
        if (number < 1000) {
            return Long.toString(number);
        }
        int exp = (int) (Math.log10(number) / 3);
        char suffix = "kMGTPE".charAt(exp - 1);
        double scaled = number / Math.pow(1000, exp);
        return String.format("%.1f%c", scaled, suffix);
    }

    private static String formatTime(long timeInMillis) {
        long seconds = timeInMillis / 1000;
        long minutes = seconds / 60;
        seconds %= 60;
        long millis = timeInMillis % 1000;
        return String.format("Time taken: %d min, %d sec, %d ms%n", minutes, seconds, millis);
    }
}
