package com.github.dkanellis.fikey.storage;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.yubico.u2f.data.DeviceRegistration;

import java.util.*;

/**
 * @author Dimitris
 */
public class DataStorage {

    private static DataStorage INSTANCE = new DataStorage();

    private LoadingCache<String, Map<String, String>> users;
    private Map<String, String> requests;

    private DataStorage() {
    }

    public static DataStorage getInstance() {
        return INSTANCE;
    }

    public void init() {
        this.requests = new HashMap<>();
        this.users = CacheBuilder.newBuilder().build(new CacheLoader<String, Map<String, String>>() {
            @Override
            public Map<String, String> load(String key) throws Exception {
                return new HashMap<>();
            }
        });
    }

    public void addRequest(String key, String value) {
        requests.put(key, value);
    }

    public String removeRequest(String key) {
        return requests.remove(key);
    }

    public void addDeviceToUser(String username, String key, String value) {
        users.getUnchecked(username).put(key, value);
    }

    public Iterable<DeviceRegistration> getDevicesFromUser(String username) {
        List<DeviceRegistration> registrations = new ArrayList<>();
        for (String serialized : getSerializedDevices(username)) {
            registrations.add(DeviceRegistration.fromJson((String) serialized));
        }
        return registrations;
    }

    private Collection<String> getSerializedDevices(String username) {
        return users.getUnchecked(username).values();
    }


}
