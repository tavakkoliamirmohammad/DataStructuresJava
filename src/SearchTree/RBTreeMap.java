package SearchTree;

import PositionalLists.Position;
import Priority.Entry;

import java.util.Comparator;

public class RBTreeMap<K, V> extends TreeMap<K, V> {
    public RBTreeMap(Comparator<K> c) {
        super(c);
    }

    private boolean isBlack(Position<Entry<K, V>> position) {
        return tree.getAux(position) == 0;
    }

    private boolean isRed(Position<Entry<K, V>> position) {
        return tree.getAux(position) == 1;
    }

    private void makeBlack(Position<Entry<K, V>> position) {
        tree.setAux(position, 0);
    }

    private void makeRed(Position<Entry<K, V>> position) {
        tree.setAux(position, 1);
    }

    private void setColor(Position<Entry<K, V>> position, boolean toRed) {
        tree.setAux(position, toRed ? 1 : 0);
    }
}
