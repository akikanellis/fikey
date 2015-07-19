package com.github.dkanellis.fikey.storage;

/**
 * @author Dimitris
 */
public interface U2fUser {

    String getUsername();

    boolean isPasswordCorrect(String password);
}
