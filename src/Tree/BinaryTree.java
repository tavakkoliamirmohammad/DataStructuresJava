package Tree;

import PositionalLists.Position;

public interface BinaryTree<E> extends Tree<E> {
    Position<E> left(Position<E> position) throws IllegalArgumentException;

    Position<E> right(Position<E> position) throws IllegalArgumentException;

    Position<E> sibling(Position<E> position) throws IllegalArgumentException;
}
