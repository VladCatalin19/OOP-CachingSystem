package cachingSystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import cachingSystem.classes.LRUCache;
import cachingSystem.classes.ObservableCache;
import cachingSystem.classes.ObservableFIFOCache;
import cachingSystem.classes.TimeAwareCache;
import cachingSystem.interfaces.CacheStalePolicy;
import dataStructures.classes.Pair;
import observerPattern.classes.BroadcastListener;
import observerPattern.interfaces.CacheListener;

public final class FileCache {

    /**
     * Types of cache that have a specific maximum capacity.
     */
    public enum Strategy {
        FIFO, LRU,
    }

    /**
     * Creates cache with a certain capacity. Its type is defined by strategy.
     *
     * @param strategy
     *            the type
     * @param capacity
     *            the maximum capacity
     * @return the created cache object
     */
    public static cachingSystem.FileCache createCacheWithCapacity(
            final cachingSystem.FileCache.Strategy strategy,
            final int capacity) {
        ObservableCache<String, String> dataCache;

        switch (strategy) {

        case FIFO:
            dataCache = new ObservableFIFOCache<>();
            break;
        case LRU:
            dataCache = new LRUCache<>();
            break;
        default:
            throw new IllegalArgumentException(
                    "Unsupported cache strategy: " + strategy);
        }

        dataCache.setStalePolicy(new CacheStalePolicy<String, String>() {
            @Override
            public boolean shouldRemoveEldestEntry(
                    final Pair<String, String> entry) {
                return dataCache.size() > capacity;
            }
        });

        return new cachingSystem.FileCache(dataCache);
    }

    /**
     * Creates cache that removes elements based on inactive time.
     *
     * @param millisToExpire
     *            the time the entry will be deleted if inactive
     * @return the created cache object
     */
    public static cachingSystem.FileCache createCacheWithExpiration(
            final long millisToExpire) {
        TimeAwareCache<String, String> dataCache = new TimeAwareCache<>();

        dataCache.setExpirePolicy(millisToExpire);

        return new cachingSystem.FileCache(dataCache);
    }

    /**
     * Constructs a new FileCache.
     *
     * @param dataCache
     */
    private FileCache(final ObservableCache<String, String> dataCache) {
        this.dataCache = dataCache;
        this.broadcastListener = new BroadcastListener<>();

        this.dataCache.setCacheListener(broadcastListener);

        broadcastListener.addListener(createCacheListener());
    }

    /**
     * Creates a listener that loads a file in memory on a cache miss.
     *
     * @return the created cache listener
     */
    private CacheListener<String, String> createCacheListener() {
        return new CacheListener<String, String>() {

            @Override
            public void onHit(final String key) {
            }

            @Override
            public void onMiss(final String key) {

                try {
                    File input = new File(key);
                    BufferedReader reader = new BufferedReader(
                            new FileReader(input));
                    String value = reader.readLine();
                    dataCache.put(key, value);
                    reader.close();

                } catch (FileNotFoundException e) {
                    System.out.println("Error! File not found!");
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onPut(final String key, final String value) {
            }
        };
    }

    /**
     * Gets content from file located at path from cache.
     *
     * @param path
     *            the path to the file
     * @return content of file
     */
    public String getFileContents(final String path) {
        String fileContents;

        do {
            fileContents = dataCache.get(path);
        } while (fileContents == null);
        return fileContents;
    }

    /**
     * Puts content from file located at path in cache.
     *
     * @param path
     *            path to file
     * @param contents
     */
    public void putFileContents(final String path, final String contents) {
        dataCache.put(path, contents);
    }

    /**
     * Adds listener to the table.
     *
     * @param listener
     *            listener to be added
     */
    public void addListener(final CacheListener<String, String> listener) {
        broadcastListener.addListener(listener);
    }

    private ObservableCache<String, String> dataCache;
    private BroadcastListener<String, String> broadcastListener;
}
