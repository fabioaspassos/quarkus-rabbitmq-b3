package com.acme.fixedIncome;

public class RequestDataMessage {
    private String cpf;
    private String referenceDate;

    public RequestDataMessage() {
    }

    public RequestDataMessage(String cpf, String referenceDate) {
        this.cpf = cpf;
        this.referenceDate = referenceDate;
    }

    public String getCpf() {
        return cpf;
    }

    public String getReferenceDate() {
        return referenceDate;
    }

    @Override
    public String toString() {
        return "MessageRequestData{" +
                "cpf='" + cpf + '\'' +
                ", referenceDate='" + referenceDate + '\'' +
                '}';
    }
}
