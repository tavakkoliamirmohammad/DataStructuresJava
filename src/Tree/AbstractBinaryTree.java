package Tree;

import PositionalLists.LinkedPositionalList;
import PositionalLists.Position;

public abstract class AbstractBinaryTree<E> extends AbstractTree<E> implements BinaryTree<E> {

    @Override
    public Position<E> sibling(Position<E> position) throws IllegalArgumentException {
        Position<E> parent = parent(position);
        if(parent == null) return null;
        if (left(parent) == position)
            return right(parent);
        else
            return left(parent);
    }

    @Override
    public Iterable<Position<E>> children(Position<E> position) throws IllegalArgumentException {
        LinkedPositionalList<E> positionalList = new LinkedPositionalList<E>();
        if(left(position) != null)
            positionalList.addFirst(left(position).getElement());
        if(right(position) != null)
            positionalList.addLast(right(position).getElement());
        return positionalList;
    }

    @Override
    public int numberOfChildren(Position<E> position) throws IllegalArgumentException {
        return 0;
    }

}
