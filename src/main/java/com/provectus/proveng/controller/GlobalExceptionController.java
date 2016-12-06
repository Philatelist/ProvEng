package com.provectus.proveng.controller;

import com.provectus.proveng.exception.ErrorResponseException;
import com.provectus.proveng.utils.ResponseUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionController {

    private Logger logger = LogManager.getLogger(GlobalExceptionController.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> ExceptionHandler(Exception e) {

        logger.error(">> GlobalController Exception: ", e);
        return ResponseUtils.buildErrorResponse("restException", e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = ErrorResponseException.class)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> ErrorResponseExceptionHandler(
            ErrorResponseException e) {

        logger.error(">> GlobalController Exception: ", e);
        return ResponseUtils.buildErrorResponse(e.getErrorType(), e.getErrorMessage(),
                e.getHttpStatus());
    }

}