package com.github.dkanellis.fikey.storage;

import com.github.dkanellis.fikey.exceptions.UserAlreadyExistsException;
import com.github.dkanellis.fikey.exceptions.UserDoesNotExistException;

import java.util.HashSet;
import java.util.Set;

/**
 * A {@link U2fUserStorage} implementation with a Set as it's backbone to ensure unique users and great search
 * performance.
 *
 * @author Dimitris Kanellis
 */
public class Users implements U2fUserStorage {

    private static Users INSTANCE = new Users();

    private Set<U2fUser> users;


    private Users() {
    }

    public static Users getInstance() {
        return INSTANCE;
    }

    @Override
    public void init() {
        this.users = new HashSet<>();
    }

    @Override
    public U2fUser getFromUsername(String username) throws UserDoesNotExistException {
        for (U2fUser user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }

        throw new UserDoesNotExistException(username);
    }

    @Override
    public boolean hasUser(U2fUser user) {
        return users.contains(user);
    }

    @Override
    public void addNewUser(U2fUser user) throws UserAlreadyExistsException {
        if (hasUser(user)) {
            throw new UserAlreadyExistsException(user.getUsername());
        }
        users.add(user);
    }
}
