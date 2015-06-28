package com.github.dkanellis.fikeyserverexample.views.register;


import com.github.dkanellis.fikey.Authenticator;
import com.github.dkanellis.fikey.FiKeyAuth;
import com.github.dkanellis.fikey.exceptions.InvalidPasswordException;
import com.github.dkanellis.fikey.exceptions.UserAlreadyExistsException;
import com.github.dkanellis.fikeyserverexample.utils.Statics;
import io.dropwizard.views.View;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author Dimitris Kanellis
 */
@Path("/")
@Produces(MediaType.TEXT_HTML)
public class RegisterDeviceResource {

    private Authenticator fiKeyAuth;

    public RegisterDeviceResource() {
        this.fiKeyAuth = new FiKeyAuth(Statics.APP_ID);
    }


    @Path("startDeviceRegistration")
    @GET
    public View startDeviceRegistration(@QueryParam("username") String username, @QueryParam("password") String password) {
        try {
            String request = fiKeyAuth.startDeviceRegistration(username, password);
            return new StartDeviceRegistrationView(username, request);
        } catch (UserAlreadyExistsException e) {
            e.printStackTrace(); // TODO show different view
            return new StartDeviceRegistrationView(username, "N/A");
        } catch (InvalidPasswordException e) {
            e.printStackTrace(); // TODO show different view
            return new StartDeviceRegistrationView(username, "N/A");
        }
    }

    @Path("finishDeviceRegistration")
    @POST
    public View finishDeviceRegistration(@FormParam("tokenResponse") String response,
                                         @FormParam("username") String username) {
        String registrationInfo = fiKeyAuth.finishDeviceRegistration(response, username);
        return new FinishDeviceRegistrationView(username, registrationInfo);
    }
}
