package com.github.dkanellis.fikey.views;


import com.github.dkanellis.fikey.storage.DataStorage;
import com.yubico.u2f.U2F;
import com.yubico.u2f.data.DeviceRegistration;
import com.yubico.u2f.data.messages.RegisterRequestData;
import com.yubico.u2f.data.messages.RegisterResponse;
import io.dropwizard.views.View;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author Dimitris Kanellis
 */
@Path("/")
@Produces(MediaType.TEXT_HTML)
public class RegisterDeviceResource {

    public static final String APP_ID = "https://localhost:8080";

    private final static String SUCCESSFUL_REGISTRATION_MESSAGE = "Successfully registered device: <pre>%s</pre>";

    private final DataStorage<String, String, String> storage;
    private U2F u2fManager;

    public RegisterDeviceResource() {
        this.storage = new DataStorage<>();
        this.u2fManager = new U2F();
    }

    @Path("startDeviceRegistration")
    @GET
    public View startDeviceRegistration(@QueryParam("username") String username, @QueryParam("password") String password) {
        RegisterRequestData registerRequest = u2fManager.startRegistration(APP_ID, storage.getDevicesFromUser(username));
        storage.addRequest(registerRequest.getRequestId(), registerRequest.toJson());
        return new RegisterDeviceView(username, registerRequest.toJson());
    }

    @Path("finishDeviceRegistration")
    @POST
    public String finishRegistration(@FormParam("tokenResponse") String response, @FormParam("username") String username) {
        RegisterResponse registerResponse = RegisterResponse.fromJson(response);
        RegisterRequestData registerRequest
                = RegisterRequestData.fromJson(storage.removeRequest(registerResponse.getRequestId()));
        DeviceRegistration registration = u2fManager.finishRegistration(registerRequest, registerResponse);
        storage.addDeviceToUser(username, registration.getKeyHandle(), registration.toJson());
        return String.format(SUCCESSFUL_REGISTRATION_MESSAGE, registration);
    }
}
