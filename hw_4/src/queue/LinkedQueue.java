//почему у поля сайз не ноль по дефолту на слайдках?
package queue;

public class LinkedQueue extends AbstractQueue {
    private Node head;
    private Node tail;

    /* обязательно ли в конструкторе делать ассерт , если я потом все равно его в общий класс вынесу?
     */

    public void enqueueImpl(Object element) {
        Node node = new Node(element, null);

        if (isEmpty()) {
            head = node;
        } else {
            tail.next = node;
        }
	    tail = node;
    }

    public Object elementImpl() {
        return head.value;
    }

    public void remove() {
        head = head.next;
    }

    public LinkedQueue makeCopy() {
        final LinkedQueue copy = new LinkedQueue();
        copy.size = size;
        copy.head = head;
        copy.tail = tail;
        return copy;
    }

    public void clearImpl() {
        head = tail = null;
    }

    private class Node {
        private Object value;
        private Node next;



        public Node(Object value, Node next) {
            assert value != null;
            this.value = value;
            this.next = next;
        }
    }


}
