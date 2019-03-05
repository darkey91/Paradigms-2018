package queue;
import java.util.Arrays;

public class ArrayQueue extends AbstractQueue {
    private Object[] elements = new Object[200];
    private int begin = 0;

    private void ensureCapacity(int capacity) {
        if (capacity <= elements.length) {
            return;
        }

        Object[] newElements = new Object[2 * capacity];

        for (int i = 0; i < size; i++) {
            newElements[i] = elements[(begin + i) % elements.length];
        }

        begin = 0;
        elements = newElements;
    }

    protected void enqueueImpl(Object element) {
        ensureCapacity(size + 1);
        elements[(begin + size) % elements.length] = element;
    }

    protected Object elementImpl() {
        return elements[begin];
    }

    public void remove() {
        begin = (begin + 1) % elements.length;
    }

    public void clearImpl() {
        elements = new Object[size / 2 + 5];
        begin = 0;
    }

    public ArrayQueue makeCopy() {
        final ArrayQueue copy = new ArrayQueue();
        copy.size = size;
        copy.begin = begin;
        copy.ensureCapacity(size);
        copy.elements = Arrays.copyOf(elements, size);
        return copy;
    }

}