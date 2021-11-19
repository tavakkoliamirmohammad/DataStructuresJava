package Priority;

import java.util.Comparator;

public class AdaptableHeapPriorityQueue<K, V> extends HeapPriorityQueue<K, V> implements AdaptablePriorityQueue<K, V> {

    public static class AdaptablePQEntry<K, V> extends PQEntry<K, V> {
        private int index;

        public AdaptablePQEntry(K key, V value, int index) {
            super(key, value);
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }

    public AdaptableHeapPriorityQueue(Comparator<K> comparator) {
        super(comparator);
    }

    protected AdaptablePQEntry<K, V> validate(Entry<K, V> entry) throws IllegalArgumentException {
        if (!(entry instanceof AdaptablePQEntry)) throw new IllegalArgumentException("Invalid entry");
        AdaptablePQEntry<K, V> adaptablePQEntryEntry = (AdaptablePQEntry<K, V>) entry;
        int index = adaptablePQEntryEntry.getIndex();
        if (index < 0 || index >= size() || heap.get(index) != adaptablePQEntryEntry)
            throw new IllegalArgumentException("Invalid Entry");
        return adaptablePQEntryEntry;
    }

    @Override
    public Entry<K, V> insert(K key, V value) throws IllegalArgumentException {
        checkKey(key);
        AdaptablePQEntry<K, V> adaptablePQEntry = new AdaptablePQEntry<>(key, value, heap.size());
        heap.add(adaptablePQEntry);
        upHeap(heap.size() - 1);
        return adaptablePQEntry;
    }

    protected void bubble(int j) {
        if (j >= 0 && compare(heap.get(j), heap.get(parent(j))) < 0)
            upHeap(j);
        else
            downHeap(j);
    }

    @Override
    protected void swap(int i, int j) {
        super.swap(i, j);
        ((AdaptablePQEntry<K, V>) heap.get(i)).setIndex(i);
        ((AdaptablePQEntry<K, V>) heap.get(j)).setIndex(j);
    }

    @Override
    public Entry<K, V> remove(Entry<K, V> entry) throws IllegalArgumentException {
        AdaptablePQEntry<K, V> adaptablePQEntry = validate(entry);
        int index = adaptablePQEntry.getIndex();
        if (index == heap.size() - 1) {
            heap.remove(index);
        } else {
            swap(index, heap.size() - 1);
            heap.remove(heap.size() - 1);
            bubble(index);
        }
        return adaptablePQEntry;
    }

    @Override
    public Entry<K, V> replaceKey(Entry<K, V> entry, K key) throws IllegalArgumentException {
        AdaptablePQEntry<K, V> adaptablePQEntry = validate(entry);
        checkKey(key);
        adaptablePQEntry.setKey(key);
        bubble(adaptablePQEntry.getIndex());
        return adaptablePQEntry;
    }

    @Override
    public Entry<K, V> replaceValue(Entry<K, V> entry, V value) throws IllegalArgumentException {
        AdaptablePQEntry<K, V> adaptablePQEntry = validate(entry);
        adaptablePQEntry.setValue(value);
        return adaptablePQEntry;
    }
}
