package com.github.dkanellis.fikeyserverexample.views.register;

import io.dropwizard.views.View;

/**
 * @author Dimitris
 */
public class StartRegistrationView extends View {

    private final String username;

    public StartRegistrationView(String username) {
        super("startRegistrationSucceeded.ftl");
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
