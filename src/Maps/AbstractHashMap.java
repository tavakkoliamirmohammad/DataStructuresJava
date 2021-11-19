package Maps;

import Priority.Entry;

import java.util.ArrayList;
import java.util.Random;

public abstract class AbstractHashMap<K, V> extends AbstractMap<K, V> {
    protected int n = 0;
    protected int capacity;
    private final int prime;
    private final long scale;
    private final long shift;

    public AbstractHashMap(int capacity, int prime) {
        this.capacity = capacity;
        this.prime = prime;
        Random rand = new Random();
        scale = rand.nextInt(prime - 1) + 1;
        shift = rand.nextInt(prime);
        createTable();
    }

    public AbstractHashMap(int capacity) {
        this(capacity, 109345121);
    }

    public AbstractHashMap() {
        this(17);
    }

    @Override
    public int size() {
        return n;
    }

    @Override
    public V get(K key) {
        return bucketGet(hashValue(key), key);
    }

    @Override
    public V put(K key, V value) {
        V answer = bucketPut(hashValue(key), key, value);
        if (n > capacity /2)
            resize(capacity * 2 - 1);
        return answer;
    }

    @Override
    public V remove(K key) {
        return bucketRemove(hashValue(key), key);
    }

    private int hashValue(K key) {
        return (int) ((Math.abs(key.hashCode() * scale + shift) % prime) % capacity);
    }

    private void resize(int capacity){
        ArrayList<Entry<K, V>> buffer = new ArrayList<>();
        for(Entry<K,V> entry : entrySet()){
            buffer.add(entry);
        }
        this.capacity = capacity;
        createTable();
        n = 0;
        for (Entry<K,V> entry : buffer)
            put(entry.getKey(), entry.getValue());
    }

    protected abstract void createTable();

    protected abstract V bucketGet(int hash, K key);

    protected abstract V bucketPut(int hash, K key, V value);

    protected abstract V bucketRemove(int hash, K key);

}
