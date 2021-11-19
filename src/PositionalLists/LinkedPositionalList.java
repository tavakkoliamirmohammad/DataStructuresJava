package PositionalLists;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedPositionalList<E> implements PositionalList<E> {
    @Override
    public Iterator<E> iterator() {
        return new ElementIterator();
    }

    private static class Node<E> implements Position<E> {
        private E element;
        private Node<E> prev;
        private Node<E> next;

        public Node(E element, Node<E> prev, Node<E> next) {
            this.element = element;
            this.prev = prev;
            this.next = next;
        }

        public void setElement(E element) {
            this.element = element;
        }

        public Node<E> getPrev() {
            return prev;
        }

        public void setPrev(Node<E> prev) {
            this.prev = prev;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }

        @Override
        public E getElement() throws IllegalStateException {
            if (next == null)
                throw new IllegalStateException("Invalid node");
            return element;
        }
    }

    private class PositionalIterator implements Iterator<Position<E>> {

        private Position<E> cursor = first();
        private Position<E> recent = null;

        @Override
        public boolean hasNext() {
            return cursor != null; // compare with trailer
        }

        @Override
        public Position<E> next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException("No element left");
            }
            recent = cursor;
            cursor = after(cursor);
            return cursor;
        }

        @Override
        public void remove() throws IllegalStateException {
            if (recent == null) throw new IllegalStateException("Nothing to remove");
            LinkedPositionalList.this.remove(recent);
            recent = null;
        }
    }

    private class ElementIterator implements Iterator<E> {
        private PositionalIterator positionalIterator = new PositionalIterator();

        @Override
        public boolean hasNext() {
            return positionalIterator.hasNext();
        }

        @Override
        public E next() {
            return positionalIterator.next().getElement();
        }

        @Override
        public void remove() {
            positionalIterator.remove();
        }
    }

    private final Node<E> header;
    private final Node<E> trailer;
    private int size = 0;

    public LinkedPositionalList() {
        header = new Node<>(null, null, null);
        trailer = new Node<>(null, header, null);
        header.setNext(trailer);
    }

    private Position<E> position(Node<E> node) {
        if (node == header || node == trailer) {
            return null;
        }
        return node;
    }

    private Node<E> validate(Position<E> p) throws IllegalArgumentException {
        if (!(p instanceof Node)) throw new IllegalArgumentException("Invalid position");
        Node<E> node = (Node<E>) p;
        if (node.getNext() == null)
            throw new IllegalArgumentException("Position is no longer in the list");
        return node;
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
    public Position<E> first() {
        return position(header.getNext());
    }

    @Override
    public Position<E> last() {
        return position(trailer.getPrev());
    }

    @Override
    public Position<E> addFirst(E element) {
        return addBetween(element, header, header.getNext());
    }

    @Override
    public Position<E> addLast(E element) {
        return addBetween(element, trailer.getPrev(), trailer);
    }

    @Override
    public E remove(Position<E> position) throws IllegalArgumentException {
        Node<E> node = validate(position);
        node.getPrev().setNext(node.getNext());
        node.getNext().setPrev(node.getPrev());
        E data = node.getElement();
        node.setPrev(null);
        node.setNext(null);
        node.setElement(null);
        --size;
        return data;
    }

    @Override
    public E set(Position<E> position, E element) throws IllegalArgumentException {
        Node<E> node = validate(position);
        E e = node.getElement();
        node.setElement(element);
        return e;
    }

    @Override
    public Position<E> addBefore(Position<E> position, E e) throws IllegalArgumentException {
        Node<E> node = validate(position);
        return addBetween(e, node.getPrev(), node);
    }

    @Override
    public Position<E> addAfter(Position<E> position, E e) throws IllegalArgumentException {
        Node<E> node = validate(position);
        return addBetween(e, node, node.getNext());
    }

    private Position<E> addBetween(E element, Node<E> pred, Node<E> succ) {
        Node<E> node = new Node<>(element, pred, succ);
        pred.setNext(node);
        succ.setPrev(node);
        ++size;
        return node;
    }

    @Override
    public Position<E> after(Position<E> position) throws IllegalArgumentException {
        Node<E> node = validate(position);
        return position(node.getNext());
    }

    @Override
    public Position<E> before(Position<E> position) throws IllegalArgumentException {
        Node<E> node = validate(position);
        return position(node.getPrev());
    }
}
