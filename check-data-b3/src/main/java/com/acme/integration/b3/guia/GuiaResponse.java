package com.acme.integration.b3.guia;

public class GuiaResponse {
    private LinksResponse links;
    private DataResponse data;

    public LinksResponse getLinks() {
        return links;
    }

    public void setLinks(LinksResponse links) {
        this.links = links;
    }

    public DataResponse getData() {
        return data;
    }

    public void setData(DataResponse data) {
        this.data = data;
    }
}
