package com.github.dkanellis.fikeyserverexample.views.register;

import io.dropwizard.views.View;

/**
 * @author Dimitris
 */
public class StartRegistrationView extends View {

    private final String username;
    private String extraMessage;

    public StartRegistrationView(String username) {
        super("startRegistrationSucceeded.ftl");
        this.username = username;
    }

    public StartRegistrationView(String username, String localizedMessage) {
        super("startRegistrationFailed.ftl");
        this.username = username;
        this.extraMessage = localizedMessage;
    }

    public String getUsername() {
        return username;
    }

    public String getExtraMessage() {
        return extraMessage;
    }
}
