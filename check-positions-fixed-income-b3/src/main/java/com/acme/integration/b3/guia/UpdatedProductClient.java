package com.acme.integration.b3.guia;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class UpdatedProductClient {
    @Inject
    @RestClient
    IUpdatedProductClient guiaClientB3;

    public GuiaResponse getUpdatedProduct(String referenceDate, String product, String page){
        Response response;
        response = guiaClientB3.getUpdatedProduct(referenceDate, product, page);

        int code = response.getStatus();
        if (code == Response.Status.OK.getStatusCode()) {
            return response.readEntity(GuiaResponse.class);
        }

        return null;
    }
}
