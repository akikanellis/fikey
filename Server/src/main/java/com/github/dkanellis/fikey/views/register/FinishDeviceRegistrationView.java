package com.github.dkanellis.fikey.views.register;

import io.dropwizard.views.View;

/**
 * @author Dimitris Kanellis
 */
public class FinishDeviceRegistrationView extends View {

    private final String username;
    private final String registrationInfo;

    public FinishDeviceRegistrationView(String username, String registrationInfo) {
        super("finishDeviceRegistration.ftl");
        this.username = username;
        this.registrationInfo = registrationInfo;
    }

    public String getUsername() {
        return username;
    }

    public String getRegistrationInfo() {
        return registrationInfo;
    }
}
