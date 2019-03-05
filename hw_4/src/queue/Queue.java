package queue;

public interface Queue extends Copiable {
    //pre: elem != null
    public void enqueue(Object element);
    //post: last element in queue = element

    //pre: size > 0
    public Object dequeue();
    //post: return first element in queue;

    public boolean isEmpty();
    //post: Is queue empty?

    public int size();
    //post: return size of queue;

    public void clear();
    //post: empty queue;


    public Object[] toArray();
    //post: rerrn array of elements of queue

    public Queue makeCopy();
}
