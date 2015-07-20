package com.github.dkanellis.fikey;

import com.github.dkanellis.fikey.exceptions.*;
import com.github.dkanellis.fikey.storage.*;
import com.yubico.u2f.U2F;
import com.yubico.u2f.data.DeviceRegistration;
import com.yubico.u2f.data.messages.AuthenticateRequestData;
import com.yubico.u2f.data.messages.AuthenticateResponse;
import com.yubico.u2f.data.messages.RegisterRequestData;
import com.yubico.u2f.data.messages.RegisterResponse;

/**
 * The main class of the API which implements all the appropriate functionality in order for the U2F registration and
 * login to be as simple as possible. The class can be instantiated with the default provided storages and settings or
 * each storage and setting can be set separately at a developer's leisure.
 *
 * @author Dimitris
 */
public class FiKeyAuth implements Authenticator {

    private static volatile String DISALLOWED_PASSWORD_CHARS = "&%";
    private static volatile int MINIMUM_USERNAME_CHARS = 4;

    private static volatile U2fUserStorage users;
    private static volatile U2fRequestStorage requests;
    private static volatile U2fDeviceStorage devices;

    private final String appId;

    private U2F u2fManager;

    public FiKeyAuth(String appId) {
        this.appId = appId;
        this.u2fManager = new U2F();
    }

    public static void initDefaultStorage() {
        Users.getInstance().init();
        Requests.getInstance().init();
        Devices.getInstance().init();

        users = Users.getInstance();
        devices = Devices.getInstance();
        requests = Requests.getInstance();
    }

    public static void setUserStorage(U2fUserStorage userStorage) {
        userStorage.init();
        users = userStorage;
    }

    public static void setRequestStorage(U2fRequestStorage requestStorage) {
        requestStorage.init();
        requests = requestStorage;
    }

    public static void setDeviceStorage(U2fDeviceStorage deviceStorage) {
        deviceStorage.init();
        devices = deviceStorage;
    }

    public static void setDisallowedPasswordCharacters(String disallowedPasswordCharacters) {
        DISALLOWED_PASSWORD_CHARS = disallowedPasswordCharacters;
    }

    public static void setMinimumUsernameCharacters(final int minimumUsernameCharacters) {
        MINIMUM_USERNAME_CHARS = minimumUsernameCharacters;
    }

    @Override
    public void registerUser(String username, String password)
            throws UserAlreadyExistsException, InvalidPasswordException, InvalidUsernameException {
        if (usernameIsInvalid(username)) {
            throw new InvalidUsernameException(MINIMUM_USERNAME_CHARS);
        }

        if (passwordIsInvalid(password)) {
            throw new InvalidPasswordException(DISALLOWED_PASSWORD_CHARS);
        }

        U2fUser user = new User(username, password);
        users.addNewUser(user);
    }

    private boolean usernameIsInvalid(String username) {
        return username.length() < MINIMUM_USERNAME_CHARS;
    }

    /**
     * Initiates a registration of a device with a given user. We first get a reference to the user himself, then to
     * the user's devices, then make the register request and then add that request to our {@link U2fRequestStorage}.
     *
     * @param username the user to add the device to
     * @return the information of the register request
     * @throws UserDoesNotExistException should not happen unless {@link #registerUser(String, String)} failed
     */
    @Override
    public String startDeviceRegistration(String username) throws UserDoesNotExistException {
        U2fUser user = users.getFromUsername(username);

        Iterable<DeviceRegistration> userDevices = devices.getDevicesFromUser(user);
        RegisterRequestData registerRequest = u2fManager.startRegistration(appId, userDevices);
        requests.addNewRequest(registerRequest);

        return registerRequest.toJson();

    }

    /**
     * Finishes the registration of a device with a user by first acquiring the user from the storage, then the response
     * of the device, then matching the response against the request from our storage and then adding the device
     * to the user.
     *
     * @param response the device's response
     * @param username the user to add the device to
     * @return the device's information
     * @throws UserDoesNotExistException should not happen unless {@link #registerUser(String, String)} failed
     */
    @Override
    public String finishDeviceRegistration(String response, String username) throws UserDoesNotExistException {
        U2fUser user = users.getFromUsername(username);

        RegisterResponse registerResponse = RegisterResponse.fromJson(response);
        String jsonRequest = requests.removeAndReturnFromResponse(registerResponse);
        RegisterRequestData registerRequest = RegisterRequestData.fromJson(jsonRequest);
        DeviceRegistration device = u2fManager.finishRegistration(registerRequest, registerResponse);
        devices.addDeviceToUser(user, device);

        return device.toString();
    }

    @Override
    public void authenticateUser(String username, String password) throws UserDoesNotExistException, WrongPasswordException {
        U2fUser user = users.getFromUsername(username);
        if (!user.isPasswordCorrect(password)) {
            throw new WrongPasswordException(username);
        }
    }

    /**
     * Initiates a device authentication against the user. We first get the user from our storage, then a reference to
     * the user's devices, then get the authenticate request data from said devices and then we add the request for
     * authentication to our {@link U2fRequestStorage}.
     *
     * @param username the user to check the device against
     * @return the information of the authentication request
     * @throws UserDoesNotExistException  should not happen unless {@link #authenticateUser(String, String)} failed
     * @throws NoEligibleDevicesException no devices registered to the user or all his devices have been compromised
     */
    @Override
    public String startDeviceAuthentication(String username) throws NoEligibleDevicesException, UserDoesNotExistException {
        U2fUser user = users.getFromUsername(username);

        Iterable<DeviceRegistration> userDevices = devices.getDevicesFromUser(user);
        AuthenticateRequestData authenticateRequestData = getAuthenticateRequestData(userDevices);
        requests.addNewRequest(authenticateRequestData);

        return authenticateRequestData.toString();
    }

    private AuthenticateRequestData getAuthenticateRequestData(Iterable<DeviceRegistration> userDevices) throws NoEligibleDevicesException {
        try {
            return u2fManager.startAuthentication(appId, userDevices);
        } catch (com.yubico.u2f.exceptions.NoEligableDevicesException e) {
            throw new NoEligibleDevicesException(e);
        }
    }

    /**
     * Finishes the authentication of a device with a user by acquiring the user from the storage, then the response of
     * the device, then matching the response against the request from our storage and then updating the device
     * in the storage.
     *
     * @param response the device's response
     * @param username the user to add the device to
     * @return the device's information
     * @throws UserDoesNotExistException  should not happen unless {@link #registerUser(String, String)} failed
     * @throws DeviceCompromisedException the device has either been compromised or its counter is too low
     */
    @Override
    public String finishDeviceAuthentication(String response, String username)
            throws DeviceCompromisedException, UserDoesNotExistException {
        U2fUser user = users.getFromUsername(username);

        AuthenticateResponse authenticateResponse = AuthenticateResponse.fromJson(response);
        String jsonRequest = requests.removeAndReturnFromResponse(authenticateResponse);
        AuthenticateRequestData authenticateRequest = AuthenticateRequestData.fromJson(jsonRequest);
        DeviceRegistration device = getDeviceRegistration(user, authenticateResponse, authenticateRequest);
        devices.addDeviceToUser(user, device);

        return device.toString();
    }

    /**
     * Get the device and finish the authentication matching the user, response and request. If matching is not
     * possible or the device has been compromised, mark the device as compromised and update it in the storage.
     *
     * @param user                 the user to which the device belongs to
     * @param authenticateResponse the device's response
     * @param authenticateRequest  the request from the storage
     * @return the device that was matched
     * @throws DeviceCompromisedException the device has either been compromised or its counter is too low
     */
    private DeviceRegistration getDeviceRegistration(U2fUser user, AuthenticateResponse authenticateResponse,
                                                     AuthenticateRequestData authenticateRequest)
            throws DeviceCompromisedException {
        DeviceRegistration registration;
        try {
            registration = u2fManager.finishAuthentication(authenticateRequest, authenticateResponse, devices.getDevicesFromUser(user));
            return registration;
        } catch (com.yubico.u2f.exceptions.DeviceCompromisedException e) {
            registration = e.getDeviceRegistration();
            devices.addDeviceToUser(user, registration);
            throw new DeviceCompromisedException(e);
        }
    }

    /**
     * Checks if any of the disallowed characters are included in the password.
     *
     * @param password the password to check
     * @return if the password is of proper quality
     */
    private boolean passwordIsInvalid(String password) {
        if (password == null) {
            return true;
        }

        for (char c : DISALLOWED_PASSWORD_CHARS.toCharArray()) {
            if (password.indexOf(c) != -1) {
                return true;
            }
        }
        return false;
    }
}
