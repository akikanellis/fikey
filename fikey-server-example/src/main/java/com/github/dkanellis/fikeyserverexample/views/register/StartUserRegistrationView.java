package com.github.dkanellis.fikeyserverexample.views.register;

import io.dropwizard.views.View;

/**
 * @author Dimitris
 */
public class StartUserRegistrationView extends View {

    private final String username;

    public StartUserRegistrationView(String username) {
        super("userRegistrationSucceeded.ftl");
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
