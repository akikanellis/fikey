package com.github.dkanellis.fikey;

import com.github.dkanellis.fikey.exceptions.*;
import com.github.dkanellis.fikey.storage.Requests;
import com.github.dkanellis.fikey.storage.U2fUser;
import com.github.dkanellis.fikey.storage.User;
import com.github.dkanellis.fikey.storage.Users;
import com.yubico.u2f.U2F;
import com.yubico.u2f.data.DeviceRegistration;
import com.yubico.u2f.data.messages.AuthenticateRequestData;
import com.yubico.u2f.data.messages.AuthenticateResponse;
import com.yubico.u2f.data.messages.RegisterRequestData;
import com.yubico.u2f.data.messages.RegisterResponse;

/**
 * @author Dimitris
 */
public class FiKeyAuth implements Authenticator {

    private final static String DISALLOWED_PASSWORD_CHARS = "&%";
    private final static int MINIMUM_USERNAME_CHARS = 4;
    private final String appId;
    private final Users users;
    private final Requests requests;
    private U2F u2fManager;

    public FiKeyAuth(String appId) {
        this.appId = appId;
        this.users = Users.getInstance();
        this.requests = Requests.getInstance();
        this.u2fManager = new U2F();
    }

    @Override
    public void registerNewUser(String username, String password) throws UserAlreadyExistsException, InvalidPasswordException, InvalidUsernameException {
        if (passwordIsInvalid(password)) {
            throw new InvalidPasswordException(DISALLOWED_PASSWORD_CHARS);
        }

        if (usernameIsInvalid(username)) {
            throw new InvalidUsernameException(MINIMUM_USERNAME_CHARS);
        }

        U2fUser user = new User(username, password);
        users.addNewUser(user);
    }

    private boolean usernameIsInvalid(String username) {
        return username.length() < MINIMUM_USERNAME_CHARS;
    }

    @Override
    public String startDeviceRegistration(String username) throws UserDoesNotExistException {
        U2fUser user = users.getFromUsername(username);

        Iterable<DeviceRegistration> userDevices = users.getDevicesFromUser(user);
        RegisterRequestData registerRequest = u2fManager.startRegistration(appId, userDevices);
        requests.addNewRequest(registerRequest);

        return registerRequest.toJson();

    }

    @Override
    public String finishDeviceRegistration(String response, String username) throws UserDoesNotExistException {
        U2fUser user = users.getFromUsername(username);

        RegisterResponse registerResponse = RegisterResponse.fromJson(response);
        String jsonRequest = requests.removeAndReturnFromResponse(registerResponse);
        RegisterRequestData registerRequest = RegisterRequestData.fromJson(jsonRequest);
        DeviceRegistration device = u2fManager.finishRegistration(registerRequest, registerResponse);
        users.addDeviceToUser(user, device);

        return device.toString();
    }

    @Override
    public void authenticateUser(String username, String password) throws UserDoesNotExistException, InvalidPasswordException {
        U2fUser user = users.getFromUsername(username);
        if (!user.isPasswordCorrect(password)) {
            throw new InvalidPasswordException();
        }
    }

    @Override
    public String startDeviceAuthentication(String username) throws NoEligibleDevicesException, UserDoesNotExistException {
        U2fUser user = users.getFromUsername(username);

        Iterable<DeviceRegistration> userDevices = users.getDevicesFromUser(user);
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

    @Override
    public String finishDeviceAuthentication(String response, String username) throws DeviceCompromisedException, UserDoesNotExistException {
        U2fUser user = users.getFromUsername(username);
        AuthenticateResponse authenticateResponse = AuthenticateResponse.fromJson(response);
        String jsonRequest = requests.removeAndReturnFromResponse(authenticateResponse);
        AuthenticateRequestData authenticateRequest = AuthenticateRequestData.fromJson(jsonRequest);

        DeviceRegistration registration = getDeviceRegistration(user, authenticateResponse, authenticateRequest);
        users.addDeviceToUser(user, registration);

        return registration.toString();
    }

    private DeviceRegistration getDeviceRegistration(U2fUser user, AuthenticateResponse authenticateResponse, AuthenticateRequestData authenticateRequest) throws DeviceCompromisedException {
        DeviceRegistration registration;
        try {
            registration = u2fManager.finishAuthentication(authenticateRequest, authenticateResponse, users.getDevicesFromUser(user));
            return registration;
        } catch (com.yubico.u2f.exceptions.DeviceCompromisedException e) {
            registration = e.getDeviceRegistration();
            users.addDeviceToUser(user, registration);
            throw new DeviceCompromisedException(e);
        }
    }

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

    private boolean userAlreadyExists(String username) {
        return false; // TODO implement this
    }
}
