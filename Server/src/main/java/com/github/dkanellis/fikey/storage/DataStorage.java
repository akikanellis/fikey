package com.github.dkanellis.fikey.storage;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.yubico.u2f.data.DeviceRegistration;

import java.util.*;

/**
 * @author Dimitris
 */
public class DataStorage<T, K, V> {

    private LoadingCache<T, Map<K, V>> users;
    private Map<K, V> requests;

    public DataStorage() {
        this.requests = new HashMap<>();
        this.users = CacheBuilder.newBuilder().build(new CacheLoader<T, Map<K, V>>() {
            @Override
            public Map<K, V> load(T key) throws Exception {
                return new HashMap<>();
            }
        });
    }

    public void addRequest(K key, V value) {
        requests.put(key, value);
    }

    public V removeRequest(K key) {
        return requests.remove(key);
    }

    public void addDeviceToUser(T username, K key, V value) {
        users.getUnchecked(username).put(key, value);
    }

    public Iterable<DeviceRegistration> getDevicesFromUser(T username) {
        List<DeviceRegistration> registrations = new ArrayList<>();
        for (V serialized : getSerializedDevices(username)) {
            registrations.add(DeviceRegistration.fromJson((String) serialized));
        }
        return registrations;
    }

    private Collection<V> getSerializedDevices(T username) {
        return users.getUnchecked(username).values();
    }


}
