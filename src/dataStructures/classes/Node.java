package dataStructures.classes;

import java.sql.Timestamp;

/**
 * Class that implements the nodes used in the doubly linked list.
 *
 * @param <K>
 *            the key type
 * @param <V>
 *            the value type
 */
public class Node<K, V> {
    private K key;
    private V value;
    private Timestamp timeStamp;
    private Node<K, V> prev;
    private Node<K, V> next;

    public Node(final K key, final V value, final Node<K, V> prev,
            final Node<K, V> next) {
        this.key = key;
        this.value = value;
        timeStamp = new Timestamp(System.currentTimeMillis());
        this.prev = prev;
        this.next = next;
    }

    /**
     * Gets the key from the node.
     *
     * @return the key
     */
    public K getKey() {
        return key;
    }

    /**
     * Sets the key from the node.
     *
     * @param key
     *            the key to be set
     */
    public void setKey(final K key) {
        this.key = key;
    }

    /**
     * Gets the value from the node.
     *
     * @return the value
     */
    public V getValue() {
        return value;
    }

    /**
     * Sets the value from the node.
     *
     * @param value
     *            value to be set
     */
    public void setValue(final V value) {
        this.value = value;
    }

    /**
     * Gets the time the node was added or updated.
     *
     * @return the time in milliseconds
     */
    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    /**
     * Sets the time the node was added or updated.
     */
    public void updateTimeStamp() {
        timeStamp.setTime(System.currentTimeMillis());
    }

    /**
     * Gets the previous node in the list.
     *
     * @return the node
     */
    public Node<K, V> getPrev() {
        return prev;
    }

    /**
     * Sets the previous node to this node.
     *
     * @param prev
     *            node to be set
     */
    public void setPrev(final Node<K, V> prev) {
        this.prev = prev;
    }

    /**
     * Gets the next node from the list.
     *
     * @return the node
     */
    public Node<K, V> getNext() {
        return next;
    }

    /**
     * Sets the next node to this node.
     *
     * @param next
     *            node to be set
     */
    public void setNext(final Node<K, V> next) {
        this.next = next;
    }
}
