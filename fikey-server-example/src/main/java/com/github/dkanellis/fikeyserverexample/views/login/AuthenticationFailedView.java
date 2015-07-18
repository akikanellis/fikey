package com.github.dkanellis.fikeyserverexample.views.login;

import io.dropwizard.views.View;

/**
 * @author Dimitris
 */
public class AuthenticationFailedView extends View {

    private final String username;
    private final String exceptionMessage;


    public AuthenticationFailedView(String username, Exception e) {
        super("authenticationFailed.ftl");
        this.username = username;
        this.exceptionMessage = e.getMessage();
    }

    public String getUsername() {
        return username;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }
}
