package twitter_bootcamp.services;

import java.util.HashMap;
import java.util.List;


public class Cache<T> {

    private HashMap<String, List<T>> cache;

    public void InitializeCache() {
        cache = new HashMap<>();
    }

    public void clearCache() {
        cache.clear();
    }

    public List<T> getCache(String key) {
        try {
            return cache.get(key);
        } catch (Exception e) {
            return null;
        }
    }

    public void setCache(String key, List<T> cache) {
        this.cache.put(key, cache);
    }
}