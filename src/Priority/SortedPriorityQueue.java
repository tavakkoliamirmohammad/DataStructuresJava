package Priority;

import PositionalLists.LinkedPositionalList;
import PositionalLists.PositionalList;
import PositionalLists.Position;

import java.util.Comparator;

public class SortedPriorityQueue<K, V> extends AbstractPriorityQueue<K, V> {
    private PositionalList<Entry<K, V>> list = new LinkedPositionalList<Entry<K, V>>();

    public SortedPriorityQueue(Comparator<K> comparator) {
        super(comparator);
    }


    @Override
    public int size() {
        return list.size();
    }

    @Override
    public Entry<K, V> insert(K key, V value) throws IllegalArgumentException {
        checkKey(key);
        Entry<K, V> entry = new PQEntry<>(key, value);
        Position<Entry<K, V>> walk = list.last();
        while (walk != null && compare(entry, walk.getElement()) < 0)
            walk = list.before(walk);
        if (walk == null)
            list.addFirst(entry);
        list.addAfter(walk, entry);
        return entry;
    }

    @Override
    public Entry<K, V> min() {
        if (isEmpty()) return null;
        return list.first().getElement();
    }

    @Override
    public Entry<K, V> removeMin() {
        if (isEmpty()) return null;
        return list.remove(list.first());
    }
}
