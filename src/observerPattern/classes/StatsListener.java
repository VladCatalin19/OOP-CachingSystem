package observerPattern.classes;

import observerPattern.interfaces.CacheListener;

/**
 * The StatsListener collects hit / miss / update stats for a cache.
 *
 * @param <K>
 *            the key type
 * @param <V>
 *            the value type
 */
public class StatsListener<K, V> implements CacheListener<K, V> {

    private static int HITS_NO;
    private static int MISS_NO;
    private static int PUTS_NO;

    public StatsListener() {
        HITS_NO = 0;
        MISS_NO = 0;
        PUTS_NO = 0;
    }

    /**
     * Get the number of hits for the cache.
     *
     * @return number of hits
     */
    public int getHits() {
        return HITS_NO;
    }

    /**
     * Get the number of misses for the cache.
     *
     * @return number of misses
     */
    public int getMisses() {
        return MISS_NO;
    }

    /**
     * Get the number of updates (put operations) for the cache.
     *
     * @return number of updates
     */
    public int getUpdates() {
        return PUTS_NO;
    }

    @Override
    public final void onHit(final K key) {
        ++HITS_NO;
    }

    @Override
    public final void onMiss(final K key) {
        ++MISS_NO;
    }

    @Override
    public final void onPut(final K key, final V value) {
        ++PUTS_NO;
    }
}
