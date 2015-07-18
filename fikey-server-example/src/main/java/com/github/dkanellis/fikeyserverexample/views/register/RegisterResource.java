package com.github.dkanellis.fikeyserverexample.views.register;


import com.github.dkanellis.fikey.Authenticator;
import com.github.dkanellis.fikey.FiKeyAuth;
import com.github.dkanellis.fikey.exceptions.DeviceAlreadyRegisteredWithUserException;
import com.github.dkanellis.fikey.exceptions.InvalidPasswordException;
import com.github.dkanellis.fikey.exceptions.UserAlreadyExistsException;
import com.github.dkanellis.fikey.exceptions.UserDoesNotExistException;
import com.github.dkanellis.fikeyserverexample.utils.Statics;
import io.dropwizard.views.View;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author Dimitris Kanellis
 */
@Path("/")
@Produces(MediaType.TEXT_HTML)
public class RegisterResource {

    private Authenticator fiKeyAuth;

    public RegisterResource() {
        this.fiKeyAuth = new FiKeyAuth(Statics.APP_ID);
    }

    @Path("startRegistration")
    @GET
    public View startRegistration(@QueryParam("username") String username, @QueryParam("password") String password) {
        try {
            fiKeyAuth.registerNewUser(username, password);
            return new StartRegistrationView(username);
        } catch (UserAlreadyExistsException e) {
            return new StartRegistrationView(username, e.getLocalizedMessage());
        } catch (InvalidPasswordException e) {
            return new StartRegistrationView(username, e.getLocalizedMessage());
        }
    }


    @Path("startDeviceRegistration")
    @GET
    public View startDeviceRegistration(@QueryParam("username") String username) {
        try {
            String request = fiKeyAuth.startDeviceRegistration(username);
            return new StartDeviceRegistrationView(username, request);
        } catch (UserAlreadyExistsException e) {
            e.printStackTrace(); // TODO show different view
            return new StartDeviceRegistrationView(username, "N/A");
        } catch (InvalidPasswordException e) {
            e.printStackTrace(); // TODO show different view
            return new StartDeviceRegistrationView(username, "N/A");
        } catch (UserDoesNotExistException e) {
            e.printStackTrace();
            return new StartDeviceRegistrationView(username, "N/A");
        }
    }

    @Path("finishDeviceRegistration")
    @POST
    public View finishRegistration(@FormParam("tokenResponse") String response,
                                   @FormParam("username") String username) {
        String registrationInfo;
        try {
            registrationInfo = fiKeyAuth.finishDeviceRegistration(response, username);
            return new FinishDeviceRegistrationView(username, registrationInfo);
        } catch (UserDoesNotExistException e) {
            e.printStackTrace();
            return new FinishDeviceRegistrationView(username, "");
        } catch (DeviceAlreadyRegisteredWithUserException e) {
            e.printStackTrace();
            return new FinishDeviceRegistrationView(username, "");
        }
    }
}
