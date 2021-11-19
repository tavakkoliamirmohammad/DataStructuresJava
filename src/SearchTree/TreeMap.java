package SearchTree;

import Maps.AbstractSortedMap;
import PositionalLists.Position;
import Priority.Entry;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TreeMap<K, V> extends AbstractSortedMap<K, V> {
    protected BalancedSearchTree<K, V> tree = new BalancedSearchTree<>();

    public TreeMap(Comparator<K> c) {
        super(c);
        tree.addRoot(null);
    }


    @Override
    public Entry<K, V> firstEntry() {
        if (isEmpty())
            return null;
        return treeMin(root()).getElement();
    }

    @Override
    public Entry<K, V> lastEntry() {
        if (isEmpty())
            return null;
        return treeMin(root()).getElement();
    }

    @Override
    public Entry<K, V> ceilingEntry(K key) throws IllegalArgumentException {
        checkKey(key);
        Position<Entry<K, V>> position = treeSearch(root(), key);
        if (isInternal(position))
            return position.getElement();
        while (!isRoot(position)) {
            if (position == left(parent(position))) {
                return parent(position).getElement();
            } else {
                position = parent(position);
            }
        }
        return null;
    }

    @Override
    public Entry<K, V> floorEntry(K key) throws IllegalArgumentException {
        checkKey(key);
        Position<Entry<K, V>> position = treeSearch(root(), key);
        if (isInternal(position))
            return position.getElement();
        while (!isRoot(position)) {
            if (position == right(parent(position))) {
                return parent(position).getElement();
            } else {
                position = parent(position);
            }
        }
        return null;
    }

    @Override
    public Entry<K, V> lowerEntry(K key) throws IllegalArgumentException {
        checkKey(key);
        Position<Entry<K, V>> position = treeSearch(root(), key);
        if (isInternal(position) && isInternal(left(position)))
            return treeMax(left(position)).getElement();
        while (!isRoot(position)) {
            if (position == right(parent(position))) {
                return parent(position).getElement();
            }
            position = parent(position);
        }
        return null;
    }

    @Override
    public Entry<K, V> higherEntry(K key) throws IllegalArgumentException {
        checkKey(key);
        Position<Entry<K, V>> position = treeSearch(root(), key);
        if (isInternal(position) && isInternal(right(position)))
            return treeMin(right(position)).getElement();
        while (!isRoot(position)) {
            if (position == left(parent(position))) {
                return parent(position).getElement();
            }
            position = parent(position);
        }
        return null;
    }

    @Override
    public Iterable<Entry<K, V>> subMap(K fromKey, K toKey) throws IllegalArgumentException {
        checkKey(fromKey);                                // may throw IllegalArgumentException
        checkKey(toKey);                                  // may throw IllegalArgumentException
        ArrayList<Entry<K, V>> buffer = new ArrayList<>(size());
        if (compare(fromKey, toKey) < 0)                  // ensure that fromKey < toKey
            subMapRecurse(fromKey, toKey, root(), buffer);
        return buffer;
    }

    private void subMapRecurse(K fromKey, K toKey, Position<Entry<K, V>> p,
                               ArrayList<Entry<K, V>> buffer) {
        if (isInternal(p))
            if (compare(p.getElement(), fromKey) < 0)
                subMapRecurse(fromKey, toKey, right(p), buffer);
            else {
                subMapRecurse(fromKey, toKey, left(p), buffer);
                if (compare(p.getElement(), toKey) < 0) {
                    buffer.add(p.getElement());
                    subMapRecurse(fromKey, toKey, right(p), buffer);
                }
            }
    }

    @Override
    public int size() {
        return (tree.size() - 1) / 2;
    }


    private void expandExternal(Position<Entry<K, V>> p, Entry<K, V> entry) {
        tree.set(p, entry);
        tree.addLeft(p, null);
        tree.addRight(p, null);
    }


    protected Position<Entry<K, V>> root() {
        return tree.root();
    }


    protected Position<Entry<K, V>> parent(Position<Entry<K, V>> p) {
        return tree.parent(p);
    }

    protected Position<Entry<K, V>> left(Position<Entry<K, V>> p) {
        return tree.left(p);
    }

    protected Position<Entry<K, V>> right(Position<Entry<K, V>> p) {
        return tree.right(p);
    }

    protected Position<Entry<K, V>> sibling(Position<Entry<K, V>> p) {
        return tree.sibling(p);
    }

    protected Position<Entry<K, V>> restructure(Position<Entry<K, V>> x) {
        return tree.restructure(x);
    }

    protected boolean isRoot(Position<Entry<K, V>> p) {
        return tree.isRoot(p);
    }

    protected boolean isExternal(Position<Entry<K, V>> p) {
        return tree.isExternal(p);
    }

    protected boolean isInternal(Position<Entry<K, V>> p) {
        return tree.isInternal(p);
    }

    protected void set(Position<Entry<K, V>> p, Entry<K, V> e) {
        tree.set(p, e);
    }

    protected Entry<K, V> remove(Position<Entry<K, V>> p) {
        return tree.remove(p);
    }

    protected void rotate(Position<Entry<K, V>> p) {
        tree.rotate(p);
    }

    protected void rebalanceInsert(Position<Entry<K, V>> p) {
    }

    protected void rebalanceDelete(Position<Entry<K, V>> p) {
    }

    protected void rebalanceAccess(Position<Entry<K, V>> p) {
    }

    private Position<Entry<K, V>> treeSearch(Position<Entry<K, V>> position, K key) {
        if (isExternal(position))
            return position;
        int comp = compare(position.getElement(), key);
        if (comp == 0)
            return position;
        if (comp < 0)
            return treeSearch(left(position), key);
        return treeSearch(right(position), key);
    }

    @Override
    public V get(K key) throws IllegalArgumentException {
        checkKey(key);
        Position<Entry<K, V>> position = treeSearch(root(), key);
        rebalanceAccess(position);
        if (isExternal(position))
            return null;
        return position.getElement().getValue();
    }

    @Override
    public V put(K key, V value) throws IllegalArgumentException {
        checkKey(key);
        Position<Entry<K, V>> position = treeSearch(root(), key);
        if (isExternal(position)) {
            expandExternal(position, new MapEntry<>(key, value));
            rebalanceInsert(position);
            return null;
        }
        V oldValue = position.getElement().getValue();
        set(position, new MapEntry<>(key, value));
        rebalanceAccess(position);
        return oldValue;
    }

    @Override
    public V remove(K key) {
        checkKey(key);
        Position<Entry<K, V>> position = treeSearch(root(), key);
        if (isExternal(position)) {
            rebalanceAccess(position);
            return null;
        }
        V old = position.getElement().getValue();
        if (isInternal(left(position)) && isInternal(right(position))) {
            Position<Entry<K, V>> replacement = treeMax(left(position));
            set(position, replacement.getElement());
            position = replacement;
        }
        Position<Entry<K, V>> leaf = (isExternal(left(position))) ? left(position) : right(position);
        Position<Entry<K, V>> sibling = sibling(leaf);
        remove(leaf);
        remove(position);
        rebalanceDelete(sibling);
        return old;
    }

    @Override
    public Iterable<Entry<K, V>> entrySet() {
        List<Entry<K, V>> buffer = new ArrayList<>(size());
        for (Position<Entry<K, V>> position : tree.inorder())
            if (isInternal(position))
                buffer.add(position.getElement());
        return buffer;
    }

    protected Position<Entry<K, V>> treeMax(Position<Entry<K, V>> position) {
        Position<Entry<K, V>> walk = position;
        while (isInternal(walk)) {
            walk = right(walk);
        }
        return parent(walk);
    }

    protected Position<Entry<K, V>> treeMin(Position<Entry<K, V>> position) {
        Position<Entry<K, V>> walk = position;
        while (isInternal(walk)) {
            walk = left(walk);
        }
        return parent(walk);
    }
}
