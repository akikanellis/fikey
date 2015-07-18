package com.github.dkanellis.fikey.storage;

import com.github.dkanellis.fikey.exceptions.DeviceAlreadyRegisteredWithUserException;
import com.github.dkanellis.fikey.exceptions.UserAlreadyExistsException;
import com.github.dkanellis.fikey.exceptions.UserDoesNotExistException;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.yubico.u2f.data.DeviceRegistration;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Dimitris Kanellis
 */
public class Users implements U2fUserStorage {

    private static Users INSTANCE = new Users();

    private Set<U2fUser> users;

    private LoadingCache<U2fUser, Map<DeviceRegistration, String>> userDevices;

    private Users() {
    }

    public static Users getInstance() {
        return INSTANCE;
    }

    public void init() {
        this.users = new HashSet<>();
        this.users.add(new User("akis", "")); // TODO remove
        this.userDevices = CacheBuilder.newBuilder().build(new CacheLoader<U2fUser, Map<DeviceRegistration, String>>() {
            @Override
            public Map<DeviceRegistration, String> load(U2fUser user) throws Exception {
                return new HashMap<>();
            }
        });
    }

    @Override
    public Iterable<DeviceRegistration> getDevicesFromUser(U2fUser user) {
        return userDevices.getUnchecked(user).keySet();
    }

    @Override
    public U2fUser getFromUsername(String username) throws UserDoesNotExistException {
        for (U2fUser user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }

        throw new UserDoesNotExistException(username);
    }

    @Override
    public boolean hasUser(U2fUser user) {
        return users.contains(user);
    }

    @Override
    public void addNewUser(U2fUser user) throws UserAlreadyExistsException {
        if (hasUser(user)) {
            throw new UserAlreadyExistsException(user.getUsername());
        }

        users.add(user);
        userDevices.put(user, new HashMap<>());
    }

    @Override
    public void addNewDeviceToUser(U2fUser user, DeviceRegistration device) throws DeviceAlreadyRegisteredWithUserException {
        Map<DeviceRegistration, String> devicesFromUser = userDevices.getUnchecked(user);

        devicesFromUser.put(device, device.toJson());
    }
}
