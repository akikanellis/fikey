package com.github.dkanellis.fikey.views;

import com.yubico.u2f.data.DeviceRegistration;
import io.dropwizard.views.View;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 * @author Dimitris
 */
public class FinishDeviceRegistrationView extends View {

    private final String username;
    private final String registrationInfo;

    public FinishDeviceRegistrationView(String username, DeviceRegistration registration) {
        super("finishDeviceRegistration.ftl");
        this.username = username;
        this.registrationInfo = StringEscapeUtils.escapeHtml4(registration.toString());
    }

    public String getUsername() {
        return username;
    }

    public String getRegistrationInfo() {
        return registrationInfo;
    }
}
