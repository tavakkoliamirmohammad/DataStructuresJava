package Stack;

import LinkedList.SinglyLinkedList;

public class LinkedStack<E> implements Stack<E> {
    private SinglyLinkedList<E> singlyLinkedList = new SinglyLinkedList<>();

    @Override
    public int size() {
        return singlyLinkedList.getSize();
    }

    @Override
    public boolean isEmpty() {
        return singlyLinkedList.isEmpty();
    }

    @Override
    public void push(E e) {
        singlyLinkedList.addFirst(e);
    }

    @Override
    public E top() {
        return singlyLinkedList.first();
    }

    @Override
    public E pop() {
        return singlyLinkedList.removeFirst();
    }
}
