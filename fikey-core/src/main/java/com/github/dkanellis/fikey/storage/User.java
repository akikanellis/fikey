package com.github.dkanellis.fikey.storage;

import com.yubico.u2f.data.DeviceRegistration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dimitris
 */
public class User implements U2fUser {

    private String username;
    private String password;
    private List<DeviceRegistration> devices;

    public User(String username) {
        this.username = username;
        this.password = null;
        this.devices = new ArrayList<>();
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.devices = new ArrayList<>();
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
    public void addDevice(DeviceRegistration device) {
        devices.add(device);
    }

    @Override
    public boolean hasDevice(DeviceRegistration device) {
        return devices.contains(device);
    }

    @Override
    public Iterable<DeviceRegistration> getDevices() {
        return devices;
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof User) {
            User other = (User) o;
            return username.equals(other.username);
        } else if (o instanceof String) {
            String otherUsername = (String) o;
            return username.equals(otherUsername);
        }

        return false;
    }
}
