package com.provectus.proveng.utils;

import com.provectus.proveng.enumaration.ErrorType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FalseResult extends Exception {

    private static final Logger log = LogManager.getLogger(FalseResult.class);

    static {
        log.info("Started CreateErrorUtils");
    }

    private String errorType;
    private String errorDescription;
    private HttpStatus httpStatus;
    private ResponseEntity result;

    public FalseResult() {
    }

    public FalseResult(String errorType, String errorDescription, HttpStatus httpStatus) {
        this.result = createErrorResult(errorType, errorDescription, httpStatus);
    }

    public FalseResult(ErrorType type) {
        if (type.equals(ErrorType.AUTH_FALSE)) {
            this.result = createErrorResult("Authentication error", ErrorType.AUTH_FALSE.getDescription(), HttpStatus.UNAUTHORIZED);
        }
        if (type.equals(ErrorType.NO_ACCESS)) {
            this.result = createErrorResult("No access", ErrorType.NO_ACCESS.getDescription(), HttpStatus.FORBIDDEN);
        }
        if (type.equals(ErrorType.EMPTY_FIELDS)) {
            this.result = createErrorResult("Empty fields", ErrorType.EMPTY_FIELDS.getDescription(), HttpStatus.BAD_REQUEST);
        }
        if (type.equals(ErrorType.DOUBLE)) {
            this.result = createErrorResult("Already exist", ErrorType.DOUBLE.getDescription(), HttpStatus.BAD_REQUEST);
        }
        if (type.equals(ErrorType.NOT_IN_DB)) {
            this.result = createErrorResult("The object is absent.", ErrorType.NOT_IN_DB.getDescription(), HttpStatus.NOT_FOUND);
        }
        if (type.equals(ErrorType.NOT_IN_GROUPS)) {
            this.result = createErrorResult("You don`t have any groups.", ErrorType.NOT_IN_GROUPS.getDescription(), HttpStatus.NOT_FOUND);
        }
        if (type.equals(ErrorType.BAD_INPUT)) {
            this.result = createErrorResult("Bad request.", ErrorType.BAD_INPUT.getDescription(), HttpStatus.BAD_REQUEST);
        }
        if (type.equals(ErrorType.ALREADY_IN_GROUP)) {
            this.result = createErrorResult("Bad request.", ErrorType.ALREADY_IN_GROUP.getDescription(), HttpStatus.BAD_REQUEST);
        }
        if (type.equals(ErrorType.NO_ADD_TO_WORKSHOP)) {
            this.result = createErrorResult("Workshop is full.", ErrorType.NO_ADD_TO_WORKSHOP.getDescription(), HttpStatus.OK);
        }
    }

    public static Logger getLog() {
        return log;
    }

    public static ResponseEntity<Map<String, Object>> createErrorResult(String errorType,
                                                                        String errorDescription, HttpStatus httpStatus) {

        Map<String, Object> result = new HashMap<>();
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("code", String.valueOf(httpStatus));
        errorMap.put("type", errorType);
        errorMap.put("description", errorDescription);

        result.put("error", errorMap);

        return new ResponseEntity<>(result, httpStatus);

    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public ResponseEntity getResult() {
        return result;
    }

    public void setResult(ResponseEntity result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FalseResult that = (FalseResult) o;

        return result != null ? result.equals(that.result) : that.result == null;

    }

    @Override
    public int hashCode() {
        return result != null ? result.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "CreateError{" +
                "result=" + result +
                '}';
    }
}
