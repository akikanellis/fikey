package com.github.dkanellis.fikey.storage;

import com.yubico.u2f.data.messages.json.Persistable;

/**
 * @author Dimitris
 */
public interface U2fRequestStorage {

    void addNewRequest(Persistable request);

    String removeAndReturnFromResponse(Persistable response);
}
