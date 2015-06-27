package com.github.dkanellis.fikey.api.exceptions;

/**
 * @author Dimitris
 */
public class WrongPasswordException extends Exception {
    public WrongPasswordException(String username, String password) {
        super(String.format("User '%s' does not match password '%s'", username, password));
    }
}
