package com.acme.integration.b3.guia;

import java.util.List;

public class DataResponse {
    private String product;
    private String referenceStartDate;

    private List<UpdatedProductResponse> updatedProducts;

    public DataResponse() {
    }

    public DataResponse(String product, String referenceStartDate, List<UpdatedProductResponse> updatedProducts) {
        this.product = product;
        this.referenceStartDate = referenceStartDate;
        this.updatedProducts = updatedProducts;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getReferenceStartDate() {
        return referenceStartDate;
    }

    public void setReferenceStartDate(String referenceStartDate) {
        this.referenceStartDate = referenceStartDate;
    }

    public List<UpdatedProductResponse> getUpdatedProducts() {
        return updatedProducts;
    }

    public void setUpdatedProducts(List<UpdatedProductResponse> updatedProducts) {
        this.updatedProducts = updatedProducts;
    }
}
