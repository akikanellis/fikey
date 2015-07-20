package com.github.dkanellis.fikey.exceptions;

/**
 * @author Dimitris
 */
public class DeviceCompromisedException extends com.yubico.u2f.exceptions.DeviceCompromisedException {
    public DeviceCompromisedException(com.yubico.u2f.exceptions.DeviceCompromisedException e) {
        super(e.getDeviceRegistration(), e.getMessage(), e.getCause());
    }
}
