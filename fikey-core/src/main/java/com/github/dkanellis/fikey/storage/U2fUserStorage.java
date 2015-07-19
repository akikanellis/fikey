package com.github.dkanellis.fikey.storage;

import com.github.dkanellis.fikey.exceptions.UserAlreadyExistsException;
import com.github.dkanellis.fikey.exceptions.UserDoesNotExistException;

/**
 * @author Dimitris
 */
public interface U2fUserStorage {

    U2fUser getFromUsername(String username) throws UserDoesNotExistException;

    boolean hasUser(U2fUser user);

    void addNewUser(U2fUser user) throws UserAlreadyExistsException;
}
