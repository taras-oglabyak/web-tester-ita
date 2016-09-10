package com.softserve.webtester.model;

import org.apache.ibatis.type.EnumTypeHandler;

/**
 * MyBatis handler is used to retrieve the value of Java {@link ResponseType} enumeration instance.
 * 
 * @author Taras Oglabyak
 */
public class ResponseTypeHandler extends EnumTypeHandler<ResponseType> {

    public ResponseTypeHandler(Class<ResponseType> type) {
        super(type);
    }
}