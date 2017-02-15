package com.idshorten.controller.request;

public class CustomerRequest {
    private String url;
    private String customAlias;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCustomAlias() {
        return customAlias;
    }

    public void setCustomAlias(String customAlias) {
        this.customAlias = customAlias;
    }

    public CustomerRequest(String url, String customAlias) {
        this.url = url;
        this.customAlias = customAlias;
    }

    public CustomerRequest(String url) {
        this.url = url;
    }

    public CustomerRequest() {
    }
}
