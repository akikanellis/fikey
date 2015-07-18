package com.github.dkanellis.fikey.storage;

import com.github.dkanellis.fikey.exceptions.DeviceAlreadyRegisteredWithUserException;
import com.github.dkanellis.fikey.exceptions.UserAlreadyExistsException;
import com.github.dkanellis.fikey.exceptions.UserDoesNotExistException;
import com.yubico.u2f.data.DeviceRegistration;

/**
 * @author Dimitris
 */
public interface U2fUserStorage {

    U2fUser getFromUsername(String username) throws UserDoesNotExistException;

    boolean hasUser(U2fUser user);

    void addNewUser(U2fUser user) throws UserAlreadyExistsException;

    void addDeviceToUser(U2fUser user, DeviceRegistration device) throws DeviceAlreadyRegisteredWithUserException;

    Iterable<DeviceRegistration> getDevicesFromUser(U2fUser user);
}
