package Queue;

import LinkedList.CircularlyLinkedList;

public class LinkedCircularQueue<E> implements CircularQueue<E> {

    private CircularlyLinkedList<E> circularlyLinkedList = new CircularlyLinkedList<>();

    @Override
    public void rotate() {
        circularlyLinkedList.rotate();
    }

    @Override
    public int size() {
        return circularlyLinkedList.getSize();
    }

    @Override
    public boolean isEmpty() {
        return circularlyLinkedList.isEmpty();
    }

    @Override
    public void enqueue(E element) {
        circularlyLinkedList.addFirst(element);
    }

    @Override
    public E dequeue() {
        return circularlyLinkedList.removeFirst();
    }

    @Override
    public E first() {
        return circularlyLinkedList.first();
    }
}
