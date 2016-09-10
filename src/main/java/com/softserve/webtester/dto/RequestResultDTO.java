package com.softserve.webtester.dto;

import java.io.Serializable;
import java.util.List;

import com.softserve.webtester.model.Request;

/**
 * Data transfer object class for transferring results of one request run. Also it contains Request instance
 * which represents request entity from DB and PreparedRequestDTO with prepared for running HttpRequestBase and variables list.
 * In case when we run collections with BuildVersion, list responses includes five ResponseDTO objects, else - only one.
 *
 * @author Anton Mykytiuk
 */
public class RequestResultDTO implements Serializable {

    private static final long serialVersionUID = 1321944315837013407L;

    private Request request;
    private PreparedRequestDTO preparedRequestDTO;
    private List<ResponseDTO> responses;

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public PreparedRequestDTO getPreparedRequestDTO() {
        return preparedRequestDTO;
    }

    public void setPreparedRequestDTO(PreparedRequestDTO preparedRequestDTO) {
        this.preparedRequestDTO = preparedRequestDTO;
    }

    public List<ResponseDTO> getResponses() {
        return responses;
    }

    public void setResponses(List<ResponseDTO> responses) {
        this.responses = responses;
    }
}