package Priority;

import PositionalLists.PositionalList;


import java.util.Comparator;

public abstract class AbstractPriorityQueue<K, V> implements PriorityQueue<K, V> {

    protected static class PQEntry<K, V> implements Entry<K, V> {
        private K key;
        private V value;

        public PQEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        protected void setKey(K key) {
            this.key = key;
        }

        protected void setValue(V value) {
            this.value = value;
        }
    }

    private Comparator<K> comparator;

    public AbstractPriorityQueue(Comparator<K> comparator) {
        this.comparator = comparator;
    }

    protected int compare(Entry<K, V> e1, Entry<K, V> e2) {
        return comparator.compare(e1.getKey(), e2.getKey());
    }

    protected boolean checkKey(K key) throws IllegalArgumentException {
        try {
            return comparator.compare(key, key) == 0;
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Incompatible key");
        }
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    public static <E> void pqSort(PositionalList<E> positionalList, PriorityQueue<E, ?> priorityQueue) {
        for (int i = 0; i < positionalList.size(); ++i) {
            E position = positionalList.remove(positionalList.first());
            priorityQueue.insert(position, null);
        }
        for (int i = 0; i < priorityQueue.size(); ++i) {
            E minValue = priorityQueue.removeMin().getKey();
            positionalList.addLast(minValue);
        }
    }
}
