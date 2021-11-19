package Tree;

import PositionalLists.Position;

import java.util.Iterator;

public interface Tree<E> extends Iterable<E> {

    Position<E> root();

    Position<E> parent(Position<E> position) throws IllegalArgumentException;

    Iterable<Position<E>> children(Position<E> position) throws IllegalArgumentException;

    int numberOfChildren(Position<E> position) throws IllegalArgumentException;

    boolean isInternal(Position<E> position) throws IllegalArgumentException;

    boolean isExternal(Position<E> position) throws IllegalArgumentException;

    boolean isRoot(Position<E> position) throws IllegalArgumentException;

    int size();

    boolean isEmpty();

    Iterator<E> iterator();

    Iterable<Position<E>> positions();
}
