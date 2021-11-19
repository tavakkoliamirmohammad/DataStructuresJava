package List;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayList<E> implements List<E>, Iterable<E> {
    @Override
    public Iterator<E> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<E> {
        private int j = 0;
        private boolean removable = false;

        @Override
        public boolean hasNext() {
            return j < size;
        }

        @Override
        public E next() throws NoSuchElementException {
            if (!hasNext())
                throw new NoSuchElementException("No element exists");
            removable = true;
            return data[j++];
        }

        @Override
        public void remove() throws IllegalStateException {
            if (!removable)
                throw new IllegalStateException("Nothing to remove");
            removable = false;
            ArrayList.this.remove(j);
            j--;
        }
    }

    private static final int CAPACITY = 10;
    private E[] data;
    private int size = 0;

    public ArrayList(int capacity) {
        data = (E[]) new Object[capacity];
    }

    public ArrayList() {
        this(CAPACITY);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E get(int i) throws IndexOutOfBoundsException {
        checkIndex(i, size);
        return data[i];
    }

    @Override
    public E set(int i, E element) throws IndexOutOfBoundsException {
        checkIndex(i, size);
        E temp = data[i];
        data[i] = element;
        return temp;
    }

    @Override
    public void add(int i, E element) throws IndexOutOfBoundsException {
        checkIndex(i, size + 1);
        if (size == data.length) {
            resize(2 * data.length);
        }
        for (int j = size - 1; j >= i; --j) {
            data[j + 1] = data[j];
        }
        data[i] = element;
        ++size;
    }

    @Override
    public E remove(int i) throws IndexOutOfBoundsException, IllegalStateException {
        checkIndex(i, size);
        if (isEmpty())
            throw new IllegalStateException("Array is empty");
        E temp = data[i];
        for (int j = i + 1; j < size; ++j) {
            data[j - 1] = data[j];
        }
        data[size - 1] = null;
        --size;
        return temp;
    }

    protected void checkIndex(int i, int n) throws IndexOutOfBoundsException {
        if (i < 0 || i >= n) {
            throw new IndexOutOfBoundsException();
        }
    }

    protected void resize(int capacity) {
        E[] temp = (E[]) new Object[capacity];
        if (size >= 0) System.arraycopy(data, 0, temp, 0, size);
        data = temp;
    }

}
