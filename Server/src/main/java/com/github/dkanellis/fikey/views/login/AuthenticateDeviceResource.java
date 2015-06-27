package com.github.dkanellis.fikey.views.login;


import com.github.dkanellis.fikey.api.Authenticator;
import com.github.dkanellis.fikey.api.FiKeyAuth;
import com.github.dkanellis.fikey.utils.Statics;
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

    private Authenticator fiKeyAuth;

    public AuthenticateDeviceResource() {
        this.fiKeyAuth = new FiKeyAuth(Statics.APP_ID);
    }

    @Path("startDeviceAuthentication")
    @GET
    public View startDeviceAuthentication(@QueryParam("username") String username, @QueryParam("password") String password) {
        try {
            String authenticateRequestData = fiKeyAuth.startDeviceAuthentication(username, password);
            return new StartDeviceAuthenticationView(username, authenticateRequestData);
        } catch (NoEligableDevicesException e) {
            e.printStackTrace(); // TODO add views
            return new StartDeviceAuthenticationView(username, "N/A");
        }
    }

    @Path("finishDeviceAuthentication")
    @POST
    public View finishDeviceAuthentication(@FormParam("tokenResponse") String response,
                                           @FormParam("username") String username) {
        try {
            String loginInfo = fiKeyAuth.finishDeviceAuthentication(response, username);
            return new FinishDeviceAuthenticationView(username, loginInfo, false);
        } catch (DeviceCompromisedException e) {
            e.printStackTrace();
            String loginInfo = e.getDeviceRegistration().toString();
            return new FinishDeviceAuthenticationView(username, loginInfo, true);
        }
    }
}
