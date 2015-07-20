package com.github.dkanellis.fikey.storage;

import com.yubico.u2f.data.messages.json.Persistable;

/**
 * An interface which declares the functionality of a storage which will store and pop all the requests from U2F
 * devices. A storage like that must be able to store a request in an efficient way and remove it when it is needed
 * by using the appropriate response of the device. The request and response should be provided through the FIDO U2F
 * Javascript API.
 *
 * @author Dimitris
 */
public interface U2fRequestStorage extends Initializable {

    void addNewRequest(Persistable request);

    String removeAndReturnFromResponse(Persistable response);
}
