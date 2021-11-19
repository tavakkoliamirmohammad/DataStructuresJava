package Priority;

import PositionalLists.LinkedPositionalList;
import PositionalLists.Position;

import java.util.Comparator;

public class UnsortedPriorityQueue<K, V> extends AbstractPriorityQueue<K, V> {

    private LinkedPositionalList<Entry<K, V>> list = new LinkedPositionalList<Entry<K, V>>();

    public UnsortedPriorityQueue(Comparator<K> comparator) {
        super(comparator);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public Entry<K, V> insert(K key, V value) throws IllegalArgumentException{
        checkKey(key);
        Entry<K, V> entry = new PQEntry<>(key, value);
        list.addLast(entry);
        return entry;
    }

    @Override
    public Entry<K, V> min() {
        if (isEmpty()) return null;
        return findMin().getElement();
    }

    @Override
    public Entry<K, V> removeMin() {
        if (isEmpty()) return null;
        return list.remove(findMin());
    }

    private Position<Entry<K, V>> findMin() {
        Position<Entry<K, V>> minItem = list.first();
        for (Position<Entry<K, V>> position : list) {
            if (compare(position.getElement(), minItem.getElement()) < 0) {
                minItem = position;
            }
        }
        return minItem;
    }
}
