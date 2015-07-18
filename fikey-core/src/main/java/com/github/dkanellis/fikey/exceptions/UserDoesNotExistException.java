package com.github.dkanellis.fikey.exceptions;

/**
 * @author Dimitris
 */
public class UserDoesNotExistException extends Exception {
    public UserDoesNotExistException(String username) {
        super(String.format("User '%s' does not exist in the database.", username));
    }

    public UserDoesNotExistException(UserDoesNotExistException e) {
        super(e.getMessage());
    }
}
