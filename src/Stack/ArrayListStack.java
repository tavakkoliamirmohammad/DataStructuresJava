package Stack;

import List.ArrayList;

public class ArrayListStack<E> implements Stack<E> {

    private ArrayList<E> data = new ArrayList<E>();

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public void push(E e) {
        data.add(data.size(), e);
    }

    @Override
    public E top() {
        if (data.isEmpty())
            return null;
        return data.get(data.size() - 1);
    }

    @Override
    public E pop() {
        return data.remove(data.size() - 1);
    }
}
