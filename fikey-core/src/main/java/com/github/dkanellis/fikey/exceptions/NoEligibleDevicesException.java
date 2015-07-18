package com.github.dkanellis.fikey.exceptions;

import com.yubico.u2f.data.DeviceRegistration;
import com.yubico.u2f.exceptions.NoEligableDevicesException;

/**
 * @author Dimitris
 */
public class NoEligibleDevicesException extends NoEligableDevicesException {
    public NoEligibleDevicesException(Iterable<? extends DeviceRegistration> devices, String message, Throwable cause) {
        super(devices, message, cause);
    }

    public NoEligibleDevicesException(Iterable<? extends DeviceRegistration> devices, String message) {
        super(devices, message);
    }

    public NoEligibleDevicesException(NoEligableDevicesException e) {
        super(e.getDeviceRegistrations(), e.getMessage(), e.getCause());
    }
}
