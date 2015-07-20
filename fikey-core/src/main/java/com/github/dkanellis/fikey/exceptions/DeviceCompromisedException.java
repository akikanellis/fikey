package com.github.dkanellis.fikey.exceptions;

/**
 * An exception for when a device has been compromised.
 * A device can be compromised either when it's internal counter is smaller than expected or it has been marked as
 * compromised and cannot be authenticated.
 *
 * @author Dimitris
 */
public class DeviceCompromisedException extends com.yubico.u2f.exceptions.DeviceCompromisedException {
    public DeviceCompromisedException(com.yubico.u2f.exceptions.DeviceCompromisedException e) {
        super(e.getDeviceRegistration(), e.getMessage(), e.getCause());
    }
}
