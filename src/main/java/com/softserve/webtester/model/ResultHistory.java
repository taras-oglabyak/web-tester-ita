package com.softserve.webtester.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * ResultHistory class representing DB object
 * @author Viktor Syomka
 */

public class ResultHistory implements Serializable {

    private static final long serialVersionUID = 2316323598058491701L;

    private int id;
    private boolean status;
    private Application application;
    private Service service;
    private Request request;
    private String requestName;
    private String requestDescription;
    private String url;
    private String responseType;
    private String requestBody;
    private String statusLine;
    private Timestamp timeStart;
    private int expectedResponseTime;
    private int responseTime;
    private String expectedResponse;
    private String actualResponse;
    private String message;
    private int runId;
    private RequestCollection requestCollection;
    private BuildVersion buildVersion;
    private List<Label> labels;
    private List<HeaderHistory> headerHistories;
    private List<DbValidationHistory> dbValidationHistories;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public String getRequestName() {
        return requestName;
    }

    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }

    public String getRequestDescription() {
        return requestDescription;
    }

    public void setRequestDescription(String requestDescription) {
        this.requestDescription = requestDescription;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getStatusLine() {
        return statusLine;
    }

    public void setStatusLine(String statusLine) {
        this.statusLine = statusLine;
    }

    public Timestamp getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Timestamp timeStart) {
        this.timeStart = timeStart;
    }

    public int getExpectedResponseTime() {
        return expectedResponseTime;
    }

    public void setExpectedResponseTime(int expectedResponseTime) {
        this.expectedResponseTime = expectedResponseTime;
    }

    public int getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(int responseTime) {
        this.responseTime = responseTime;
    }

    public String getExpectedResponse() {
        return expectedResponse;
    }

    public void setExpectedResponse(String expectedResponse) {
        this.expectedResponse = expectedResponse;
    }

    public String getActualResponse() {
        return actualResponse;
    }

    public void setActualResponse(String actualResponse) {
        this.actualResponse = actualResponse;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getRunId() {
        return runId;
    }

    public void setRunId(int runId) {
        this.runId = runId;
    }

    public RequestCollection getRequestCollection() {
        return requestCollection;
    }

    public void setRequestCollection(RequestCollection requestCollection) {
        this.requestCollection = requestCollection;
    }

    public BuildVersion getBuildVersion() {
        return buildVersion;
    }

    public void setBuildVersion(BuildVersion buildVersion) {
        this.buildVersion = buildVersion;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    public List<HeaderHistory> getHeaderHistories() {
        return headerHistories;
    }

    public void setHeaderHistories(List<HeaderHistory> headerHistories) {
        this.headerHistories = headerHistories;
    }

    public List<DbValidationHistory> getDbValidationHistories() {
        return dbValidationHistories;
    }

    public void setDbValidationHistories(List<DbValidationHistory> dbValidationHistories) {
        this.dbValidationHistories = dbValidationHistories;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, true);
    }
}
