package com.softserve.webtester.model;

/**
 * Enumeration of the response time type for generating statistic reports.
 * 
 * @author Yura Lubinec
 */
public enum ResponseTimeType {

    AVERAGE ("With average response time"), MAX ("With maximum response time");
    
    private String text;

    private ResponseTimeType(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
