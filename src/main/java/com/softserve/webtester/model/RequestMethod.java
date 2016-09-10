package com.softserve.webtester.model;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * Enumeration of HTTP request methods can be used in the {@link Request}
 * instance.
 * 
 * @author Taras Oglabyak
 */
public enum RequestMethod {

    POST(new HttpPost()), //
    GET(new HttpGet()), //
    DELETE(new HttpDelete()); // , PUT, OPTIONS, HEAD, TRACE, CONNECT, PATCH;

    private HttpRequestBase request;

    private RequestMethod(HttpRequestBase request) {
        this.request = request;
    }

    public HttpRequestBase getHttpRequest() {
        return this.request;
    }
}