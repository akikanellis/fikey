package com.github.dkanellis.fikey.storage;

import com.yubico.u2f.data.DeviceRegistration;

/**
 * @author Dimitris
 */
public interface FidoDevice {

    String getKey();

    String getInfo();

    DeviceRegistration getDevice();
}
