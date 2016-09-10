package com.softserve.webtester.dto;

import java.io.Serializable;

/**
 * DTO class is used for filtering {@link ResultHistory} instances
 * @author Viktor Somka
 */
public class ResultCollectionFilterDTO implements Serializable {

    private static final long serialVersionUID = -3133417511946682972L;

    private boolean statusFilter;
    private int[] buildVersionsFilter;
    private int[] labelFilter;
    private int runId;

    public boolean getStatusFilter() {
        return statusFilter;
    }

    public void setStatusFilter(boolean statusFilter) {
        this.statusFilter = statusFilter;
    }

    public int[] getBuildVersionsFilter() {
        return buildVersionsFilter;
    }

    public void setBuildVersionsFilter(int[] buildVersionsFilter) {
        this.buildVersionsFilter = buildVersionsFilter;
    }

    public int[] getLabelFilter() {
        return labelFilter;
    }

    public void setLabelFilter(int[] labelFilter) {
        this.labelFilter = labelFilter;
    }

    public int getRunId() {
        return runId;
    }

    public void setRunId(int runId) {
        this.runId = runId;
    }
}