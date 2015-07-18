package com.github.dkanellis.fikey;

import com.github.dkanellis.fikey.exceptions.*;

/**
 * @author Dimitris
 */
public interface Authenticator {

    void registerNewUser(String username, String password) throws UserAlreadyExistsException, InvalidPasswordException;

    String startDeviceRegistration(String username) throws UserAlreadyExistsException, InvalidPasswordException, UserDoesNotExistException;

    String finishDeviceRegistration(String response, String username) throws UserDoesNotExistException, DeviceAlreadyRegisteredWithUserException;

    void authenticateUser(String username, String password) throws UserDoesNotExistException, InvalidPasswordException;

    String startDeviceAuthentication(String username) throws NoEligibleDevicesException, UserDoesNotExistException;

    String finishDeviceAuthentication(String response, String username) throws DeviceCompromisedException, UserDoesNotExistException, DeviceAlreadyRegisteredWithUserException;
}
