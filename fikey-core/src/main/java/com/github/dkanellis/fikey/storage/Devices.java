package com.github.dkanellis.fikey.storage;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.yubico.u2f.data.DeviceRegistration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Dimitris
 */
public class Devices implements U2fDeviceStorage {

    private static Devices INSTANCE = new Devices();

    private LoadingCache<U2fUser, Map<DeviceRegistration, String>> userDevices;

    private Devices() {
    }

    public static Devices getInstance() {
        return INSTANCE;
    }

    public void init() {
        this.userDevices = CacheBuilder.newBuilder().build(new CacheLoader<U2fUser, Map<DeviceRegistration, String>>() {
            @Override
            public Map<DeviceRegistration, String> load(U2fUser user) throws Exception {
                return new HashMap<>();
            }
        });
    }

    @Override
    public void addDeviceToUser(U2fUser user, DeviceRegistration device) {
        Map<DeviceRegistration, String> devicesFromUser = userDevices.getUnchecked(user);

        devicesFromUser.put(device, device.toJson());
    }

    @Override
    public Iterable<DeviceRegistration> getDevicesFromUser(U2fUser user) {
        return userDevices.getUnchecked(user).keySet();
    }
}
