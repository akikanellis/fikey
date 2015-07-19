package com.github.dkanellis.fikey.storage;

import com.yubico.u2f.data.DeviceRegistration;

/**
 * @author Dimitris
 */
public interface U2fDeviceStorage extends Initializable {

    void addDeviceToUser(U2fUser user, DeviceRegistration device);

    Iterable<DeviceRegistration> getDevicesFromUser(U2fUser user);
}
