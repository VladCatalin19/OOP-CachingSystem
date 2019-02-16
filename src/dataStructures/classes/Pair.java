package dataStructures.classes;

/**
 * The Pair class is a convenient way of storing key-value pairs.
 *
 * @param <K>
 *            the key type
 * @param <V>
 *            the value type
 */
public class Pair<K, V> {
    private K key;
    private V value;

    public Pair(final K key, final V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Gets the key from the pair.
     *
     * @return the key
     */
    public K getKey() {
        return key;
    }

    /**
     * Sets the key from the pair.
     *
     * @param key
     *            the key to be set
     */
    public void setKey(final K key) {
        this.key = key;
    }

    /**
     * Gets the value from the pair.
     *
     * @return the value
     */
    public V getValue() {
        return value;
    }

    /**
     * Sets the value from the pair.
     *
     * @param value
     *            the value
     */
    public void setValue(final V value) {
        this.value = value;
    }
}
