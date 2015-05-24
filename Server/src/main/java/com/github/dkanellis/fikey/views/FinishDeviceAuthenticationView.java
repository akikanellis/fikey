package com.github.dkanellis.fikey.views;

import com.yubico.u2f.data.DeviceRegistration;
import io.dropwizard.views.View;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 * @author Dimitris
 */
public class FinishDeviceAuthenticationView extends View {

    private final String username;
    private final String authenticationInfo;

    public FinishDeviceAuthenticationView(String username, DeviceRegistration registration) {
        super("finishDeviceAuthentication.ftl");
        this.username = username;
        this.authenticationInfo = StringEscapeUtils.escapeHtml4(registration.toString());
    }

    public String getUsername() {
        return username;
    }

    public String getAuthenticationInfo() {
        return authenticationInfo;
    }
}
