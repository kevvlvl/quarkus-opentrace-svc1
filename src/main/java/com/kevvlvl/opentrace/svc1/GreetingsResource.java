package com.kevvlvl.opentrace.svc1;

import org.jboss.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api/health")
public class GreetingsResource {

    private static final Logger LOG = Logger.getLogger(GreetingsResource.class);

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String health() {

        String message = "Hello! Health check from svc1!";

        LOG.info(message);
        return message;
    }
}