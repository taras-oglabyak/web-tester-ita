package com.softserve.webtester.dto;

import java.io.Serializable;
import java.util.List;

import org.apache.http.client.methods.HttpRequestBase;

import com.softserve.webtester.model.Variable;

/**
 * Data transfer object class for transferring prepared for executing HttpRequestBase, 
 * if exist generated variables list and prepared request body
 */
public class PreparedRequestDTO implements Serializable {

    private static final long serialVersionUID = -105517229236219065L;

    private HttpRequestBase httpRequest;
    private List<Variable> variableList;
    private String preparedRequestBody;

    public HttpRequestBase getHttpRequest() {
        return httpRequest;
    }

    public void setHttpRequest(HttpRequestBase httpRequest) {
        this.httpRequest = httpRequest;
    }

    public List<Variable> getVariableList() {
        return variableList;
    }

    public void setVariableList(List<Variable> variableList) {
        this.variableList = variableList;
    }

    public String getPreparedRequestBody() {
        return preparedRequestBody;
    }

    public void setPreparedRequestBody(String preparedRequestBody) {
        this.preparedRequestBody = preparedRequestBody;
    }
}