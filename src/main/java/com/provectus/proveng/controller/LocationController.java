package com.provectus.proveng.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.provectus.proveng.service.EventService;
import com.provectus.proveng.service.LocationService;
import com.provectus.proveng.service.UserService;
import com.provectus.proveng.utils.FalseResult;
import com.provectus.proveng.utils.SuccessfulResult;
import com.provectus.proveng.utils.view.LocationView;
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
public class LocationController {

    private static final Logger log = LogManager.getLogger(LocationController.class);

    static {
        log.info("Started EventController");
    }

    @Autowired
    private EventService eventService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private UserService userService;


    @RequestMapping(value = "/rest/v1/locations", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    @JsonView(LocationView.ShortInfoLevel.class)
    ResponseEntity getDepartmentsShort() {
        try {
            return getAllLocations();
        } catch (Exception e) {
            return getErrorMapResponseEntity(e);
        }
    }

    @RequestMapping(value = "/rest/v1/locations", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE, headers = {"mediumInfo=true"})
    public
    @ResponseBody
    @JsonView(LocationView.MediumInfoLevel.class)
    ResponseEntity getDepartmentsMedium() {
        try {
            return getAllLocations();
        } catch (Exception e) {
            return getErrorMapResponseEntity(e);
        }
    }

    @RequestMapping(value = "/rest/v1/locations", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE, headers = {"allInfo=true"})
    public
    @ResponseBody
    @JsonView(LocationView.AllInfoLevel.class)
    ResponseEntity getDepartmentsAll() {
        try {
            return getAllLocations();
        } catch (Exception e) {
            return getErrorMapResponseEntity(e);
        }
    }

    private ResponseEntity getAllLocations() {
        List locations = locationService.getAll();

        SuccessfulResult successfulResult = new SuccessfulResult(locations);

        log.debug(">>> RESULT 'getAllLocations' returns: " + successfulResult.toString());

        return successfulResult.getResult();
    }


    private ResponseEntity getErrorMapResponseEntity(Exception e) {
        log.error(">>> MESSAGE ERROR - " + String.valueOf(e));

        String description = String.valueOf(e);

        FalseResult falseResult = new FalseResult(e.getCause().toString(), description, HttpStatus.BAD_REQUEST);
        log.debug(">>> ERROR returns: " + e.toString());
        return falseResult.getResult();
    }
}
