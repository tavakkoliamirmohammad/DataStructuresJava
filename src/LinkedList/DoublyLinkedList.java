package LinkedList;

public class DoublyLinkedList<E> {
    private static class Node<E> {
        private final E element;
        private Node<E> next;
        private Node<E> previous;

        public Node(E element, Node<E> previous, Node<E> next) {
            this.element = element;
            this.next = next;
            this.previous = previous;
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

        public Node<E> getPrevious() {
            return previous;
        }

        public void setPrevious(Node<E> previous) {
            this.previous = previous;
        }
    }

    private final Node<E> header;
    private final Node<E> trailer;
    private int size = 0;

    public DoublyLinkedList() {
        header = new Node<>(null, null, null);
        trailer = new Node<>(null, null, null);
        header.setNext(trailer);
        trailer.setPrevious(header);
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int getSize() {
        return size;
    }

    public E first() {
        if (isEmpty()) return null;
        return header.getNext().getElement();
    }

    public E last() {
        if (isEmpty()) return null;
        return trailer.getPrevious().getElement();
    }

    public void addFirst(E element) {
        addBetween(element, header, header.getNext());
    }

    public void addLast(E element) {
        addBetween(element, trailer.getPrevious(), trailer);
    }

    private void addBetween(E element, Node<E> previous, Node<E> next) {
        Node<E> node = new Node<>(element, previous, next);
        next.setPrevious(node);
        previous.setNext(node);
        ++size;
    }

    public E removeFirst() {
        if (isEmpty()) return null;
        return removeBetween(header.getNext());
    }

    public E removeLast() {
        if (isEmpty()) return null;
        return removeBetween(trailer.getPrevious());
    }

    private E removeBetween(Node<E> node) {
        Node<E> previous = node.getPrevious();
        Node<E> next = node.getNext();
        previous.setNext(next);
        next.setPrevious(previous);
        --size;
        return node.getElement();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        DoublyLinkedList other = (DoublyLinkedList) obj;
        if (other.size != size) return false;
        Node walkA = other.header;
        Node walkB = header;
        while (walkA != null) {
            if(!walkA.getElement().equals(walkB.getElement())) return false;
            walkA = walkA.getNext();
            walkB = walkB.getNext();
        }
        return true;
    }
}
