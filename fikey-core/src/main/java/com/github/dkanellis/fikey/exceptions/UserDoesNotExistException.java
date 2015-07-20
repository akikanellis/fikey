package com.github.dkanellis.fikey.exceptions;

/**
 * @author Dimitris Kanellis
 */
public class UserDoesNotExistException extends Exception {
    public UserDoesNotExistException(String username) {
        super(String.format("User '%s' does not exist in the database.", username));
    }
}
