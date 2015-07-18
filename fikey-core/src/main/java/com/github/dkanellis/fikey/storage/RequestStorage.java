package com.github.dkanellis.fikey.storage;

import com.yubico.u2f.data.messages.json.Persistable;

/**
 * @author Dimitris
 */
public interface RequestStorage {
    void add(Persistable request);

    String removeAndReturnFromResponse(Persistable response);
}
