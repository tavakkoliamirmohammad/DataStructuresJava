package PositionalLists;

public interface Position<E> {
    E getElement() throws IllegalStateException;
}
