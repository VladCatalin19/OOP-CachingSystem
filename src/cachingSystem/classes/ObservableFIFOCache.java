package cachingSystem.classes;

import dataStructures.classes.Pair;
import observerPattern.classes.BroadcastListener;

/**
 * Class that adapts the FIFOCache class to the ObservableCache abstract class.
 */
public class ObservableFIFOCache<K, V> extends ObservableCache<K, V> {

    private FIFOCache<K, V> fifo;
    private BroadcastListener<K, V> bcstListener;

    public ObservableFIFOCache() {
        fifo = new FIFOCache<K, V>();
    }

    @Override
    public final V get(final K key) {
        bcstListener = (BroadcastListener<K, V>) getCacheListener();

        // Not found
        if (fifo.get(key) == null) {
            bcstListener.onMiss(key);
            return null;
        }

        // Return value
        bcstListener.onHit(key);
        return fifo.get(key);
    }

    @Override
    public final void put(final K key, final V value) {
        bcstListener = (BroadcastListener<K, V>) getCacheListener();

        fifo.put(key, value);
        bcstListener.onPut(key, value);
        clearStaleEntries();
    }

    @Override
    public final int size() {
        return fifo.size();
    }

    @Override
    public final boolean isEmpty() {
        return fifo.isEmpty();
    }

    @Override
    public final V remove(final K key) {
        return fifo.remove(key);
    }

    @Override
    public final void clearAll() {
        fifo.clearAll();
    }

    @Override
    public final Pair<K, V> getEldestEntry() {
        return fifo.getEldestEntry();
    }
}
