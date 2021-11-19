package Maps;

import Priority.Entry;

import java.util.ArrayList;

public class ChainHashMap<K, V> extends AbstractHashMap<K, V> {

    private UnsortedTableMap<K, V>[] tableMaps;

    public ChainHashMap(int capacity, int prime) {
        super(capacity, prime);
    }

    public ChainHashMap(int capacity) {
        super(capacity);
    }

    public ChainHashMap() {

    }

    @Override
    protected void createTable() {
        tableMaps = (UnsortedTableMap<K, V>[]) new UnsortedTableMap[capacity];
    }

    @Override
    protected V bucketGet(int hash, K key) {
        UnsortedTableMap<K, V> bucket = tableMaps[hash];
        if (bucket == null) return null;
        return bucket.get(key);
    }

    @Override
    protected V bucketPut(int hash, K key, V value) {
        UnsortedTableMap<K, V> bucket = tableMaps[hash];
        if (bucket == null)
            bucket = new UnsortedTableMap<K, V>();
        int oldSize = bucket.size();
        V entry = bucket.put(key, value);
        n += (bucket.size() - oldSize);
        return entry;
    }

    @Override
    protected V bucketRemove(int hash, K key) {
        UnsortedTableMap<K, V> bucket = tableMaps[hash];
        if (bucket == null)
            return null;
        int oldSize = bucket.size();
        V entry = bucket.remove(key);
        n -= (bucket.size() - oldSize);
        return entry;
    }

    @Override
    public Iterable<Entry<K, V>> entrySet() {
        ArrayList<Entry<K, V>> arrayList = new ArrayList<>();
        for (int i = 0; i < capacity; i++) {
            if (tableMaps[i] != null)
                for (Entry<K, V> mapEntry : tableMaps[i].entrySet())
                    arrayList.add(mapEntry);
        }
        return arrayList;
    }
}
