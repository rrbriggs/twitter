package twitter_bootcamp.services;

import java.util.List;


public class ListCache<T> {
    private List<T> cache;

    public List<T> getCache() {
        return cache;
    }

    public void setCache(List<T> cache) {
        this.cache = cache;
    }
}