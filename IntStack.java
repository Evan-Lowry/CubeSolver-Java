import java.util.Arrays;

final class IntStack {
    private int[] data = new int[32];
    private int size = 0;

    public int size() { return size; }
    public int get(int i) { return data[i]; }
    
    public void push(int v) {
        if (size == data.length) data = Arrays.copyOf(data, data.length * 2);
        data[size++] = v;
    }

    public int pop() { return data[--size]; }
    public int peek() { return data[size - 1]; }
    public void removeLast() { --size; } // if you don't need the popped value
}