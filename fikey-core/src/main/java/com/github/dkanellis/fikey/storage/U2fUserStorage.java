package com.github.dkanellis.fikey.storage;

import com.github.dkanellis.fikey.exceptions.UserAlreadyExistsException;
import com.github.dkanellis.fikey.exceptions.UserDoesNotExistException;

/**
 * An interface which declares the functionality of a user storage. A user storage is used to keep a reference to
 * every user that the website/application will hold. A storage like that must be able to store only one reference of a
 * {@link U2fUser} to avoid duplicate users. Also the storage needs to be able to efficiently add a new user and find
 * if a user is part of the storage.
 *
 * @author Dimitris
 */
public interface U2fUserStorage extends Initializable {

    /**
     * Uses the given username to search the storage for the particular user.
     *
     * @param username the id of the user to search the storage
     * @return the user from the storage
     * @throws UserDoesNotExistException if the user is not found we notify the API user with an exception
     */
    U2fUser getFromUsername(String username) throws UserDoesNotExistException;

    boolean hasUser(U2fUser user);

    void addNewUser(U2fUser user) throws UserAlreadyExistsException;
}
