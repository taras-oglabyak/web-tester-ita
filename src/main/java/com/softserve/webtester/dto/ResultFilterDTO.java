package com.softserve.webtester.dto;

import java.io.Serializable;

/**
 * DTO class is used for filtering {@link ResultHistory} instances
 * @author Viktor Somka
 */
public class ResultFilterDTO implements Serializable {

    private static final long serialVersionUID = -4420976248741084072L;

    private boolean statusFilter;
    private int[] applicationFilter;
    private int[] serviceFilter;
    private int runId;

    public boolean getStatusFilter() {
        return statusFilter;
    }

    public void setStatusFilter(boolean statusFilter) {
        this.statusFilter = statusFilter;
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

    public int getRunId() {
        return runId;
    }

    public void setRunId(int runId) {
        this.runId = runId;
    }
}