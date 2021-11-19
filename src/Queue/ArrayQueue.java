package Queue;

public class ArrayQueue<E> implements Queue<E> {
    private E[] data;
    private int front = 0;
    private int sz = 0;

    private static final int CAPACITY = 1000;

    public ArrayQueue() {
        this(CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public ArrayQueue(int capacity) {
        data = (E[]) new Object[capacity];
    }

    @Override
    public int size() {
        return sz;
    }

    @Override
    public boolean isEmpty() {
        return sz == 0;
    }

    @Override
    public void enqueue(E element) throws IllegalStateException {
        if (sz == data.length) throw new IllegalStateException("Queue is full");
        int availablePosition = (front + sz) % data.length;
        data[availablePosition] = element;
        ++sz;
    }

    @Override
    public E dequeue() {
        if (sz == 0) return null;
        E element = data[front];
        data[front] = null;
        front = (front + 1) % data.length;
        sz--;
        return element;
    }

    @Override
    public E first() {
        if (isEmpty()) return null;
        return data[front];
    }
}
