package com.acme.integration.b3.guia;

public class UpdatedProductResponse {
    private String documentNumber;

    public UpdatedProductResponse() {
    }

    public UpdatedProductResponse(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }
}
