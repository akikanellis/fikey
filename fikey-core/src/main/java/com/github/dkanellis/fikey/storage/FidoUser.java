package com.github.dkanellis.fikey.storage;

/**
 * @author Dimitris
 */
public interface FidoUser {

    String getUsername();

    boolean isPasswordCorrect(String password);

    void addDevice(RegisteredDevice device);

    boolean hasDevice(RegisteredDevice device);


}
