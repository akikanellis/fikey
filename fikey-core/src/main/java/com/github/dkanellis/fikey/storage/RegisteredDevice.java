package com.github.dkanellis.fikey.storage;

import com.yubico.u2f.data.DeviceRegistration;

/**
 * @author Dimitris
 */
public class RegisteredDevice implements FidoDevice {

    private String deviceKey;
    private String deviceInfo;

    public RegisteredDevice(String deviceKey, String deviceInfo) {
        this.deviceKey = deviceKey;
        this.deviceInfo = deviceInfo;
    }

    @Override
    public String getKey() {
        return deviceKey;
    }

    @Override
    public String getInfo() {
        return deviceInfo;
    }

    @Override
    public DeviceRegistration getDevice() {
        return DeviceRegistration.fromJson(deviceInfo);
    }

    @Override
    public int hashCode() {
        return deviceKey.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RegisteredDevice)) {
            return false;
        }

        RegisteredDevice other = (RegisteredDevice) o;

        return deviceKey.equals(other.deviceKey);
    }
}
