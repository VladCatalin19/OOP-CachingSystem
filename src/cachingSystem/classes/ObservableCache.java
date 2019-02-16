package cachingSystem.classes;

import cachingSystem.interfaces.Cache;
import cachingSystem.interfaces.CacheStalePolicy;
import dataStructures.classes.Pair;
import observerPattern.interfaces.CacheListener;

/**
 * Abstract class that adds support for listeners and stale element policies to
 * the Cache interface.
 *
 * @param <K>
 *            the key type
 * @param <V>
 *            the value type
 */
public abstract class ObservableCache<K, V> implements Cache<K, V> {

    private CacheStalePolicy<K, V> stalePolicy;
    private CacheListener<K, V> cacheListener;

    /**
     * Set a policy for removing stale elements from the cache.
     *
     * @param stalePolicy
     *            the policy
     */
    public void setStalePolicy(final CacheStalePolicy<K, V> stalePolicy) {
        this.stalePolicy = stalePolicy;
    }

    /**
     * Get the cache stale policy.
     *
     * @return the policy
     */
    public CacheStalePolicy<K, V> getStalePolicy() {
        return stalePolicy;
    }

    /**
     * Set a listener for the cache.
     *
     * @param cacheListener
     *            the listener
     */
    public void setCacheListener(final CacheListener<K, V> cacheListener) {
        this.cacheListener = cacheListener;
    }

    /**
     * Get the listener for the cache.
     *
     * @return the listener
     */
    public CacheListener<K, V> getCacheListener() {
        return cacheListener;
    }

    /**
     * Clear the stale elements from the cache. This method must make use of the
     * stale policy.
     *
     */
    public void clearStaleEntries() {
        Pair<K, V> entry = getEldestEntry();
        if (stalePolicy.shouldRemoveEldestEntry(entry)) {
            remove(entry.getKey());
        }
    }
}
