package com.provectus.proveng.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@EnableAutoConfiguration
public class WelcomeController {

    //for test external property file
    @Value("${level.advanced.score}")
    private double ADVANCED_SCORE;

    @RequestMapping("/")
    public String index() {
        return "Welcome to trainees project! version _0e0b2fc0*"
                + ADVANCED_SCORE;

    }

}