package cachingSystem.classes;

import java.util.HashMap;
import java.util.Map;

import dataStructures.classes.DoublyLinkedList;
import dataStructures.classes.Node;
import dataStructures.classes.Pair;
import observerPattern.classes.BroadcastListener;

/**
 * This cache is very similar to the FIFOCache, but guarantees O(1) complexity
 * for the get, put and remove operations.
 */
public class LRUCache<K, V> extends ObservableCache<K, V> {

    protected Map<K, Node<K, V>> data;
    protected DoublyLinkedList<K, V> doublyList;
    private BroadcastListener<K, V> bcstListener;

    public LRUCache() {
        data = new HashMap<K, Node<K, V>>();
        doublyList = new DoublyLinkedList<K, V>();
    }

    /**
     * Checks if the eldest entry should be removed and removes it if needed.
     */
    protected void checkAndRemoveEldestEntry() {
        if (getStalePolicy().shouldRemoveEldestEntry(getEldestEntry())) {
            data.remove(doublyList.getTail().getKey());
            doublyList.removeLast();
            clearStaleEntries();
        }
    }

    @Override
    public final V get(final K key) {
        bcstListener = (BroadcastListener<K, V>) getCacheListener();
        checkAndRemoveEldestEntry();

        if (data.containsKey(key)) {
            // Move to first place
            Node<K, V> node = data.get(key);
            doublyList.moveFirst(node);
            node.updateTimeStamp();

            // Return value
            bcstListener.onHit(key);
            return node.getValue();
        }

        // Not found
        bcstListener.onMiss(key);
        return null;
    }

    @Override
    public final void put(final K key, final V value) {
        bcstListener = (BroadcastListener<K, V>) getCacheListener();

        // Existing slot
        if (data.containsKey(key)) {
            Node<K, V> node = data.get(key);
            doublyList.moveFirst(node);
            node.setValue(value);
            node.updateTimeStamp();

            bcstListener.onPut(key, value);
            return;
        }

        // New slot
        Node<K, V> node = new Node<K, V>(key, value, null, null);
        doublyList.add(node);
        data.put(key, node);
        bcstListener.onPut(key, value);

        // Clean oldest slot if needed
        checkAndRemoveEldestEntry();
    }

    @Override
    public final int size() {
        return doublyList.size();
    }

    @Override
    public final boolean isEmpty() {
        return doublyList.isEmpty();
    }

    @Override
    public final V remove(final K key) {
        doublyList.removeLast();
        return data.remove(key).getValue();
    }

    @Override
    public final void clearAll() {
        data.clear();
        doublyList.removeAll();
    }

    @Override
    public final Pair<K, V> getEldestEntry() {
        if (doublyList == null || doublyList.getTail() == null) {
            return null;
        }
        return new Pair<K, V>(doublyList.getTail().getKey(),
                doublyList.getTail().getValue());
    }
}
