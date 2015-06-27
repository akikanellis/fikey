package com.github.dkanellis.fikey.views.login;


import com.github.dkanellis.fikey.api.storage.DataStorage;
import com.github.dkanellis.fikey.utils.Statics;
import com.yubico.u2f.U2F;
import com.yubico.u2f.data.DeviceRegistration;
import com.yubico.u2f.data.messages.AuthenticateRequestData;
import com.yubico.u2f.data.messages.AuthenticateResponse;
import com.yubico.u2f.exceptions.DeviceCompromisedException;
import com.yubico.u2f.exceptions.NoEligableDevicesException;
import io.dropwizard.views.View;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author Dimitris Kanellis
 */
@Path("/")
@Produces(MediaType.TEXT_HTML)
public class AuthenticateDeviceResource {

    private final DataStorage storage;
    private U2F u2fManager;

    public AuthenticateDeviceResource() {
        this.storage = DataStorage.getInstance();
        this.u2fManager = new U2F();
    }

    @Path("startDeviceAuthentication")
    @GET
    public View startDeviceAuthentication(@QueryParam("username") String username, @QueryParam("password") String password) throws NoEligableDevicesException {
        AuthenticateRequestData authenticateRequestData = u2fManager.startAuthentication(Statics.APP_ID, storage.getDevicesFromUser(username));
        storage.addRequest(authenticateRequestData.getRequestId(), authenticateRequestData.toJson());
        return new StartDeviceAuthenticationView(username, authenticateRequestData.toJson());
    }

    @Path("finishDeviceAuthentication")
    @POST
    public View finishDeviceAuthentication(@FormParam("tokenResponse") String response,
                                           @FormParam("username") String username) {
        AuthenticateResponse authenticateResponse = AuthenticateResponse.fromJson(response);
        AuthenticateRequestData authenticateRequest = AuthenticateRequestData.fromJson(storage.removeRequest(authenticateResponse.getRequestId()));
        DeviceRegistration registration;
        try {
            registration = u2fManager.finishAuthentication(authenticateRequest, authenticateResponse, storage.getDevicesFromUser(username));
            storage.addDeviceToUser(username, registration.getKeyHandle(), registration.toJson());
            return new FinishDeviceAuthenticationView(username, registration);
        } catch (DeviceCompromisedException e) {
            registration = e.getDeviceRegistration();
            storage.addDeviceToUser(username, registration.getKeyHandle(), registration.toJson());
            return new FinishDeviceAuthenticationView(username, registration);
        }
    }
}
