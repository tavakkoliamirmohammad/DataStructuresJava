package Priority;

public interface AdaptablePriorityQueue<K, V> {
    Entry<K, V> remove(Entry<K, V> entry);

    Entry<K, V> replaceKey(Entry<K, V> entry, K Key);

    Entry<K, V> replaceValue(Entry<K, V> entry, V value);

}
