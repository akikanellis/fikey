package com.github.dkanellis.fikey.views;

import com.google.common.cache.LoadingCache;
import com.yubico.u2f.U2F;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Map;

/**
 * @author Dimitris Kanellis
 */
@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class FiKeyResource {

    public static final String APP_ID = "https://localhost:8080";

    private final static Logger log = LoggerFactory.getLogger(FiKeyResource.class);

    private Map<String, String> requests;
    private LoadingCache<String, Map<String, String>> users;
    private U2F u2fManager;

    @Path("startRegistration")
    @GET
    public String startRegistration(@QueryParam("username") String username, @QueryParam("password") String password) {
        log.info("Username: " + username + "Password: " + password);

        return null;
    }
}
