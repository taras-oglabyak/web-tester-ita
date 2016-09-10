package com.softserve.webtester.dto;

import com.softserve.webtester.model.ResponseTimeType;

import java.io.Serializable;

/**
 * DTO class is used for filtering {@link ResultHistory} instances for graphic building
 * @author Viktor Somka
 */
public class ReportFilterDTO implements Serializable {

    private static final long serialVersionUID = -358128122616172034L;

    private int serviceId;
    private int [] buildVersionId;
    private ResponseTimeType responseTimeFilterMarker;

    public ReportFilterDTO() {
    }

    public ReportFilterDTO(int serviceId, int[] buildVersionId, ResponseTimeType responseTimeFilterMarker) {
        this.serviceId = serviceId;
        this.buildVersionId = buildVersionId;
        this.responseTimeFilterMarker = responseTimeFilterMarker;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int[] getBuildVersionId() {
        return buildVersionId;
    }

    public void setBuildVersionId(int[] buildVersionId) {
        this.buildVersionId = buildVersionId;
    }

    public ResponseTimeType getResponseTimeFilterMarker() {
        return responseTimeFilterMarker;
    }

    public void setResponseTimeFilterMarker(ResponseTimeType responseTimeFilterMarker) {
        this.responseTimeFilterMarker = responseTimeFilterMarker;
    }
}