package com.github.dkanellis.fikey.views;

import com.yubico.u2f.data.DeviceRegistration;
import io.dropwizard.views.View;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 * @author Dimitris
 */
public class FinishDeviceAuthenticationView extends View {

    private final String username;
    private final String loginInfo;
    private final String header;
    private final String messageTitle;

    public FinishDeviceAuthenticationView(String username, DeviceRegistration registration) {
        super("finishDeviceAuthentication.ftl");
        this.username = username;
        this.loginInfo = StringEscapeUtils.escapeHtml4(registration.toString());
        final boolean compromised = registration.isCompromised();
        this.header = compromised ?
                "Authentication for user " + username + " failed, the device has been possibly compromised." :
                "Authentication for user " + username + "  was successful!";
        this.messageTitle = compromised ? "Error info: " : "Device info: ";
    }

    public String getHeader() {
        return header;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public String getUsername() {
        return username;
    }

    public String getLoginInfo() {
        return loginInfo;
    }
}
