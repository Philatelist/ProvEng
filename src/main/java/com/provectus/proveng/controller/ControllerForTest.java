package com.provectus.proveng.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/rest/test")
public class ControllerForTest {
    private static Logger logger = LogManager.getLogger(UserController.class);

    @RequestMapping(value = "/json_body",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    Map<String, Object> createGroup(
            @RequestBody Map<String, Object> request) {

        logger.debug(">> request = " + request);

        return request;
    }
}
