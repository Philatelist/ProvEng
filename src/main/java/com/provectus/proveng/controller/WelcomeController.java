package com.provectus.proveng.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class WelcomeController {

    @RequestMapping("/")
    public String index() {
        return "Welcome to trainees project!";

    }

}