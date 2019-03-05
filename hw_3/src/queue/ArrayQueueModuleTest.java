package queue;

public class ArrayQueueModuleTest {
    public static void fill(int n) {
        for (int i = 0; i < n; i++) {
            ArrayQueueModule.enqueue(i);
        }
    }

    public static void dump() {
        while (!ArrayQueueModule.isEmpty()) {
            System.out.println(
                    ArrayQueueModule.size() + " " +
                            ArrayQueueModule.element() + " " +
                            ArrayQueueModule.dequeue());
        }
    }

    public static void main(String[] args) {
        int n = 10;
        fill(n);
        dump();

    }
}
