package com.github.dkanellis.fikey.views.register;

import io.dropwizard.views.View;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Dimitris
 */
public class StartDeviceRegistrationView extends View {

    private final String username;
    private final String data;

    public StartDeviceRegistrationView(String username, String data) {
        super("registerDevice.ftl");
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
