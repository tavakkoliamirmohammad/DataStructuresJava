package Stack;

import java.util.Objects;

public class ArrayStack<E> implements Stack<E> {
    private static final int CAPACITY = 1000;
    private E[] data;
    private int t = -1;

    public ArrayStack() {
        this(CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public ArrayStack(int capacity) {
        data = (E[]) new Object[capacity];
    }

    @Override
    public int size() {
        return t + 1;
    }

    @Override
    public boolean isEmpty() {
        return t == -1;
    }

    @Override
    public void push(E e) throws IllegalStateException {
        if (size() == data.length) throw new IllegalStateException("Stack is full");
        data[++t] = e;
    }

    @Override
    public E top() {
        return data[t];
    }

    @Override
    public E pop() {
        if (isEmpty()) return null;
        E element = top();
        data[t] = null;
        --t;
        return element;
    }
}
