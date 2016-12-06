package com.provectus.proveng.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.provectus.proveng.domain.*;
import com.provectus.proveng.domain.dto.TestResultDto;
import com.provectus.proveng.enumaration.DaybookType;
import com.provectus.proveng.enumaration.RoleName;
import com.provectus.proveng.enumaration.TestType;
import com.provectus.proveng.exception.ErrorResponseException;
import com.provectus.proveng.exception.TestServiceException;
import com.provectus.proveng.service.DayBookService;
import com.provectus.proveng.service.RoleService;
import com.provectus.proveng.service.TestService;
import com.provectus.proveng.service.UserService;
import com.provectus.proveng.utils.ResponseUtils;
import com.provectus.proveng.utils.view.TestView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TestController {

    private static Logger logger = LogManager.getLogger(TestController.class);

    @Autowired
    private TestService testService;
    @Autowired
    private DayBookService dayBookService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/rest/v1/test", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Map<String, Object>> createTest(@RequestHeader("token") String token,
                                                   @RequestBody Test test) {

        logger.debug(">>> createTest()");
        // System.out.println(">> test = " + test);

        test = testService.create(test);

        Map<String, Object> response = new HashMap<>();
        response.put("testId", test.getId());
        response.put("testName", test.getName());
        logger.debug("<<< createTest()");
        return ResponseUtils.buildSuccessfulResponse(response);

    }

    /*****
     * update test
     *****/
    @RequestMapping(value = "/rest/v1/test", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(TestView.AllInfoLevel.class)
    ResponseEntity<Map<String, Object>> updateTest(@RequestHeader("token") String token,
                                                   @RequestBody Test test) {

        logger.debug(">>> updateTest()");
        // System.out.println(">> test = " + test);

        test = testService.update(test);

        logger.debug("<<< createTest()");
        return ResponseUtils.buildSuccessfulResponse(test);

    }

    /**
     * Get all test for user
     */
    @RequestMapping(value = "/rest/v1/tests", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(TestView.ShortInfoLevel.class)
    ResponseEntity<Map<String, Object>> getTests(@RequestHeader("token") String token) {

        User user = userService.getUserFromSessionByToken(token);
        List<Test> tests = testService.getTestsForUser(user);
        return ResponseUtils.buildSuccessfulResponse(tests);
    }

    @RequestMapping(value = "/rest/v1/test", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(TestView.AllInfoLevel.class)
    ResponseEntity<Map<String, Object>> getTest(@RequestHeader("token") String token,
                                                @RequestParam(value = "id") Long parentTestId) {

        logger.debug(">>> getTest()");
        Test test;
        try {

            test = testService.getActiveById(parentTestId);
        } catch (TestServiceException e) {
            throw new ErrorResponseException("GetTestError", e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
        // Test test = testService.getTestVariant(parentTestId);

        return ResponseUtils.buildSuccessfulResponse(test);

    }

    @RequestMapping(value = "/rest/v1/test/cards", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> addTestCardsToTest(
            @RequestHeader("token") String token, @RequestParam("testId") Long testId,
            @RequestBody TestCard[] testCards) {
        // TODO implement
        try {
            testService.checkIfExist(testId);
        } catch (TestServiceException e) {
            throw new ErrorResponseException("AddTestCardsToTestError", e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("status", 200);
        result.put("message", "testCards have added to test");
        return ResponseUtils.buildSuccessfulResponse(result);

    }

    @RequestMapping(value = "/rest/v1/test", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> deleteTest(@RequestHeader("token") String token,
                                                          @RequestParam("id") Long id) {

        logger.debug(">>> deleteTest()");

        try {
            testService.checkIfExist(id);
        } catch (TestServiceException e) {
            throw new ErrorResponseException("deleteTestError", e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }

        testService.turnOffById(id);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("status", 200);
        result.put("message", "test has deleted");
        logger.debug("<<< deleteTest()");
        return ResponseUtils.buildSuccessfulResponse(result);

    }

    @RequestMapping(value = "rest/v1/test_result", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    // @JsonView(DayBookView.TestResultLevel.class)
    public ResponseEntity<Map<String, Object>> createTestResult(
            @RequestHeader("token") String token, @RequestParam("testId") Long testId,
            @RequestParam("duration") Long duration, @RequestBody TestCardChoice[] choices) {
        logger.debug(">>> createTestResult()");
        // TODO check for test integrity(cards and answers are from this test)
        User user = userService.getUserFromSessionByToken(token);

        TestAttempt testAttempt = null;

        try {
            testAttempt = testService.createTestAttempt(testId, choices);
        } catch (TestServiceException e) {
            throw new ErrorResponseException("testResultError", e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }

        DayBook daybook = testService.checkTest(testAttempt);

        TestResultDto testResult = testService.makeTestResult(daybook);

        if (user.hasRole(RoleName.GUEST) && testAttempt.getIsPassed()
                && testAttempt.getTest().getType().equals(TestType.START)) {

            Role roleNew = roleService.getByName(RoleName.STUDENT);
            Role roleOld = roleService.getByName(RoleName.GUEST);

            user.removeRole(roleOld);
            user.getRoles().add(roleNew);

            user.setLevel(testResult.getLevel());
            daybook.setType(DaybookType.START_TEST.getDaybookType());
            dayBookService.deleteAllStartTestDaybooksForUser(user);

        }

        daybook.setUser(user);

        daybook = dayBookService.create(daybook);
        daybook = dayBookService.refresh(daybook);

        logger.debug("<<< createTestResult()");
        return ResponseUtils.buildSuccessfulResponse(testResult);
    }
}
