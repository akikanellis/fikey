package com.github.dkanellis.fikey.storage;

/**
 * A U2fUser should be able to take care of the first part of the two-factor authentication by being able to
 * recognize if the password given is correct and should also be able to return the username.
 *
 * @author Dimitris Kanellis
 */
public interface U2fUser {

    String getUsername();

    boolean isPasswordCorrect(String password);
}
