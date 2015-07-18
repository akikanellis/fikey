package com.github.dkanellis.fikeyserverexample.views.register;

import io.dropwizard.views.View;

/**
 * @author Dimitris
 */
public class RegistrationFailedView extends View {

    private final String username;
    private final String exceptionMessage;


    public RegistrationFailedView(String username, Exception e) {
        super("registrationFailed.ftl");
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
