package com.github.dkanellis.fikey.storage;

import com.yubico.u2f.data.DeviceRegistration;

/**
 * @author Dimitris
 */
public interface U2fUser {

    String getUsername();

    boolean isPasswordCorrect(String password);

    void addDevice(DeviceRegistration device);

    boolean hasDevice(DeviceRegistration device);

    Iterable<DeviceRegistration> getDevices();
}
