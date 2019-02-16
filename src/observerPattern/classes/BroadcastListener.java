package observerPattern.classes;

import java.util.ArrayList;

import observerPattern.interfaces.CacheListener;

/**
 * The BroadcastListener broadcasts cache events to other listeners that have
 * been added to it.
 */
public class BroadcastListener<K, V> implements CacheListener<K, V> {

    private ArrayList<CacheListener<K, V>> listeners;

    public BroadcastListener() {
        listeners = new ArrayList<CacheListener<K, V>>();
    }

    /**
     * Add a listener to the broadcast list.
     *
     * @param listener
     *            the listener
     */
    public void addListener(final CacheListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public final void onHit(final K key) {
        for (CacheListener<K, V> listener : listeners) {
            listener.onHit(key);
        }
    }

    @Override
    public final void onMiss(final K key) {
        for (CacheListener<K, V> listener : listeners) {
            listener.onMiss(key);
        }
    }

    @Override
    public final void onPut(final K key, final V value) {
        for (CacheListener<K, V> listener : listeners) {
            listener.onPut(key, value);
        }
    }
}
