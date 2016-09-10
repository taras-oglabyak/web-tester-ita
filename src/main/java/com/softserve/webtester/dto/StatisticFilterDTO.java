package com.softserve.webtester.dto;

import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.Size;
import com.softserve.webtester.model.BuildVersion;
import com.softserve.webtester.model.ResponseTimeType;

/**
 * DTO class with data for selecting statistic info for generating reports.
 * 
 * @author Yura Lubinec
 *
 */
public class StatisticFilterDTO implements Serializable {

    private static final long serialVersionUID = -8426535580134914033L;

    @Size(min = 1)
    private int[] serviceId;

    @Size(min = 1, max = 5)
    private int[] buildVersionId;

    private ResponseTimeType responseTimeFilterMarker;
    private List<BuildVersion> buildVersions;

    public int[] getServiceId() {
        return serviceId;
    }

    public void setServiceId(int[] serviceId) {
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

    public List<BuildVersion> getBuildVersions() {
        return buildVersions;
    }

    public void setBuildVersions(List<BuildVersion> buildVersions) {
        this.buildVersions = buildVersions;
    }
}