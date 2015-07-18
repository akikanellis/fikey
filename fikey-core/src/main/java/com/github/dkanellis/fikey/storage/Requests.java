package com.github.dkanellis.fikey.storage;

import com.yubico.u2f.data.messages.json.Persistable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Dimitris
 */
public class Requests implements U2fRequestStorage {

    private static Requests INSTANCE = new Requests();

    Map<String, String> requests;

    private Requests() {
    }

    public static Requests getInstance() {
        return INSTANCE;
    }

    public void init() {
        this.requests = new HashMap<>();
    }

    @Override
    public void addNewRequest(Persistable request) {
        String key = request.getRequestId();
        String value = request.toJson();

        requests.put(key, value);
    }

    @Override
    public String removeAndReturnFromResponse(Persistable response) {
        String keyToRemove = response.getRequestId();

        return requests.remove(keyToRemove);
    }
}
