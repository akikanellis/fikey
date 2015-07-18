package com.github.dkanellis.fikey.storage;

import com.yubico.u2f.data.DeviceRegistration;

/**
 * @author Dimitris
 */
public interface UserStorage {

    boolean hasUser(FidoUser user);

    void addUser(String username, String password);

    void addDeviceToUser(String username, DeviceRegistration device);
}
