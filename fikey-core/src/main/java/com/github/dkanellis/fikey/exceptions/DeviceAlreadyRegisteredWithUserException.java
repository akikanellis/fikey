package com.github.dkanellis.fikey.exceptions;

/**
 * @author Dimitris
 */
public class DeviceAlreadyRegisteredWithUserException extends Exception {
    public DeviceAlreadyRegisteredWithUserException(String deviceInfo, String username) {
        super(String.format("Device '%s' is already registered with user '%s'.", deviceInfo, username));
    }
}
