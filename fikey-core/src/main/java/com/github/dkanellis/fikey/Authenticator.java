package com.github.dkanellis.fikey;

import com.github.dkanellis.fikey.exceptions.DeviceCompromisedException;
import com.github.dkanellis.fikey.exceptions.InvalidPasswordException;
import com.github.dkanellis.fikey.exceptions.NoEligibleDevicesException;
import com.github.dkanellis.fikey.exceptions.UserAlreadyExistsException;

/**
 * @author Dimitris
 */
public interface Authenticator {

    String startDeviceRegistration(String username, String password) throws UserAlreadyExistsException, InvalidPasswordException;

    String finishDeviceRegistration(String response, String username);

    String startDeviceAuthentication(String username, String password) throws NoEligibleDevicesException;

    String finishDeviceAuthentication(String response, String username) throws DeviceCompromisedException;
}
