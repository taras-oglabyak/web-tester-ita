package com.softserve.webtester.dto;

import java.io.Serializable;

/**
 * DTO class using for filtering {@link RequestCollection} instances.
 * 
 * @author Yura Lubinec
 *
 */
public class RequestCollectionFilterDTO implements Serializable {

    private static final long serialVersionUID = -3108439506230131807L;

    private String RequestCollectionNameFilter;

    private int[] labelFilter;

    public String getRequestCollectionNameFilter() {
        return RequestCollectionNameFilter;
    }

    public void setRequestCollectionNameFilter(String requestCollectionNameFilter) {
        RequestCollectionNameFilter = requestCollectionNameFilter;
    }

    public int[] getLabelFilter() {
        return labelFilter;
    }

    public void setLabelFilter(int[] labelFilter) {
        this.labelFilter = labelFilter;
    } 
}
