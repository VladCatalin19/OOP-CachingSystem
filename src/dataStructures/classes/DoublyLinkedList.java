package dataStructures.classes;

/**
 * The class defines a doubly linked list used in LRUCache system.
 *
 * @param <K>
 *            the key type
 * @param <V>
 *            the value type
 */
public class DoublyLinkedList<K, V> {

    private Node<K, V> head;
    private Node<K, V> tail;
    private int size;

    /**
     * Constructor that creates empty list.
     */
    public DoublyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Gets the size of the list.
     *
     * @return the size of the list.
     */
    public int size() {
        return size;
    }

    /**
     * Checks if the list is empty.
     *
     * @return returns true is list is empty
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Adds node to list.
     *
     * @param node
     *            node to be added
     */
    public void add(final Node<K, V> node) {

        // Nothing to do
        if (node == null) {
            return;
        }

        // Reset position
        node.setNext(null);
        node.setPrev(null);

        // If list is empty
        if (head == null) {
            head = node;
            tail = node;
            ++size;
            return;
        }

        // If list is not empty
        head.setPrev(node);
        node.setNext(head);
        head = node;
        ++size;
    }

    /**
     * Remove node from list.
     *
     * @param node
     *            node to be removed
     */
    public void remove(final Node<K, V> node) {

        // If list is empty
        if (head == null) {
            size = 0;
            return;
        }

        // If node is null
        if (node == null) {
            return;
        }

        // If list has only one element
        if (head == tail && head == node) {
            head = null;
            tail = null;
            size = 0;
            return;
        }

        // If node to be removed is at the beginning
        if (node == head) {
            head.getNext().setPrev(null);
            head = head.getNext();
            --size;
            return;
        }

        // If node to be removed is at the end
        if (node == tail) {
            tail.getPrev().setNext(null);
            tail = tail.getPrev();
            --size;
            return;
        }

        // If node is inside the list
        node.getPrev().setNext(node.getNext());
        node.getNext().setPrev(node.getPrev());
        --size;
    }

    /**
     * Moves the node to the beginning of the list.
     *
     * @param node
     *            node to be moved
     */
    public void moveFirst(final Node<K, V> node) {
        remove(node);
        add(node);
    }

    /**
     * Removes last node.
     */
    public void removeLast() {
        remove(tail);
    }

    /**
     * Removes all nodes.
     */
    public void removeAll() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Gets the head of the list.
     *
     * @return the head of the list
     */
    public Node<K, V> getHead() {
        return head;
    }

    /**
     * Gets the tail of the list.
     *
     * @return the tail of the list
     */
    public Node<K, V> getTail() {
        return tail;
    }

    /**
     * Gets the size of the list.
     *
     * @return the size of the list
     */
    public int getSize() {
        return size;
    }
}
