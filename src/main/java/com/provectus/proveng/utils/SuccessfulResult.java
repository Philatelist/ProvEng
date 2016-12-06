package com.provectus.proveng.utils;

import com.provectus.proveng.enumaration.SuccessType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SuccessfulResult {

    private static final Logger log = LogManager.getLogger(SuccessfulResult.class);

    static {
        log.info("Started CreateResultUtils");
    }

    private ResponseEntity result;

    public SuccessfulResult() {
    }

    public SuccessfulResult(SuccessType type) {
        if (type.equals(SuccessType.DELETE_OK)) {
            this.result = createResult("Delete object", SuccessType.DELETE_OK.getDescription(), HttpStatus.OK);
        }
        if (type.equals(SuccessType.OK)) {
            this.result = createResult("Success", SuccessType.OK.getDescription(), HttpStatus.OK);
        }

    }

    public SuccessfulResult(Object object) {
        this.result = createResult(object);
    }

    public static ResponseEntity<Map<String, Object>> createResult(Object object) {
        Map<String, Object> result = new HashMap<>();
        result.put("result", object);

        if (object == null) result.put("result", "List of entities is empty");

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public static ResponseEntity<Map<String, Object>> createResult(String successType,
                                                                   String successDescription, HttpStatus httpStatus) {
        Map<String, Object> result = new HashMap<>();
        Map<String, String> successMap = new HashMap<>();
        successMap.put("code", String.valueOf(httpStatus));
        successMap.put("type", successType);
        successMap.put("description", successDescription);

        result.put("result", successMap);

        return new ResponseEntity<>(result, httpStatus);
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
        if (!super.equals(o)) return false;

        SuccessfulResult that = (SuccessfulResult) o;

        return result != null ? result.equals(that.result) : that.result == null;

    }

    @Override
    public int hashCode() {
        int result1 = super.hashCode();
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        return result1;
    }

    @Override
    public String toString() {
        return "CreateResult{" +
                "result=" + result +
                '}';
    }
}
