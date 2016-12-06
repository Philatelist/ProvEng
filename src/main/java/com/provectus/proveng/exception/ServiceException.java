package com.provectus.proveng.exception;

public class ServiceException extends Exception {

    private static final long serialVersionUID = -5960950687427508705L;

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

}
