package com.provectus.proveng.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.provectus.proveng.domain.User;
import com.provectus.proveng.service.DepartmentService;
import com.provectus.proveng.service.EventService;
import com.provectus.proveng.service.LocationService;
import com.provectus.proveng.service.UserService;
import com.provectus.proveng.utils.FalseResult;
import com.provectus.proveng.utils.SuccessfulResult;
import com.provectus.proveng.utils.view.DepartmentView;
import com.provectus.proveng.utils.view.EventView;
import com.provectus.proveng.utils.view.UserView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TestForAllController {

    private static final Logger log = LogManager.getLogger(TestForAllController.class);

    static {
        log.info("Started EventController");
    }

    @Autowired
    private EventService eventService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/rest/all/events", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE, headers = {"allInfo=include"})
    public
    @ResponseBody
    @JsonView(EventView.AllInfoLevel.class)
    ResponseEntity getEvents() {
        try {

            List event = eventService.getAll(0);

            SuccessfulResult successfulResult = new SuccessfulResult(event);

            log.debug(">>> RESULT 'getEvents' returns: " + successfulResult.toString());

            return successfulResult.getResult();
        } catch (Exception e) {
            return getErrorMapResponseEntity(e);
        }
    }

    @RequestMapping(value = "/rest/all/departments", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE, headers = {"allInfo=include"})
    public
    @ResponseBody
    @JsonView(DepartmentView.AllInfoLevel.class)
    ResponseEntity getDepartments() {
        try {

            List departments = departmentService.getAll();

            SuccessfulResult successfulResult = new SuccessfulResult(departments);

            log.debug(">>> RESULT 'getDepartments' returns: " + successfulResult.toString());

            return successfulResult.getResult();
        } catch (Exception e) {
            return getErrorMapResponseEntity(e);
        }
    }

    @RequestMapping(value = "/rest/all/users", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE, headers = {"allInfo=include"})
    public
    @ResponseBody
    @JsonView(UserView.AllInfoLevel.class)
    ResponseEntity getUsers() {
        try {

            List<User> users = userService.getAll();

            SuccessfulResult successfulResult = new SuccessfulResult(users);

            log.debug(">>> RESULT 'getUsers' returns: " + successfulResult.toString());

            return successfulResult.getResult();
        } catch (Exception e) {
            return getErrorMapResponseEntity(e);
        }
    }

    private ResponseEntity getErrorMapResponseEntity(Exception e) {
        log.error(">>> MESSAGE ERROR - " + String.valueOf(e));

        String description = String.valueOf(e);

        FalseResult falseResult = new FalseResult(e.getCause().toString(), description, HttpStatus.BAD_REQUEST);
        log.debug(">>> ERROR returns: " + e.toString());
        return falseResult.getResult();
    }
}
