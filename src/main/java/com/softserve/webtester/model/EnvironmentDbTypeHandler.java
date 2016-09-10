package com.softserve.webtester.model;

import org.apache.ibatis.type.EnumTypeHandler;

/**
 * MyBatis handler is used to retrieve the value of Java EnvironmentDbType
 * enumeration instance.
 */
public class EnvironmentDbTypeHandler extends EnumTypeHandler<EnvironmentDbType> {

    public EnvironmentDbTypeHandler(Class<EnvironmentDbType> type) {
        super(type);
    }
}
