package com.github.dkanellis.fikey.exceptions;

/**
 * @author Dimitris Kanellis
 */
public class InvalidUsernameException extends Exception {
    public InvalidUsernameException(final int minCharacters) {
        super(String.format("Your username must be at least %s characters!", minCharacters));
    }
}
