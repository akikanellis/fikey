package com.github.dkanellis.fikeyserverexample.views.login;


import com.github.dkanellis.fikey.Authenticator;
import com.github.dkanellis.fikey.FiKeyAuth;
import com.github.dkanellis.fikey.exceptions.DeviceCompromisedException;
import com.github.dkanellis.fikey.exceptions.InvalidPasswordException;
import com.github.dkanellis.fikey.exceptions.NoEligibleDevicesException;
import com.github.dkanellis.fikey.exceptions.UserDoesNotExistException;
import com.github.dkanellis.fikeyserverexample.FiKeyApplication;
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
        this.fiKeyAuth = new FiKeyAuth(FiKeyApplication.APP_ID);
    }

    @Path("startAuthentication")
    @GET
    public View startRegistration(@QueryParam("username") String username, @QueryParam("password") String password) {
        try {
            fiKeyAuth.authenticateUser(username, password);
            return new StartAuthenticationView(username);
        } catch (UserDoesNotExistException e) {
            return new AuthenticationFailedView(username, e);
        } catch (InvalidPasswordException e) {
            return new AuthenticationFailedView(username, e);
        }
    }

    @Path("startDeviceAuthentication")
    @GET
    public View startDeviceAuthentication(@QueryParam("username") String username) {
        try {
            String authenticateRequestData = fiKeyAuth.startDeviceAuthentication(username);
            return new StartDeviceAuthenticationView(username, authenticateRequestData);
        } catch (NoEligibleDevicesException e) {
            return new AuthenticationFailedView(username, e);
        } catch (UserDoesNotExistException e) {
            return new AuthenticationFailedView(username, e);
        }
    }

    @Path("finishDeviceAuthentication")
    @POST
    public View finishDeviceAuthentication(@FormParam("tokenResponse") String response,
                                           @FormParam("username") String username) {
        try {
            String loginInfo = fiKeyAuth.finishDeviceAuthentication(response, username);
            return new FinishDeviceAuthenticationView(username, loginInfo, false);
        } catch (UserDoesNotExistException e) {
            return new AuthenticationFailedView(username, e);
        } catch (DeviceCompromisedException e) {
            return new AuthenticationFailedView(username, e);
        }
    }
}
