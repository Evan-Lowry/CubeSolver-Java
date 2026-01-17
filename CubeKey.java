import java.util.Arrays;

public final class CubeKey {
    private static final String B64_URL = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_";
    private static final int DIGITS = 54; // number of base-6 digits (one per sticker)
    private static final int CHUNKS = 24; // 24 * 6 = 144 bits, >= 54 * log2(6) ≈ 139.59

    // fast decode table for ASCII chars used in B64_URL
    private static final byte[] DECODE = new byte[128];
    static {
        Arrays.fill(DECODE, (byte) -1);
        for (int i = 0; i < B64_URL.length(); i++) {
            DECODE[B64_URL.charAt(i)] = (byte) i;
        }
    }

    private CubeKey() { /* utility */ }

    /**
     * Encode a byte array of length 54 (each element 0..5) into a compact 24-char base64-url-safe key.
     * digits[0] is treated as the MOST significant base-6 digit.
     *
     * This implementation avoids BigInteger by keeping the running value in base-64 chunks
     * and performing multiply-by-6 + add(d) using primitive arithmetic.
     */
    public static String encode(byte[] digits) {
        if (digits == null) throw new IllegalArgumentException("digits is null");
        if (digits.length != DIGITS) throw new IllegalArgumentException("digits length must be " + DIGITS);

        int[] chunks = new int[CHUNKS]; // base-64 digits, big-endian in chunks[0]..chunks[CHUNKS-1]

        // For each base-6 digit (msb to lsb): value = value * 6 + d
        for (int idx = 0; idx < DIGITS; idx++) {
            int d = digits[idx] & 0xFF;
            if (d < 0 || d > 5) throw new IllegalArgumentException("digits[" + idx + "] must be in 0..5");
            int carry = d;
            for (int i = CHUNKS - 1; i >= 0; i--) {
                int x = chunks[i] * 6 + carry; // x <= 63*6 + 5 = 383
                chunks[i] = x & 0x3F;         // mod 64
                carry = x >>> 6;              // divide by 64
            }
            if (carry != 0) {
                throw new IllegalArgumentException("digits encode to a value that does not fit in " + CHUNKS + " base64 chars");
            }
        }

        char[] out = new char[CHUNKS];
        for (int i = 0; i < CHUNKS; i++) out[i] = B64_URL.charAt(chunks[i]);
        return new String(out);
    }

    /**
     * Decode a 24-char key produced by encode(...) back into the byte[54] base-6 digit array (values 0..5).
     *
     * This implementation builds the base-64 chunk array then repeatedly divides it by 6 to extract base-6 digits.
     */
    public static byte[] decode(String key) {
        if (key == null) throw new IllegalArgumentException("key is null");
        if (key.length() != CHUNKS) throw new IllegalArgumentException("key length must be " + CHUNKS);

        byte[] chunks = new byte[CHUNKS];
        for (int i = 0; i < CHUNKS; i++) {
            char c = key.charAt(i);
            if (c >= DECODE.length) throw new IllegalArgumentException("invalid character in key: " + c);
            int val = DECODE[c];
            if (val == -1) throw new IllegalArgumentException("invalid character in key: " + c);
            chunks[i] = (byte) val;
        }

        byte[] digits = new byte[DIGITS];
        // Repeatedly divide the base-64 number by 6, collecting remainders (lsb first)
        for (int d = DIGITS - 1; d >= 0; d--) {
            int carry = 0;
            for (int i = 0; i < CHUNKS; i++) {
                int cur = (carry << 6) | (chunks[i] & 0xFF); // cur <= 5*64+63 = 383
                int q = cur / 6;
                int r = cur - q * 6;
                chunks[i] = (byte) q;
                carry = r;
            }
            digits[d] = (byte) carry;
        }

        // ensure no leftover magnitude
        for (int i = 0; i < CHUNKS; i++) {
            if (chunks[i] != 0) throw new IllegalArgumentException("key encodes a value too large for " + DIGITS + " base-6 digits");
        }
        return digits;
    }
}