package com.acme.checkdata;

public class CheckDataMessageRequest {
    private String referenceDate;
    private Integer page;

    public CheckDataMessageRequest() {
    }

    public CheckDataMessageRequest(String referenceDate, Integer page) {
        this.referenceDate = referenceDate;
        this.page = page;
    }

    public String getReferenceDate() {
        return referenceDate;
    }

    public Integer getPage() {
        return page;
    }

    @Override
    public String toString() {
        return "MessageRequestDocuments{" +
                "referenceDate='" + referenceDate + '\'' +
                ", page='" + page + '\'' +
                '}';
    }
}
