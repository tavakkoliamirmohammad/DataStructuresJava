package Tree;

import PositionalLists.Position;
import Queue.Queue;
import Queue.LinkedQueue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractTree<E> implements Tree<E> {

    @Override
    public boolean isInternal(Position<E> position) throws IllegalArgumentException {
        return numberOfChildren(position) != 0;
    }

    @Override
    public boolean isExternal(Position<E> position) throws IllegalArgumentException {
        return numberOfChildren(position) == 0;
    }

    @Override
    public boolean isRoot(Position<E> position) throws IllegalArgumentException {
        return position == root();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    public int depth(Position<E> position) {
        if (isRoot(position))
            return 0;
        return 1 + depth(parent(position));
    }

    public int height(Position<E> position) {
        int h = 0;
        for (Position<E> child : children(position)) {
            h = Math.max(h, 1 + height(child));
        }
        return h;
    }

    private class ElementIterator implements Iterator<E> {
        Iterator<Position<E>> positionIterator = positions().iterator();

        @Override
        public boolean hasNext() {
            return positionIterator.hasNext();
        }

        @Override
        public E next() {
            return positionIterator.next().getElement();
        }

        @Override
        public void remove() {
            positionIterator.remove();
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new ElementIterator();
    }

    private void preorderSubtree(Position<E> position, List<Position<E>> snapshot) {
        snapshot.add(position);
        for (Position<E> child : children(position)) {
            preorderSubtree(child, snapshot);
        }
    }

    private void postorderSubtree(Position<E> position, List<Position<E>> snapshot) {
        for (Position<E> chid : children(position)) {
            postorderSubtree(chid, snapshot);
        }
        snapshot.add(position);
    }

    public Iterable<Position<E>> preorder() {
        List<Position<E>> snapshot = new ArrayList<>();
        if (!isEmpty())
            preorderSubtree(root(), snapshot);
        return snapshot;
    }

    public Iterable<Position<E>> postorder() {
        List<Position<E>> snapshot = new ArrayList<>();
        if (!isEmpty())
            postorderSubtree(root(), snapshot);
        return snapshot;
    }

    public Iterable<Position<E>> breadthFirst() {
        List<Position<E>> snapshot = new ArrayList<>();
        if (!isEmpty()) {
            Queue<Position<E>> fringe = new LinkedQueue<Position<E>>();
            fringe.enqueue(root());
            while (!fringe.isEmpty()){
                Position<E> position = fringe.dequeue();
                snapshot.add(position);
                for(Position<E> child: children(position)){
                    fringe.enqueue(child);
                }
            }
        }
        return snapshot;
    }

    @Override
    public Iterable<Position<E>> positions() {
        return preorder();
    }
}
