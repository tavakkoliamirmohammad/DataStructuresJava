package Tree;

import PositionalLists.Position;

import java.util.ArrayList;
import java.util.List;

public class LinkedBinaryTree<E> extends AbstractBinaryTree<E> {
    protected static class Node<E> implements Position<E> {
        private E element;
        private Node<E> parent;
        private Node<E> left;
        private Node<E> right;

        public Node(E element, Node<E> parent, Node<E> left, Node<E> right) {
            this.element = element;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }

        public void setParent(Node<E> parent) {
            this.parent = parent;
        }

        public void setLeft(Node<E> left) {
            this.left = left;
        }

        public void setRight(Node<E> right) {
            this.right = right;
        }

        public Node<E> getParent() {
            return parent;
        }

        public Node<E> getLeft() {
            return left;
        }

        public Node<E> getRight() {
            return right;
        }

        public void setElement(E element) {
            this.element = element;
        }

        @Override
        public E getElement() throws IllegalStateException {
            return element;
        }
    }

    protected Node<E> root = null;
    private int size = 0;

    public LinkedBinaryTree() {

    }

    protected Node<E> validate(Position<E> position) throws IllegalArgumentException {
        if (!(position instanceof Node))
            throw new IllegalArgumentException("Not a valid position");
        Node<E> node = (Node<E>) position;
        if (node.getParent() == node)
            throw new IllegalArgumentException("Node is not part of tree anymore");
        return node;
    }

    @Override
    public Position<E> left(Position<E> position) throws IllegalArgumentException {
        Node<E> node = validate(position);
        return node.getLeft();
    }

    @Override
    public Position<E> right(Position<E> position) throws IllegalArgumentException {
        Node<E> node = validate(position);
        return node.getRight();
    }

    @Override
    public Position<E> root() throws IllegalArgumentException {
        return root;
    }

    @Override
    public Position<E> parent(Position<E> position) throws IllegalArgumentException {
        Node<E> node = validate(position);
        return node.getParent();
    }

    @Override
    public int size() {
        return size;
    }


    public Position<E> addRoot(E element) throws IllegalStateException {
        if (root != null)
            throw new IllegalStateException("Root is not empty");
        root = new Node<>(element, null, null, null);
        ++size;
        return root;
    }

    public Position<E> addLeft(Position<E> position, E element) throws IllegalArgumentException {
        Node<E> parent = validate(position);
        if (parent.getLeft() != null)
            throw new IllegalArgumentException("Left is not empty");
        Node<E> node = new Node<>(element, parent, null, null);
        parent.setLeft(node);
        ++size;
        return node;
    }

    public Position<E> addRight(Position<E> position, E element) throws IllegalArgumentException {
        Node<E> parent = validate(position);
        if (parent.getRight() != null)
            throw new IllegalArgumentException("Right is not empty");
        Node<E> node = new Node<>(element, parent, null, null);
        parent.setRight(node);
        ++size;
        return node;
    }

    public E set(Position<E> position, E element) throws IllegalArgumentException {
        Node<E> node = validate(position);
        E temp = node.getElement();
        node.setElement(element);
        return temp;
    }

    public void attach(Position<E> position, LinkedBinaryTree<E> t1, LinkedBinaryTree<E> t2) throws IllegalArgumentException {
        Node<E> node = validate(position);
        if (isInternal(node)) throw new IllegalArgumentException("Node must be a leaf");
        size += (t1.size() + t2.size());
        if (!t1.isEmpty()) {
            t1.root.setParent(node);
            node.setLeft(t1.root);
            t1.root = null;
            t1.size = 0;
        }
        if (!t2.isEmpty()) {
            t2.root.setParent(node);
            node.setRight(t2.root);
            t2.root = null;
            t2.size = 0;
        }
    }

    public E remove(Position<E> position) throws IllegalArgumentException {
        Node<E> node = validate(position);
        if (numberOfChildren(position) == 2)
            throw new IllegalArgumentException("Position has 2 children");
        Node<E> child = node.getLeft() != null ? node.getLeft() : node.getRight();
        if (child != null) {
            child.setParent(node.parent);
        }
        if (node == root) {
            root = child;
        } else {
            Node<E> parent = node.getParent();
            if (parent.getLeft() == node) {
                parent.setLeft(child);
            } else {
                parent.setRight(child);
            }
        }
        --size;
        E element = node.getElement();
        node.setElement(null);
        node.setRight(null);
        node.setLeft(null);
        node.setParent(node);
        return element;
    }

    private void inorderSubtree(Position<E> position, List<Position<E>> snapshot) {
        inorderSubtree(left(position), snapshot);
        snapshot.add(position);
        inorderSubtree(right(position), snapshot);
    }

    public Iterable<Position<E>> inorder(){
        List<Position<E>> snapshot = new ArrayList<>();
        if (!isEmpty())
            inorderSubtree(root(), snapshot);
        return snapshot;
    }
}
