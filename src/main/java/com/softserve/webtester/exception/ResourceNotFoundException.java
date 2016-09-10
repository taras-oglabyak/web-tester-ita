package com.softserve.webtester.exception;

/**
 * Exception thrown when an attempt to load nonexistent data.
 * 
 * @author Taras Oglabyak
 *
 */
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -2009681498227068253L;

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }

    protected ResourceNotFoundException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}