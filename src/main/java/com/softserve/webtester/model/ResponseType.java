package com.softserve.webtester.model;

/**
 * Enumeration of the expected response types can be returned by the {@link Request}.
 * 
 * @author Taras Oglabyak
 */
public enum ResponseType {

    XML("XML"), JSON("JSON");

    private String textValue;

    private ResponseType(String textValue) {
        this.textValue = textValue;
    }

    public String getTextValue() {
        return textValue;
    }
}