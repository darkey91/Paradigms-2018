package queue;

public class QueueTest {

    public static void main(String[] args) {
        System.out.println("oj");
        test(new ArrayQueue());
        System.out.println("Tut next: ");
        test(new LinkedQueue());
    }

    public static void fill(Queue queue) {
        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
        }
    }

    public static void dump(Queue queue) {
        while(!queue.isEmpty()) {
            System.out.println(queue.size() + " " + queue.dequeue());
        }
    }

    public static void test(Queue queue) {
        fill(queue);

        Object[] p = queue.toArray();
        for(int i = 0; i < p.length; i++) {
            System.out.println(p[i]);

        }
        dump(queue);
    }
}
