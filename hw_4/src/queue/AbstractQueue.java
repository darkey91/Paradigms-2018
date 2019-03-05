package queue;

public abstract class AbstractQueue implements Queue {
    protected int size;
    protected Object[] arr = new Object[size];

    public void enqueue(Object element) {
        assert element != null;

        enqueueImpl(element);
        size++;
    }

    protected abstract void enqueueImpl(Object element);

    public Object element() {
        assert size > 0;
        return elementImpl();
    }

    protected abstract Object elementImpl();

    public Object dequeue() {
        Object res = element();
        remove();
        size--;
        return res;
    }

    public abstract void remove();

    public void clear() {
        size = 0;
        clearImpl();
    }

    public abstract void clearImpl();

    public Object[] toArray() {
        int i = 0;
        Object[] arr = new Object[size];
        while (!isEmpty()) {
            arr[i++] = dequeue();
        }
        for (i = 0; i < arr.length; i++) {
            enqueue(arr[i]);
        }

        return arr;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

}
