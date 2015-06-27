package com.github.dkanellis.fikey.api;

import com.github.dkanellis.fikey.api.exceptions.InvalidPasswordException;
import com.github.dkanellis.fikey.api.exceptions.UserAlreadyExistsException;
import com.yubico.u2f.exceptions.DeviceCompromisedException;
import com.yubico.u2f.exceptions.NoEligableDevicesException;

/**
 * @author Dimitris
 */
public interface Authenticator {

    String startDeviceRegistration(String username, String password) throws UserAlreadyExistsException, InvalidPasswordException;

    String finishDeviceRegistration(String response, String username);

    String startDeviceAuthentication(String username, String password) throws NoEligableDevicesException;

    String finishDeviceAuthentication(String response, String username) throws DeviceCompromisedException;
}
