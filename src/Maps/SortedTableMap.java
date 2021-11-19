package Maps;

import Priority.Entry;

import java.util.ArrayList;
import java.util.Comparator;

public class SortedTableMap<K, V> extends AbstractSortedMap<K, V> {
    private ArrayList<MapEntry<K, V>> mapEntries = new ArrayList<>();

    protected SortedTableMap(Comparator<K> c) {
        super(c);
    }

    private int findIndex(K key, int low, int high) {
        if (high < low) return high + 1;
        int mid = (low + high) / 2;
        int comp = compare(key, mapEntries.get(mid));
        if (comp == 0)
            return mid;
        if (comp < 0)
            return findIndex(key, low, mid - 1);
        return findIndex(key, mid + 1, high);
    }

    private int findKey(K key) {
        return findIndex(key, 0, mapEntries.size() - 1);
    }

    private Entry<K, V> safeEntry(int index) {
        if (index < 0 || index >= mapEntries.size())
            return null;
        return mapEntries.get(index);
    }

    @Override
    public Entry<K, V> firstEntry() {
        return safeEntry(0);
    }

    @Override
    public Entry<K, V> lastEntry() {
        return safeEntry(mapEntries.size() - 1);
    }

    @Override
    public Entry<K, V> ceilingEntry(K key) throws IllegalArgumentException {
        return safeEntry(findKey(key));
    }

    @Override
    public Entry<K, V> floorEntry(K key) throws IllegalArgumentException {
        int j = findKey(key);
        if (j == size() || !mapEntries.get(j).getKey().equals(key))
            --j;
        return safeEntry(j);
    }

    @Override
    public Entry<K, V> lowerEntry(K key) throws IllegalArgumentException {
        return safeEntry(findKey(key) - 1);
    }

    @Override
    public Entry<K, V> higherEntry(K key) throws IllegalArgumentException {
        int j = findKey(key);
        if (j < size() && mapEntries.get(j).getKey().equals(key))
            ++j;
        return safeEntry(j);
    }

    @Override
    public Iterable<Entry<K, V>> subMap(K fromKey, K toKey) throws IllegalArgumentException {
        return snapshot(findKey(fromKey), toKey);
    }

    @Override
    public int size() {
        return mapEntries.size();
    }

    @Override
    public V get(K key) {
        int j = findKey(key);
        if (j == size() || !mapEntries.get(j).getKey().equals(key))
            return null;
        return mapEntries.get(j).getValue();
    }

    @Override
    public V put(K key, V value) {
        int j = findKey(key);
        if (j < size() && compare(key, mapEntries.get(j)) == 0)
            return mapEntries.get(j).setValue(value);
        mapEntries.add(new MapEntry<>(key, value));
        return null;
    }

    @Override
    public V remove(K key) {
        int j = findKey(key);
        if (j == size() || compare(key, mapEntries.get(j)) != 0)
            return null;
        return mapEntries.remove(j).getValue();
    }

    private Iterable<Entry<K, V>> snapshot(int startIndex, K toKey) {
        ArrayList<Entry<K, V>> buffer = new ArrayList<>();
        int j = startIndex;
        while (j < size() && (toKey == null || compare(toKey, mapEntries.get(j)) > 0)) {
            buffer.add(mapEntries.get(j));
        }
        return buffer;
    }

    @Override
    public Iterable<Entry<K, V>> entrySet() {
        return snapshot(0, null);
    }
}
