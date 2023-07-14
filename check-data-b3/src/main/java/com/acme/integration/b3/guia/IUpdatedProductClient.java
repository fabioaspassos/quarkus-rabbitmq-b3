package com.acme.integration.b3.guia;

import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "APIGuiaB3")
@Singleton
public interface IUpdatedProductClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/api/updated-product/v1/investors")
    Response getUpdatedProduct(
            @QueryParam("referenceDate") String referenceDate,
            @QueryParam("product") String product,
            @QueryParam("page") String page
    );
}
