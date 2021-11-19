package SearchTree;

import PositionalLists.Position;
import Priority.Entry;

import java.util.Comparator;

public class SplayTreeMap<K, V> extends TreeMap<K, V> {
    public SplayTreeMap(Comparator<K> c) {
        super(c);
    }

    private void splay(Position<Entry<K, V>> position) {
        while (!isRoot(position)) {
            Position<Entry<K, V>> parent = parent(position);
            Position<Entry<K, V>> grand = parent(parent);
            if (grand == null)
                rotate(position);
            else if ((parent == left(grand)) == (position == left(parent))) {
                rotate(parent);
                rotate(position);
            } else {
                rotate(position);
                rotate(position);
            }
        }
    }

    @Override
    protected void rebalanceInsert(Position<Entry<K, V>> p) {
        splay(p);
    }

    @Override
    protected void rebalanceDelete(Position<Entry<K, V>> p) {
        if (!isRoot(p))
            splay(parent(p));
    }

    @Override
    protected void rebalanceAccess(Position<Entry<K, V>> p) {
        if (isExternal(p))
            p = parent(p);
        if (p != null)
            splay(p);
    }
}
