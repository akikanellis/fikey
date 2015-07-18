package com.github.dkanellis.fikeyserverexample.views.login;


import com.github.dkanellis.fikey.Authenticator;
import com.github.dkanellis.fikey.FiKeyAuth;
import com.github.dkanellis.fikey.exceptions.*;
import com.github.dkanellis.fikeyserverexample.utils.Statics;
import io.dropwizard.views.View;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author Dimitris Kanellis
 */
@Path("/")
@Produces(MediaType.TEXT_HTML)
public class AuthenticateResource {

    private Authenticator fiKeyAuth;

    public AuthenticateResource() {
        this.fiKeyAuth = new FiKeyAuth(Statics.APP_ID);
    }

    @Path("startDeviceAuthentication")
    @GET
    public View startDeviceAuthentication(@QueryParam("username") String username, @QueryParam("password") String password) {
        try {
            fiKeyAuth.authenticateUser(username, password);

            String authenticateRequestData = fiKeyAuth.startDeviceAuthentication(username);
            return new StartDeviceAuthenticationView(username, authenticateRequestData);
        } catch (NoEligibleDevicesException e) {
            e.printStackTrace(); // TODO add views
            return new StartDeviceAuthenticationView(e.getLocalizedMessage(), "N/A");
        } catch (UserDoesNotExistException e) {
            e.printStackTrace();
            return new StartDeviceAuthenticationView(e.getLocalizedMessage(), "N/A");
        } catch (InvalidPasswordException e) {
            e.printStackTrace();
            return new StartDeviceAuthenticationView(e.getLocalizedMessage(), "N/A");
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
        } catch (DeviceAlreadyRegisteredWithUserException e) {
            e.printStackTrace();
            return new FinishDeviceAuthenticationView(username, "", true);
        } catch (UserDoesNotExistException e) {
            e.printStackTrace();
            return new FinishDeviceAuthenticationView(username, "", true);
        }
    }
}
