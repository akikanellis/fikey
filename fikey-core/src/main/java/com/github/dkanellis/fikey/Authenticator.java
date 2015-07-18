package com.github.dkanellis.fikey;

import com.github.dkanellis.fikey.exceptions.*;

/**
 * @author Dimitris
 */
public interface Authenticator {

    String startDeviceRegistration(String username, String password) throws UserAlreadyExistsException, InvalidPasswordException, UserDoesNotExistException;

    String finishDeviceRegistration(String response, String username) throws UserDoesNotExistException, DeviceAlreadyRegisteredWithUserException;

    String startDeviceAuthentication(String username, String password) throws NoEligibleDevicesException, UserDoesNotExistException;

    String finishDeviceAuthentication(String response, String username) throws DeviceCompromisedException, UserDoesNotExistException, DeviceAlreadyRegisteredWithUserException;
}
