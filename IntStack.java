import java.util.Arrays;

final class IntStack {
    private int[] data = new int[16];
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
    public IntStack copy() {
        IntStack copy = new IntStack();
        for (int i = 0; i < size; i++) {
            copy.push(data[i]);
        }
        return copy;
    }
    public void insert(int index, int value) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        if (size == data.length) data = Arrays.copyOf(data, data.length * 2);
        System.arraycopy(data, index, data, index + 1, size - index);
        data[index] = value;
        size++;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) {
            sb.append(data[i]);
            if (i < size - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}