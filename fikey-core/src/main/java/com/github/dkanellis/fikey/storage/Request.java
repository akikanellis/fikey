package com.github.dkanellis.fikey.storage;

import com.yubico.u2f.data.messages.AuthenticateRequestData;
import com.yubico.u2f.data.messages.RegisterRequestData;
import com.yubico.u2f.data.messages.json.Persistable;

/**
 * @author Dimitris
 */
public class Request implements Persistable {

    Persistable request;

    public Request(Persistable request) {
        this.request = request;
    }

    public RegisterRequestData toRegisterRequestData() {
        return RegisterRequestData.fromJson(request.toJson());
    }

    public AuthenticateRequestData toAuthenticateRequestData() {
        return AuthenticateRequestData.fromJson(request.toJson());
    }

    @Override
    public String getRequestId() {
        return request.getRequestId();
    }

    @Override
    public String toJson() {
        return request.toJson();
    }

    @Override
    public int hashCode() {
        return request.getRequestId().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Request)) {
            return false;
        }
        Request other = (Request) o;

        return request.getRequestId().equals(other.getRequestId());
    }
}
