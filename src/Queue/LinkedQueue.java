package Queue;

import LinkedList.SinglyLinkedList;

public class LinkedQueue<E> implements Queue<E> {
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
    public void enqueue(E element) {
        singlyLinkedList.addLast(element);
    }

    @Override
    public E dequeue() {
        return singlyLinkedList.removeFirst();
    }

    @Override
    public E first() {
        return singlyLinkedList.first();
    }
}
