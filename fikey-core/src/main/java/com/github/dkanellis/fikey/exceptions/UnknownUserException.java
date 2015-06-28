package com.github.dkanellis.fikey.exceptions;

/**
 * @author Dimitris
 */
public class UnknownUserException extends Exception {

    public UnknownUserException(String username) {
        super(String.format("User '%s' was not found in the database.", username));
    }
}
