package com.provectus.proveng.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.provectus.proveng.service.DepartmentService;
import com.provectus.proveng.service.EventService;
import com.provectus.proveng.service.LocationService;
import com.provectus.proveng.service.UserService;
import com.provectus.proveng.utils.FalseResult;
import com.provectus.proveng.utils.SuccessfulResult;
import com.provectus.proveng.utils.view.DepartmentView;
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
import java.util.Map;

@Controller
public class DepartmentController {

    private static final Logger log = LogManager.getLogger(DepartmentController.class);

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


    @RequestMapping(value = "/rest/v1/departments", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    @JsonView(DepartmentView.ShortInfoLevel.class)
    ResponseEntity<Map<String, Object>> getDepartmentsShort() {
        try {

            return getAllDepartments();
        } catch (Exception e) {
            return getErrorMapResponseEntity(e);
        }
    }

    @RequestMapping(value = "/rest/v1/departments", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE, headers = {"mediumInfo=true"})
    public
    @ResponseBody
    @JsonView(DepartmentView.MediumInfoLevel.class)
    ResponseEntity<Map<String, Object>> getDepartmentsMedium() {
        try {

            return getAllDepartments();
        } catch (Exception e) {
            return getErrorMapResponseEntity(e);
        }
    }

    @RequestMapping(value = "/rest/v1/departments", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE, headers = {"allInfo=true"})
    public
    @ResponseBody
    @JsonView(DepartmentView.AllInfoLevel.class)
    ResponseEntity<Map<String, Object>> getDepartmentsAll() {
        try {

            return getAllDepartments();
        } catch (Exception e) {
            return getErrorMapResponseEntity(e);
        }
    }

    private ResponseEntity<Map<String, Object>> getAllDepartments() {
        List departments = departmentService.getAll();

        SuccessfulResult successfulResult = new SuccessfulResult(departments);

        log.debug(">>> RESULT returns: " + successfulResult.toString());

        return successfulResult.getResult();
    }


    private ResponseEntity<Map<String, Object>> getErrorMapResponseEntity(Exception e) {
        log.error(">>> MESSAGE ERROR - " + String.valueOf(e));

        String description = String.valueOf(e);

        FalseResult falseResult = new FalseResult(e.getCause().toString(), description, HttpStatus.BAD_REQUEST);
        log.debug(">>> ERROR returns: " + e.toString());
        return falseResult.getResult();
    }
}
