package com.github.dkanellis.fikey;

import com.github.dkanellis.fikey.exceptions.*;

/**
 * @author Dimitris
 */
public interface Authenticator {

    void registerUser(String username, String password) throws UserAlreadyExistsException, InvalidUsernameException, InvalidPasswordException;

    String startDeviceRegistration(String username) throws UserDoesNotExistException;

    String finishDeviceRegistration(String response, String username) throws UserDoesNotExistException;

    void authenticateUser(String username, String password) throws UserDoesNotExistException, WrongPasswordException;

    String startDeviceAuthentication(String username) throws NoEligibleDevicesException, UserDoesNotExistException;

    String finishDeviceAuthentication(String response, String username) throws DeviceCompromisedException, UserDoesNotExistException;
}
