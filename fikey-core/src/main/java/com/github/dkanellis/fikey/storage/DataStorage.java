package com.github.dkanellis.fikey.storage;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.yubico.u2f.data.DeviceRegistration;

import java.util.*;

/**
 * @author Dimitris Kanellis
 */
public class DataStorage implements UserStorage {

    private static DataStorage INSTANCE = new DataStorage();

    private List<FidoUser> users;
    private LoadingCache<String, Map<String, String>> usersOld;
    private Requests requests;

    private DataStorage() {
    }

    public static DataStorage getInstance() {
        return INSTANCE;
    }

    public void init() {
        this.users = new ArrayList<>();
        this.usersOld = CacheBuilder.newBuilder().build(new CacheLoader<String, Map<String, String>>() {
            @Override
            public Map<String, String> load(String key) throws Exception {
                return new HashMap<>();
            }
        });
    }

    public void addDeviceToUser(String username, String key, String value) {
        usersOld.getUnchecked(username).put(key, value);
    }

    public Iterable<DeviceRegistration> getDevicesFromUser(String username) {
        List<DeviceRegistration> registrations = new ArrayList<>();
        for (String serialized : getSerializedDevices(username)) {
            registrations.add(DeviceRegistration.fromJson((String) serialized));
        }
        return registrations;
    }

    private Collection<String> getSerializedDevices(String username) {
        return usersOld.getUnchecked(username).values();
    }


    @Override
    public boolean hasUser(FidoUser user) {
        return users.contains(user);
    }

    @Override
    public void addUser(String username, String password) {
        FidoUser user = new WebsiteUser(username, password);
        users.add(user);
    }

    @Override
    public void addDeviceToUser(String username, DeviceRegistration device) {

    }
}
