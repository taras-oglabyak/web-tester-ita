package com.softserve.webtester.model;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotBlank;

/**
 * The Variable class represents {@code Variable} entity stored in the database.
 * 
 * @author Taras Oglabyak
 */
public class Variable implements Serializable {

    private static final long serialVersionUID = 3879025473184231929L;

    private int id;

    @NotBlank
    @Size(max = 50)
    private String name;
    private String value;
    private boolean sql;
    private boolean random;
    private VariableDataType dataType;
    private Integer length;
    private Request request;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isSql() {
        return sql;
    }

    public void setSql(boolean sql) {
        this.sql = sql;
    }

    public boolean isRandom() {
        return random;
    }

    public void setRandom(boolean random) {
        this.random = random;
    }

    public VariableDataType getDataType() {
        return dataType;
    }

    public void setDataType(VariableDataType dataType) {
        this.dataType = dataType;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, true);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}