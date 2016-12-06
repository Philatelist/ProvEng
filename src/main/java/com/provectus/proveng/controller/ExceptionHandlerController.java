package com.provectus.proveng.controller;

import com.provectus.proveng.utils.FalseResult;
import org.jboss.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@ControllerAdvice
public class ExceptionHandlerController {

    private static final Logger log = Logger.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public
    @ResponseBody
    ResponseEntity<Map<String, Object>> missingServletRequestParameterException(Exception e) {
        log.error(">> Error: " + e.getMessage(), e);
        return FalseResult.createErrorResult("RestException", "Missing some fields: " + e.getMessage(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NumberFormatException.class)
    public
    @ResponseBody
    ResponseEntity<Map<String, Object>> numberFormatException(Exception e) {
        log.error(">> Error: " + e.getMessage(), e);
        return FalseResult.createErrorResult("RestException", "Wrong input format: " + e.getMessage(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ServletRequestBindingException.class)
    public
    @ResponseBody
    ResponseEntity<Map<String, Object>> servletRequestBindingException(Exception e) {
        log.error(">> Error: " + e.getMessage(), e);
        return FalseResult.createErrorResult("RestException", "Missing token in header: " + e.getMessage(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public
    @ResponseBody
    ResponseEntity<Map<String, Object>> httpMessageNotReadableException(Exception e) {
        log.error(">> Error: " + e.getMessage(), e);
        return FalseResult.createErrorResult("RestException", "Unexpected: " + e.getMessage(),
                HttpStatus.BAD_REQUEST);
    }
}