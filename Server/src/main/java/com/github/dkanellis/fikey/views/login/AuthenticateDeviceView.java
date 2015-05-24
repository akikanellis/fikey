package com.github.dkanellis.fikey.views.login;

import io.dropwizard.views.View;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Dimitris
 */
public class AuthenticateDeviceView extends View {

    private final String username;
    private final String data;

    public AuthenticateDeviceView(String username, String data) {
        super("authenticateDevice.ftl");
        this.username = checkNotNull(username);
        this.data = checkNotNull(data);
    }

    public String getUsername() {
        return username;
    }

    public String getData() {
        return data;
    }
}
