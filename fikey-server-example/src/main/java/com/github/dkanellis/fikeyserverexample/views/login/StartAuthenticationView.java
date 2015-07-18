package com.github.dkanellis.fikeyserverexample.views.login;

import io.dropwizard.views.View;

/**
 * @author Dimitris
 */
public class StartAuthenticationView extends View {

    private final String username;

    public StartAuthenticationView(String username) {
        super("startAuthenticationSucceeded.ftl");
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
