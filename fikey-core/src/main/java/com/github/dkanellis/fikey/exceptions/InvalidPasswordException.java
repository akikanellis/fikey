package com.github.dkanellis.fikey.exceptions;

/**
 * @author Dimitris
 */
public class InvalidPasswordException extends Exception {
    public InvalidPasswordException(String disallowedChars) {
        super(String.format("Your password cannot contain any of these characters: '%s'", disallowedChars));
    }
}
