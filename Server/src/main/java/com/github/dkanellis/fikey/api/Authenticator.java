package com.github.dkanellis.fikey.api;

import com.github.dkanellis.fikey.api.exceptions.InvalidPasswordException;
import com.github.dkanellis.fikey.api.exceptions.UserAlreadyExistsException;

/**
 * @author Dimitris
 */
public interface Authenticator {

    String startDeviceRegistration(String username, String password) throws UserAlreadyExistsException, InvalidPasswordException;

    String finishDeviceRegistration(String response, String username);

    void login();
}
