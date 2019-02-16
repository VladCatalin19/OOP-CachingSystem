package observerPattern.classes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import observerPattern.interfaces.CacheListener;

/**
 * The KeyStatsListener collects key-level stats for cache operations.
 *
 * @param <K>
 *            the key type
 * @param <V>
 *            the value type
 */
public class KeyStatsListener<K, V> implements CacheListener<K, V> {

    private SortedSet<Map.Entry<K, Integer>> sortedSet;
    private SortedMap<K, Integer> freqHitMap;
    private SortedMap<K, Integer> freqMissMap;
    private SortedMap<K, Integer> freqPutMap;

    /**
     * Creates the set that sorts the keys in descending order based on their
     * values.
     */
    public KeyStatsListener() {
        sortedSet = new TreeSet<Map.Entry<K, Integer>>(
                new Comparator<Map.Entry<K, Integer>>() {

                    @Override
                    public int compare(final Map.Entry<K, Integer> e1,
                            final Map.Entry<K, Integer> e2) {

                        if (e1.getValue().compareTo(e2.getValue()) != 0) {
                            return -1 * e1.getValue().compareTo(e2.getValue());
                        }

                        return 1;
                    }
                });

        freqHitMap = new TreeMap<K, Integer>();
        freqMissMap = new TreeMap<K, Integer>();
        freqPutMap = new TreeMap<K, Integer>();
    }

    /**
     * Get the number of hits for a key.
     *
     * @param key
     *            the key
     * @return number of hits
     */
    public int getKeyHits(final K key) {
        return freqHitMap.get(key);
    }

    /**
     * Get the number of misses for a key.
     *
     * @param key
     *            the key
     * @return number of misses
     */
    public int getKeyMisses(final K key) {
        return freqMissMap.get(key);
    }

    /**
     * Get the number of updates for a key.
     *
     * @param key
     *            the key
     * @return number of updates
     */
    public int getKeyUpdates(final K key) {
        return freqPutMap.get(key);
    }

    /**
     * Get the @top entries from a sorted set.
     *
     * @param top
     *            number of top keys
     * @param map
     *            map to get entries from
     * @return the list of keys
     */
    public List<K> makeListFromMap(final int top,
            final SortedMap<K, Integer> map) {

        int count = 0;
        List<K> list = new ArrayList<K>();

        sortedSet.clear();
        sortedSet.addAll(map.entrySet());
        for (Entry<K, Integer> entry : sortedSet) {
            if (count >= top) {
                break;
            }

            list.add(entry.getKey());
            ++count;
        }

        return list;
    }

    /**
     * Get the @top most hit keys.
     *
     * @param top
     *            number of top keys
     * @return the list of keys
     */
    public List<K> getTopHitKeys(final int top) {
        return makeListFromMap(top, freqHitMap);
    }

    /**
     * Get the @top most missed keys.
     *
     * @param top
     *            number of top keys
     * @return the list of keys
     */
    public List<K> getTopMissedKeys(final int top) {
        return makeListFromMap(top, freqMissMap);
    }

    /**
     * Get the @top most updated keys.
     *
     * @param top
     *            number of top keys
     * @return the list of keys
     */
    public List<K> getTopUpdatedKeys(final int top) {
        return makeListFromMap(top, freqPutMap);
    }

    @Override
    public final void onHit(final K key) {
        // Put key in map if does not exist
        if (!freqHitMap.containsKey(key)) {
            freqHitMap.put(key, 1);
            return;
        }

        // Add 1 to times used in map
        freqHitMap.put(key, freqHitMap.get(key) + 1);
    }

    @Override
    public final void onMiss(final K key) {
        // Put key in map if does not exist
        if (!freqMissMap.containsKey(key)) {
            freqMissMap.put(key, 1);
            return;
        }

        // Add 1 to times used in map
        freqMissMap.put(key, freqMissMap.get(key) + 1);
    }

    @Override
    public final void onPut(final K key, final V value) {
        // Put key in map if does not exist
        if (!freqPutMap.containsKey(key)) {
            freqPutMap.put(key, 1);
            return;
        }

        // Add 1 to times used in map
        freqPutMap.put(key, freqPutMap.get(key) + 1);
    }
}
