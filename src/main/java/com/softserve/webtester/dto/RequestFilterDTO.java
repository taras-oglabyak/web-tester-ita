package com.softserve.webtester.dto;

import java.io.Serializable;

/**
 * DTO class using for filtering {@link Request} instances.
 * 
 * @author Taras Oglabyak
 *
 */
public class RequestFilterDTO implements Serializable {

    private static final long serialVersionUID = 5474774865741817927L;

    private String RequestNameFilter;
    private int[] applicationFilter;
    private int[] serviceFilter;
    private int[] labelFilter;

    public String getRequestNameFilter() {
        return RequestNameFilter;
    }

    public void setRequestNameFilter(String requestNameFilter) {
        RequestNameFilter = requestNameFilter;
    }

    public int[] getApplicationFilter() {
        return applicationFilter;
    }

    public void setApplicationFilter(int[] applicationFilter) {
        this.applicationFilter = applicationFilter;
    }

    public int[] getServiceFilter() {
        return serviceFilter;
    }

    public void setServiceFilter(int[] serviceFilter) {
        this.serviceFilter = serviceFilter;
    }

    public int[] getLabelFilter() {
        return labelFilter;
    }

    public void setLabelFilter(int[] labelFilter) {
        this.labelFilter = labelFilter;
    }
}