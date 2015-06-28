package com.github.dkanellis.fikey.exceptions;

import com.yubico.u2f.data.DeviceRegistration;

/**
 * @author Dimitris
 */
public class DeviceCompromisedException extends com.yubico.u2f.exceptions.DeviceCompromisedException {
    public DeviceCompromisedException(DeviceRegistration registration, String message, Throwable cause) {
        super(registration, message, cause);
    }

    public DeviceCompromisedException(DeviceRegistration registration, String message) {
        super(registration, message);
    }

    public DeviceCompromisedException(com.yubico.u2f.exceptions.DeviceCompromisedException e) {
        super(e.getDeviceRegistration(), e.getMessage(), e.getCause());
    }
}
