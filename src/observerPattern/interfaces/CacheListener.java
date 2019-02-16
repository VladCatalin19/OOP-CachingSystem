package observerPattern.interfaces;

/**
 * The CacheListener interface defines functionality for processing events
 * reported by a cache.
 *
 * @param <K>
 *            the key type
 * @param <V>
 *            the value type
 */
public interface CacheListener<K, V> {
    void onHit(K key);

    void onMiss(K key);

    void onPut(K key, V value);
}
