package Tree;

import List.ArrayList;
import List.List;
import PositionalLists.Position;

public class ArrayBinaryTree<E> extends AbstractBinaryTree<E> {

    private static class Node<E> implements Position<E> {

        private E element;
        private int index;

        public int getIndex() {
            return index;
        }

        public void setElement(E element) {
            this.element = element;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        @Override
        public E getElement() throws IllegalStateException {
            return element;
        }

        public Node(E element, int index) {
            this.element = element;
            this.index = index;
        }
    }

    private List<Node<E>> nodes = new ArrayList<Node<E>>();

    private Node<E> validate(Position<E> position) throws IllegalArgumentException {
        if (!(position instanceof Node)) throw new IllegalArgumentException("Node is a valid one");
        return (Node<E>) position;
    }

    @Override
    public Position<E> left(Position<E> position) throws IllegalArgumentException {
        Node<E> node = validate(position);
        int index = 2 * node.getIndex() + 1;
        return nodes.get(index);
    }

    @Override
    public Position<E> right(Position<E> position) throws IllegalArgumentException {
        Node<E> node = validate(position);
        int index = 2 * node.getIndex() + 2;
        return nodes.get(index);
    }

    @Override
    public Position<E> root() {
        return nodes.get(0);
    }

    @Override
    public Position<E> parent(Position<E> position) throws IllegalArgumentException {
        Node<E> node = validate(position);
        int index = (node.getIndex() - 1) / 2;
        return nodes.get(index);
    }

    @Override
    public int size() {
        return nodes.size();
    }


    public Node<E> addRoot(E element) throws IllegalStateException {
        if (root() == null) throw new IllegalStateException("Root is not empty");
        Node<E> node = new Node<E>(element, 0);
        nodes.add(0, node);
        return node;
    }

    public Node<E> addLeft(Position<E> position, E element) throws IllegalArgumentException {
        Node<E> parent = validate(position);
        if (left(parent) != null) throw new IllegalArgumentException("Left must be empty");
        int index = 2 * parent.getIndex() + 1;
        Node<E> node = new Node<>(element, index);
        nodes.add(index, node);
        return node;
    }

    public Node<E> addRight(Position<E> position, E element) throws IllegalArgumentException {
        Node<E> parent = validate(position);
        if (right(parent) != null) throw new IllegalArgumentException("Left must be empty");
        int index = 2 * parent.getIndex() + 2;
        Node<E> node = new Node<>(element, index);
        nodes.add(index, node);
        return node;
    }

    public E set(Position<E> position, E element) throws IllegalArgumentException {
        Node<E> node = validate(position);
        E temp = node.getElement();
        node.setElement(element);
        return temp;
    }
}
