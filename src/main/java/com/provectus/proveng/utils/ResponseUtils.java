package com.provectus.proveng.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtils {

    public static Logger logger = LogManager.getLogger(ResponseUtils.class);

    /**
     * @param e
     * @return
     */
    public static ResponseEntity<Map<String, Object>> buildSuccessfulResponse(Object result) {

        logger.debug(">>> into buildSucessfulResponse()");
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("result", result);
        logger.debug(">> response = " + responseMap);
        logger.debug("<<< out of buildSuccessResponse()");
        return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.OK);

    }

    public static ResponseEntity<Map<String, Object>> buildSuccessfulResponse(Object result,
                                                                              HttpStatus httpStatus) {

        logger.debug(">>> into buildSucessfulResponse()");
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("result", result);
        logger.debug(">> response = " + responseMap);
        logger.debug("<<< out of buildSuccessResponse()");
        return new ResponseEntity<Map<String, Object>>(responseMap, httpStatus);

    }

    public static ResponseEntity<Map<String, Object>> buildErrorResponse(String errorType,
                                                                         String errorDescription, HttpStatus httpStatus) {

        logger.debug(">>> buildErrorResponse");

        Map<String, Object> responseParams = new HashMap<>();
        // Map<String, String> errorMap =new HashMap<>();
        // errorMap.put(errorType, errorDescription);

        responseParams.put("error",
                new ErrorResponse(httpStatus.value(), errorType, errorDescription));

        logger.debug("<<< buildErrorResponse");
        return new ResponseEntity<Map<String, Object>>(responseParams, httpStatus);

    }

    private static class ErrorResponse {

        private int code;
        private String type;
        private String description;

        public ErrorResponse(int code, String type, String description) {
            super();
            this.code = code;
            this.type = type;
            this.description = description;
        }

        @SuppressWarnings("unused")
        public int getCode() {
            return code;
        }

        @SuppressWarnings("unused")
        public void setCode(int code) {
            this.code = code;
        }

        @SuppressWarnings("unused")
        public String getType() {
            return type;
        }

        @SuppressWarnings("unused")
        public void setType(String type) {
            this.type = type;
        }

        @SuppressWarnings("unused")
        public String getDescription() {
            return description;
        }

        @SuppressWarnings("unused")
        public void setDescription(String description) {
            this.description = description;
        }

    }
}