package com.github.dkanellis.fikey;

import com.github.dkanellis.fikey.exceptions.*;

/**
 * The Authenticator interface holds the main functionality of registering and logging in a user with the FIDO
 * Universal Two Factor Authentication.
 * <p>
 * To register a new user, the first part of the U2F registration is the username along with the password
 * registration where they occur in the method {@link #registerUser (String, String)} where the API user (a developer)
 * can ensure that the password and username are of proper quality. For the second part (the device registration) the
 * methods {@link #startDeviceRegistration(String)} and {@link #finishDeviceRegistration(String, String)} are to be
 * used to properly register the FIDO device with the user.
 * <p>
 * To authenticate a user, for the first part of the U2F authentication the method {@link #authenticateUser(String,
 * String)} is used to ensure that the user exists and that the password is correct. For the device authentication
 * the methods {@link #startDeviceAuthentication(String)} and {@link #finishDeviceAuthentication(String, String)} are
 * to be used to make sure that the device belongs to the user and has not been compromised.
 *
 * @author Dimitris Kanellis
 */
public interface Authenticator {

    void registerUser(String username, String password) throws UserAlreadyExistsException, InvalidUsernameException, InvalidPasswordException;

    String startDeviceRegistration(String username) throws UserDoesNotExistException;

    String finishDeviceRegistration(String response, String username) throws UserDoesNotExistException;

    void authenticateUser(String username, String password) throws UserDoesNotExistException, WrongPasswordException;

    String startDeviceAuthentication(String username) throws NoEligibleDevicesException, UserDoesNotExistException;

    String finishDeviceAuthentication(String response, String username) throws DeviceCompromisedException, UserDoesNotExistException;
}
