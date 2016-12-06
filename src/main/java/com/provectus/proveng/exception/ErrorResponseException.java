package com.provectus.proveng.exception;

import org.springframework.http.HttpStatus;

public class ErrorResponseException extends RuntimeException {

    private static final long serialVersionUID = 1181072556865534183L;
    private String errorType;
    private String errorMessage;
    private HttpStatus httpStatus;

    public ErrorResponseException(String errorType, String errorMessage, HttpStatus httpStatus) {
        super();
        this.errorType = errorType;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public String toString() {
        return "ErrorResponseException [errorType=" + errorType + ", errorMessage=" + errorMessage
                + ", httpStatus=" + httpStatus + "]";
    }


}
