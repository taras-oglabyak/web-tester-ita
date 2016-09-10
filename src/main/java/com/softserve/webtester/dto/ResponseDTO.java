package com.softserve.webtester.dto;

import java.io.Serializable;

/**
 * Data transfer object class for transferring results of one request run. Includes such fields as responseTime,
 * statusLine, statusCode, reasonPhrase, responseBody.
 *
 * @author Anton Mykytiuk
 */
public class ResponseDTO implements Serializable {

    private static final long serialVersionUID = -1285748203667063594L;

    private int responseTime;
    private String statusLine;
    private int statusCode;
    private String reasonPhrase;
    private String responseBody;

    public int getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(int responseTime) {
        this.responseTime = responseTime;
    }

    public String getStatusLine() {
        return statusLine;
    }

    public void setStatusLine(String statusLine) {
        this.statusLine = statusLine;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public void setReasonPhrase(String reasonPhrase) {
        this.reasonPhrase = reasonPhrase;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

}