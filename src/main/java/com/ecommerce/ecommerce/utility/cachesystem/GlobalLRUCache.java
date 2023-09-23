package com.ecommerce.ecommerce.utility.cachesystem;

import java.util.*;

public class GlobalLRUCache<K,V> {

    private final int capacity = 5;
    private final Map<K, Map<String, Object>> cache;
    private final LinkedList<K> lruQueue;
    private static  GlobalLRUCache<String, Map<String, Object>> instance;

    private GlobalLRUCache() {
        this.cache = new LinkedHashMap<>(capacity, 0.75f, true);
        this.lruQueue = new LinkedList<>();
    }

    public static synchronized  GlobalLRUCache<String, Map<String, Object>> getInstance() {
        if (instance == null) {
            instance = new GlobalLRUCache<>();
        }
        return instance;
    }
    public Map<String, Object> getAll(K key) {
        synchronized (cache) {
            if (cache.containsKey(key)) {
                // Move the accessed key to the end of the LRU queue
                lruQueue.remove(key);
                lruQueue.addLast(key);
                return cache.get(key);
            }
            return null;
        }
    }

    public Object get(K key, String studentId) {
        synchronized (cache) {
            if (cache.containsKey(key)) {
                Map<String, Object> studentMap = cache.get(key);
                // Move the key to the end of the LRU queue
                lruQueue.remove(key);
                lruQueue.addLast(key);
                return studentMap.get(studentId);
            }
            return null;
        }
    }

    public void put(K key, String objectId, Object object) {
        synchronized (cache) {
            if (!cache.containsKey(key)) {
                cache.put(key, new LinkedHashMap<>(capacity, 0.75f, true));
            }

            Map<String, Object> objectMap = cache.get(key);
            objectMap.put(objectId, object);

            // Move the key to the end of the LRU queue
            lruQueue.remove(key);
            lruQueue.addLast(key);

            // Evict the least recently used cache if the size exceeds capacity
            if (cache.size() > capacity) {
                K lruKey = lruQueue.poll();
                if (lruKey != null) {
                    cache.remove(lruKey);
                }
            }
        }
    }
}
