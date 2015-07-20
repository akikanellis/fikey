package com.github.dkanellis.fikey.exceptions;

/**
 * @author Dimitris Kanellis
 */
public class WrongPasswordException extends Exception {
    public WrongPasswordException(String username) {
        super(String.format("User '%s' does not match the given password.", username));
    }
}
