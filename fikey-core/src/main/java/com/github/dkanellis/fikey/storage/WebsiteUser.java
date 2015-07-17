package com.github.dkanellis.fikey.storage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dimitris
 */
public class WebsiteUser implements FidoUser {

    private String username;
    private String password;
    private List<RegisteredDevice> devices;

    public WebsiteUser(String username, String password) {
        this.username = username;
        this.password = password;
        devices = new ArrayList<>();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isPasswordCorrect(String password) {
        return this.password.equals(password);
    }

    @Override
    public void addDevice(RegisteredDevice device) {
        devices.add(device);
    }

    @Override
    public boolean hasDevice(RegisteredDevice device) {
        return devices.contains(device);
    }
}
