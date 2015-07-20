package com.github.dkanellis.fikey.storage;

import com.yubico.u2f.data.DeviceRegistration;

/**
 * An interface which declares the functionality of a U2F compatible device storage. A storage like that must be able
 * to store a {@link U2fUser}'s  devices in an efficient way, add/update a FIDO {@link DeviceRegistration} to a user
 * and also return all the devices of a particular user.
 *
 * @author Dimitris
 */
public interface U2fDeviceStorage extends Initializable {

    void addDeviceToUser(U2fUser user, DeviceRegistration device);

    Iterable<DeviceRegistration> getDevicesFromUser(U2fUser user);
}
