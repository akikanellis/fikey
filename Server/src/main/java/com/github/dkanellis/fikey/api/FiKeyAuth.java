package com.github.dkanellis.fikey.api;

import com.github.dkanellis.fikey.api.exceptions.InvalidPasswordException;
import com.github.dkanellis.fikey.api.exceptions.UserAlreadyExistsException;
import com.github.dkanellis.fikey.api.storage.DataStorage;
import com.github.dkanellis.fikey.utils.Statics;
import com.yubico.u2f.U2F;
import com.yubico.u2f.data.DeviceRegistration;
import com.yubico.u2f.data.messages.RegisterRequestData;
import com.yubico.u2f.data.messages.RegisterResponse;

/**
 * @author Dimitris
 */
public class FiKeyAuth implements Authenticator {

    private final DataStorage storage;
    private final String disallowedCharacters;
    private U2F u2fManager;

    public FiKeyAuth() {
        this.storage = DataStorage.getInstance();
        this.u2fManager = new U2F();
        this.disallowedCharacters = "&%";
    }

    @Override
    public String startDeviceRegistration(String username, String password) throws UserAlreadyExistsException, InvalidPasswordException {
        if (userAlreadyExists(username)) {
            throw new UserAlreadyExistsException(username);
        }

        if (passwordIsInvalid(password)) {
            throw new InvalidPasswordException(disallowedCharacters);
        }

        Iterable<DeviceRegistration> userDevices = storage.getDevicesFromUser(username);
        RegisterRequestData registerRequest = u2fManager.startRegistration(Statics.APP_ID, userDevices);
        storage.addRequest(registerRequest.getRequestId(), registerRequest.toJson());

        return registerRequest.toJson();
    }

    @Override
    public String finishDeviceRegistration(String response, String username) {
        RegisterResponse registerResponse = RegisterResponse.fromJson(response);
        RegisterRequestData registerRequest
                = RegisterRequestData.fromJson(storage.removeRequest(registerResponse.getRequestId()));
        DeviceRegistration registration = u2fManager.finishRegistration(registerRequest, registerResponse);
        storage.addDeviceToUser(username, registration.getKeyHandle(), registration.toJson());

        return registration.toString();
    }

    private boolean passwordIsInvalid(String password) {
        return false; // TODO remove this

//        if (password == null){
//            return true;
//        }
//
//        for (char c : disallowedCharacters.toCharArray()){
//            if (password.indexOf(c) != -1){
//                return true;
//            }
//        }
//        return false;
    }

    private boolean userAlreadyExists(String username) {
        return false; // TODO implement this
    }

    @Override
    public void login() {

    }
}
