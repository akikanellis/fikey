package com.github.dkanellis.fikeyserverexample.views.login;

import io.dropwizard.views.View;

/**
 * @author Dimitris Kanellis
 */
public class FinishDeviceAuthenticationView extends View {

    private final String username;
    private final String loginInfo;
    private final String header;
    private final String messageTitle;

    public FinishDeviceAuthenticationView(String username, String loginInfo, boolean compromised) {
        super("finishDeviceAuthentication.ftl");
        this.username = username;
        this.loginInfo = loginInfo;
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
