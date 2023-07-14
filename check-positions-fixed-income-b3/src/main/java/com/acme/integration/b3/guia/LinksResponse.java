package com.acme.integration.b3.guia;

public class LinksResponse {

    private String self;
    private String first;
    private String next;
    private String prev;
    private String last;

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrev() {
        return prev;
    }

    public void setPrev(String prev) {
        this.prev = prev;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public Integer lastPage() {
        return Integer.parseInt(
                this.getLast().substring(
                        this.getLast().indexOf("page") + 5));
    }
}
