package com.github.dkanellis.fikey.exceptions;

/**
 * @author Dimitris
 */
public class NoEligibleDevicesException extends com.yubico.u2f.exceptions.NoEligableDevicesException {
    public NoEligibleDevicesException(com.yubico.u2f.exceptions.NoEligableDevicesException e) {
        super(e.getDeviceRegistrations(), e.getMessage(), e.getCause());
    }
}
