package com.github.dkanellis.fikeyserverexample.views.login;

import io.dropwizard.views.View;

/**
 * @author Dimitris
 */
public class StartUserAuthenticationView extends View {

    private final String username;

    public StartUserAuthenticationView(String username) {
        super("userAuthenticationSucceeded.ftl");
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
