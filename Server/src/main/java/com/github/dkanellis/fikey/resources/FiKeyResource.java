package com.github.dkanellis.fikey.resources;

import com.codahale.metrics.annotation.Timed;

import com.google.common.base.Optional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Dimitris Kanellis
 */
@Path("")
@Produces(MediaType.APPLICATION_JSON)
public class FiKeyResource {
}
