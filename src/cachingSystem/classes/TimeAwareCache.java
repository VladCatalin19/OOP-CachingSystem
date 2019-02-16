package cachingSystem.classes;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Map.Entry;

import dataStructures.classes.Node;

/**
 * The TimeAwareCache offers the same functionality as the LRUCache, but also
 * stores a timestamp for each element. The timestamp is updated after each get
 * / put operation for a key. This functionality allows for time based cache
 * stale policies (e.g. removing entries that are older than 1 second).
 */
public class TimeAwareCache<K, V> extends LRUCache<K, V> {
    private long millisToExpire;

    public TimeAwareCache() {
        super();
    }

    /**
     * Get the timestamp associated with a key, or null if the key is not stored
     * in the cache.
     *
     * @param key
     *            the key
     * @return the timestamp, or null
     */
    public Timestamp getTimestampOfKey(final K key) {
        return (data.get(key) == null) ? null : data.get(key).getTimeStamp();
    }

    /**
     * Set a cache stale policy that should remove all elements older
     * than @millisToExpire milliseconds. This is a convenience method for
     * setting a time based policy for the cache.
     *
     * @param millisToExpire
     *            the expiration time, in milliseconds
     */
    public void setExpirePolicy(final long millisToExpire) {
        this.millisToExpire = millisToExpire;
    }

    @Override
    /**
     * Checks if the difference between the current time and the time in the
     * node is greater than @millisToExpire and deletes the node if true.
     */
    protected final void checkAndRemoveEldestEntry() {
        long currentTime = System.currentTimeMillis();
        Iterator<Entry<K, Node<K, V>>> it = data.entrySet().iterator();

        while (it.hasNext()) {
            Entry<K, Node<K, V>> entry = (Entry<K, Node<K, V>>) it.next();
            if (currentTime - entry.getValue().getTimeStamp()
                    .getTime() >= millisToExpire) {
                it.remove();
                doublyList.remove(entry.getValue());
            }
        }
    }
}
