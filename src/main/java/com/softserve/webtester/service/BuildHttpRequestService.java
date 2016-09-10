package com.softserve.webtester.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softserve.webtester.dto.PreparedRequestDTO;
import com.softserve.webtester.model.Environment;
import com.softserve.webtester.model.Header;
import com.softserve.webtester.model.Request;
import com.softserve.webtester.model.Variable;
import com.softserve.webtester.model.VariableDataType;

/**
 * BuildHttpRequestService class allows create http requests based on {@link Request}
 * and {@link Environment} instances
 */
@Service
public class BuildHttpRequestService {

    @Autowired
    private RequestExecuteSupportService requestExecuteSupportService;

    /**
     * Create instance PreparedRequestDTO which contains instance of HttpRequest,
     * list of generated {@link Variable} instances and prepared request body
     * 
     * @param request {@link Request} instance for preparing to execute
     * @param environment {@link Environment} instance for preparing to execute request
     * @throws Exception
     */
    public PreparedRequestDTO getHttpRequest(Request request, Environment environment) throws Exception {

        PreparedRequestDTO preparedRequestDTO = new PreparedRequestDTO();
        HttpRequestBase httpRequest = request.getRequestMethod().getHttpRequest();

        URI uri = new URIBuilder(environment.getBaseUrl() + request.getEndpoint()).build();
        httpRequest.setURI(uri);

        if (request.getHeaders() != null) {
            for (Header header : request.getHeaders()) {
                httpRequest.setHeader(header.getName(), header.getValue());
            }
        }
        
        List<Variable> variableList = null;
        if (CollectionUtils.isNotEmpty(request.getVariables())) {
            variableList = getListVarables(request, environment);
            preparedRequestDTO.setVariableList(variableList);
        }
        
        if (StringUtils.isNotEmpty(request.getRequestBody())) {
            String preparedRequestBody = request.getRequestBody();
            if (!CollectionUtils.isEmpty(variableList)) {
                preparedRequestBody = requestExecuteSupportService.getEvaluatedString(request.getRequestBody(),
                        variableList, "Request body");
            }
            HttpEntity entity = new StringEntity(preparedRequestBody);
            preparedRequestDTO.setPreparedRequestBody(preparedRequestBody);
            
            HttpEntityEnclosingRequestBase httpEntityEnclosingRequest = (HttpEntityEnclosingRequestBase) httpRequest;
            httpEntityEnclosingRequest.setEntity(entity);
            preparedRequestDTO.setHttpRequest(httpEntityEnclosingRequest);
            return preparedRequestDTO;
        }
        preparedRequestDTO.setHttpRequest(httpRequest);
        return preparedRequestDTO;

    }

    /**
     * Provides generate random String of a given length
     */
    private String getRandomString(Variable variable, int length) {
        String randomString = null;
        randomString = variable.getDataType().getRandomStream(length)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
        if (variable.getDataType() == VariableDataType.DIGIT_FLOAT) {
            randomString = new StringBuilder(randomString).insert(randomString.length() - 2, ".").toString();
        }
        return randomString;
    }

    /**
     * Provides list instances of Variable for later use in building HttpRequest
     * body or expected HttpResponse body or SQL queries for DB validation
     * 
     * @throws Exception
     */
    private List<Variable> getListVarables(Request request, Environment environment) throws Exception {
        List<Variable> variableList = new ArrayList<Variable>();
        for (Variable variable : request.getVariables()) {
            if (variable.isSql()) {
                String variableValue = requestExecuteSupportService.getExecutedQueryValue(environment,
                        variable.getValue());
                variable.setValue(variableValue);
                variableList.add(variable);
            } else if (variable.isRandom()) {
                String variableValue = variable.getValue() + getRandomString(variable, variable.getLength());
                variable.setValue(variableValue);
                variableList.add(variable);
            } else {
                variableList.add(variable);
            }
        }
        return variableList;
    }
}
