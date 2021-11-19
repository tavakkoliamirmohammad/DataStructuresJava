package Maps;

import Priority.Entry;

import java.util.ArrayList;

public class ProbeHashMap<K, V> extends AbstractHashMap<K, V> {

    private MapEntry<K, V>[] mapEntries;
    private final MapEntry<K, V> DEFUNC = new MapEntry<>(null, null);

    public ProbeHashMap(int capacity, int prime) {
        super(capacity, prime);
    }

    public ProbeHashMap(int capacity) {
        super(capacity);
    }

    public ProbeHashMap() {
    }

    @Override
    protected void createTable() {
        mapEntries = (MapEntry<K, V>[]) new MapEntry[capacity];
    }

    private boolean isAvailable(int j) {
        return mapEntries[j] == null || mapEntries[j] == DEFUNC;
    }

    private int findSlot(int h, K key) {
        int available = -1;
        int j = h;
        do {
            if (isAvailable(j)) {
                if (available == -1) available = j;
                if (mapEntries[j] == null) break;
            } else if (mapEntries[j].getKey().equals(key))
                return j;
            j = (j + 1) % capacity;
        } while (j != h);
        return -(available + 1);
    }

    @Override
    protected V bucketGet(int hash, K key) {
        int i = findSlot(hash, key);
        if (i < 0) return null;
        return mapEntries[i].getValue();
    }

    @Override
    protected V bucketPut(int hash, K key, V value) {
        int i = findSlot(hash, key);
        if (i >= 0)
            return mapEntries[i].setValue(value);
        mapEntries[-(i + 1)] = new MapEntry<K, V>(key, value);
        n++;
        return null;
    }

    @Override
    protected V bucketRemove(int hash, K key) {
        int i = findSlot(hash, key);
        if (i < 0)
            return null;
        V entry = mapEntries[i].getValue();
        mapEntries[i] = DEFUNC;
        n--;
        return entry;
    }

    @Override
    public Iterable<Entry<K, V>> entrySet() {
        ArrayList<Entry<K,V>> buffer = new ArrayList<>();
        for (int i = 0; i < capacity; ++i)
            if (isAvailable(i))
                buffer.add(mapEntries[i]);
        return buffer;
    }
}
