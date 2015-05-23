package com.github.dkanellis.fikey.views;

import io.dropwizard.views.View;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Dimitris
 */
public class RegisterDeviceView extends View {

    private final String username;
    private final String data;

    public RegisterDeviceView(String username, String data) {
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
