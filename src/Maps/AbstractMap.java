package Maps;

import Priority.Entry;

import java.util.Iterator;

public abstract class AbstractMap<K, V> implements Map<K, V> {
    protected static class MapEntry<K, V> implements Entry<K, V> {

        private K key;
        private V value;

        public MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        protected void setKey(K key) {
            this.key = key;
        }

        protected V setValue(V value) {
            V old = this.value;
            this.value = value;
            return old;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }
    }

    private class KeyIterator implements Iterator<K> {
        private Iterator<Entry<K, V>> entryIterator = entrySet().iterator();

        @Override
        public boolean hasNext() {
            return entryIterator.hasNext();
        }

        @Override
        public K next() {
            return entryIterator.next().getKey();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class KeyIterable implements Iterable<K> {

        @Override
        public Iterator<K> iterator() {
            return new KeyIterator();
        }
    }

    private class ValueIterator implements Iterator<V> {
        private Iterator<Entry<K, V>> entryIterator = entrySet().iterator();

        @Override
        public boolean hasNext() {
            return entryIterator.hasNext();
        }

        @Override
        public V next() {
            return entryIterator.next().getValue();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class ValueIterable implements Iterable<V> {

        @Override
        public Iterator<V> iterator() {
            return new ValueIterator();
        }
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public Iterable<K> keySet() {
        return new KeyIterable();
    }

    @Override
    public Iterable<V> valueSet() {
        return new ValueIterable();
    }
}
