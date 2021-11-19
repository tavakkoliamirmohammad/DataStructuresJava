package LinkedList;

public class CircularlyLinkedList<E> {
    private static class Node<E> {
        private final E element;
        private Node<E> next;

        public Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }

        public E getElement() {
            return element;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }
    }

    private Node<E> tail;
    private int size = 0;

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public E first() {
        if (isEmpty()) return null;
        return tail.getNext().getElement();
    }

    public E last() {
        if (size == 0) return null;
        return tail.getElement();
    }

    public void addFirst(E element) {
        if (isEmpty()) {
            tail = new Node<>(element, null);
            tail.setNext(tail);
        } else {
            Node<E> head = tail.getNext();
            Node<E> node = new Node<>(element, head);
            tail.setNext(node);
        }
        ++size;
    }

    public void addLast(E element) {
        addFirst(element);
        tail = tail.getNext();
        ++size;
    }

    public E removeFirst() {
        if (isEmpty()) return null;
        Node<E> head = tail.getNext();
        --size;
        if (isEmpty()) {
            tail = null;
        } else {
            tail.setNext(head.getNext());
        }
        return head.getElement();
    }

    public void rotate() {
        if (tail != null){
            tail = tail.getNext();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        CircularlyLinkedList other = (CircularlyLinkedList) obj;
        if (other.size != size) return false;
        Node walkA = other.tail.getNext();
        Node walkB = tail.getNext();
        while (walkA != other.tail) {
            if(!walkA.getElement().equals(walkB.getElement())) return false;
            walkA = walkA.getNext();
            walkB = walkB.getNext();
        }
        return true;
    }
}
