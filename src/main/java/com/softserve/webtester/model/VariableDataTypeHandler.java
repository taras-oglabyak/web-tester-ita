package com.softserve.webtester.model;

import org.apache.ibatis.type.EnumTypeHandler;

/**
 * MyBatis handler is used to retrieve the value of Java {@link VariableDataType} enumeration instance.
 * 
 * @author Taras Oglabyak
 */
public class VariableDataTypeHandler extends EnumTypeHandler<VariableDataType> {

    public VariableDataTypeHandler(Class<VariableDataType> type) {
        super(type);
    }
}