package com.idshorten.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class ShortenURL {

    @NotNull @Column(name="url") private String url;
    @NotNull @Column(name="customAlias") private String customAlias;
    @NotNull @Id @Column(name="shorten") private String shortenedURL;
    @Column private Long requestTime;

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

    public String getShortenedURL() {
        return shortenedURL;
    }

    public void setShortenedURL(String shortenedURL) {
        this.shortenedURL = shortenedURL;
    }

    public Long getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Long requestTime) {
        this.requestTime = requestTime;
    }

    public ShortenURL(String url, String customAlias, String shortenedURL, Long requestTime) {
        this.url = url;
        this.customAlias = customAlias;
        this.shortenedURL = shortenedURL;
        this.requestTime = requestTime;
    }

    public ShortenURL() {
    }
}
