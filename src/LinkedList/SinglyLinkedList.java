package LinkedList;

public class SinglyLinkedList<E> {
    private static class Node<E> {
        private E data;
        private Node<E> next;

        public Node(E data, Node<E> next) {
            this.data = data;
            this.next = next;
        }

        public E getData() {
            return data;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }
    }

    private Node<E> head;
    private Node<E> tail;
    private int size = 0;

    public boolean isEmpty() {
        return size == 0;
    }

    public int getSize() {
        return size;
    }

    public E first() {
        if (isEmpty()) return null;
        return head.getData();
    }

    public E last() {
        if (isEmpty()) return null;
        return tail.getData();
    }

    public void addFirst(E element) {
        head = new Node<>(element, head);
        if (size == 0)
            tail = head;
        ++size;
    }

    public void addLast(E element) {
        Node<E> node = new Node<>(element, null);
        if (isEmpty())
            head = node;
        else
            tail.setNext(node);
        tail = node;
        ++size;
    }

    public E removeFirst() {
        if (isEmpty()) return null;
        Node<E> first = head;
        head = head.getNext();
        --size;
        if (isEmpty()) {
            tail = null;
        }
        return head.getData();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        SinglyLinkedList other = (SinglyLinkedList) obj;
        if (other.size != size) return false;
        Node walkA = other.head;
        Node walkB = head;
        while (walkA != null) {
            if(!walkA.getData().equals(walkB.getData())) return false;
            walkA = walkA.getNext();
            walkB = walkB.getNext();
        }
        return true;
    }
}
