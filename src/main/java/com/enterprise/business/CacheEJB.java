package com.enterprise.business;

import java.util.HashMap;
import java.util.Map;
import javax.ejb.AccessTimeout;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

@Singleton // Instantiated once per application
@Lock(LockType.READ) // All methods default to shared read access
public class CacheEJB {

    private final Map<Long, Object> cache = new HashMap<>();

    // Explicitly overrides class default to exclusive access
    @Lock(LockType.WRITE)
    @AccessTimeout(2000) // Rejects requests if lock isn't acquired within 2 seconds
    public void addToCache(Long id, Object object) {
        if (!cache.containsKey(id)) {
            cache.put(id, object);
        }
    }

    // Explicitly overrides class default to exclusive access
    @Lock(LockType.WRITE)
    @AccessTimeout(2000) // Rejects requests if lock isn't acquired within 2 seconds
    public void removeFromCache(Long id) {
        if (cache.containsKey(id)) {
            cache.remove(id);
        }
    }

    // Inherits the class-level shared READ lock automatically
    public Object getFromCache(Long id) {
        if (cache.containsKey(id)) {
            return cache.get(id);
        } else {
            return null;
        }
    }
}
