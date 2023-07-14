package com.acme;

import com.acme.checkdata.CheckDataService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class ExampleResource {

    @Inject
    CheckDataService checkPositionsService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(
            @QueryParam("referenceDate") String referenceDate) {

        checkPositionsService.execute(referenceDate);

        return "Hello from RESTEasy Reactive";
    }
}
