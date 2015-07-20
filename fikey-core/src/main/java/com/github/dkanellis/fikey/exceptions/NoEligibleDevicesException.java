package com.github.dkanellis.fikey.exceptions;

/**
 * An exception for when either there are no devices registered to the user or all his devices have been compromised.
 *
 * @author Dimitris Kanellis
 */
public class NoEligibleDevicesException extends com.yubico.u2f.exceptions.NoEligableDevicesException {
    public NoEligibleDevicesException(com.yubico.u2f.exceptions.NoEligableDevicesException e) {
        super(e.getDeviceRegistrations(), e.getMessage(), e.getCause());
    }
}
