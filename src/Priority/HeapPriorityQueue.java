package Priority;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HeapPriorityQueue<K, V> extends AbstractPriorityQueue<K, V> {

    protected List<Entry<K, V>> heap = new ArrayList<>();

    public HeapPriorityQueue(Comparator<K> comparator) {
        super(comparator);
    }

    public HeapPriorityQueue(K[] keys, V[] values, Comparator<K> comparator){
        super(comparator);
        for(int j = 0; j < Math.min(keys.length, values.length); ++j){
            heap.add(new PQEntry<>(keys[j], values[j]));
        }
        heapify();
    }

    protected int left(int i) {
        return 2 * i + 1;
    }

    protected int right(int i) {
        return 2 * i + 2;
    }

    protected int parent(int i) {
        return (i - 1) / 2;
    }

    protected boolean hasLeft(int i) {
        return left(i) < heap.size();
    }

    protected boolean hasRight(int i) {
        return right(i) < heap.size();
    }

    protected void swap(int i, int j) {
        Entry<K, V> entry = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, entry);
    }

    protected void upHeap(int i) {
        while (i > 0) {
            int p = parent(i);
            if (compare(heap.get(p), heap.get(i)) <= 0) break;
            swap(p, i);
            i = p;
        }
    }

    protected void downHeap(int i) {
        while (hasLeft(i)) {
            Entry<K, V> minEntry = heap.get(left(i));
            int minIndex = left(i);
            if (hasRight(i)) {
                if (compare(minEntry, heap.get(right(i))) > 0) {
                    minEntry = heap.get(right(i));
                    minIndex = right(i);
                }
            }
            if (compare(heap.get(i), minEntry) < 0) break;
            swap(i, minIndex);
            i = minIndex;
        }
    }

    @Override
    public int size() {
        return heap.size();
    }

    @Override
    public Entry<K, V> insert(K key, V value) throws IllegalArgumentException {
        checkKey(key);
        Entry<K, V> entry = new PQEntry<>(key, value);
        heap.add(entry);
        upHeap(heap.size() - 1);
        return entry;
    }

    @Override
    public Entry<K, V> min() {
        if (isEmpty()) return null;
        return heap.get(0);
    }

    @Override
    public Entry<K, V> removeMin() {
        if (isEmpty()) return null;
        swap(0, heap.size() - 1);
        Entry<K, V> temp = heap.remove(heap.size() - 1);
        downHeap(0);
        return temp;
    }

    protected void heapify() {
        int parent = parent(heap.size() - 1);
        for (int i = parent; i >= 0; i--) {
            downHeap(i);
        }
    }
}
