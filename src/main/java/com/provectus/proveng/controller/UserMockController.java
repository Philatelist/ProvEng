package com.provectus.proveng.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.provectus.proveng.domain.DayBook;
import com.provectus.proveng.domain.Department;
import com.provectus.proveng.domain.User;
import com.provectus.proveng.enumaration.ErrorType;
import com.provectus.proveng.utils.FalseResult;
import com.provectus.proveng.utils.SuccessfulResult;
import com.provectus.proveng.utils.TestEntityUtils;
import com.provectus.proveng.utils.view.UserView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UserMockController {

    private static Logger log = LogManager.getLogger(UserMockController.class);

    static {
        log.info(">> loaded UserMockController");
    }

    @Autowired
    SuccessfulResult successfulResult;

    @RequestMapping(value = "rest/mock/logout", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity logout(@RequestHeader("token") String token) {

        log.debug(">>> logout()");
        log.debug("token = " + token);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status", 200);
        resultMap.put("message", HttpStatus.OK);
        log.debug("<<< logout()");
        return new SuccessfulResult(resultMap).getResult();
    }

    /**
     * rest call for user info Get:
     *
     * @param token  String (token of our application)
     * @param userId Integer
     * @return User entity in Json
     */
    @RequestMapping(value = "rest/mock/user", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(UserView.AllInfoLevel.class)
    public ResponseEntity getUser(@RequestHeader("token") String token,
                                  @RequestParam(value = "userId", required = false) Long userId) {

        log.debug(">>> getUser()");
        log.debug("token = " + token + ", userId = " + userId);

        if (userId == null)
            userId = 1L;
        User user = TestEntityUtils.createTestUser(userId);

        log.debug(">> user = " + user);
        log.debug("<<< getUser()");
        return new SuccessfulResult(user).getResult();
    }

    /**
     * Authentification call POST:
     *
     * @param request a JSON: validateGtoken:Google token String email:email (non required)
     *                String
     * @return in JSON token:String (token of our application) userID:Integer
     * (will be removed in future)
     */
    @RequestMapping(value = "/rest/mock/auth-by-google", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity authByGoogleMock(@RequestBody Map<String, String> request) {

        log.debug(">>> authByGoogleMock()");

        String gToken = request.get("gToken");
        String email = request.get("email");

        log.debug(">>> gToken = " + gToken + "\nemail = " + email);
        Map<String, Object> response = new HashMap<>();
        if (gToken == null || gToken.isEmpty()) {

            log.debug("<<< authByGoogleMock()");
            return new FalseResult(ErrorType.AUTH_FALSE).getResult();

        } else {

            response.put("token", "ourToken");
            response.put("userID", 22);

            log.debug("<<< authByGoogleMock()");
            return new SuccessfulResult(response).getResult();
        }
    }

    /**
     * rest call for user info Get:
     *
     * @param token    String (token of our application) - expected
     * @param roleName String - optionally
     * @param level    String - optionally
     * @param event_id Long - optionally
     * @return List entities of User in JSON
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "rest/mock/users", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getUsersLevelMock(@RequestHeader("token") String token,
                                                                 @RequestParam(value = "roleName", required = false) String roleName,
                                                                 @RequestParam(value = "event_id", required = false) Long event_id,
                                                                 @RequestParam(value = "level", required = false) String level) {

        Department department1 = new Department();

        User user1 = new User();
        user1.setDepartment(department1);
        user1.setLoginName("Login");
        user1.setUserType("U");
        user1.setFirstName("Alexander");
        user1.setLastName("Makedonsky");
        user1.setEmail("alex@google.com");
        user1.setSkype("makedonian_skype");
        user1.setInviteDate(new Date());
        user1.setPasswordCanExpireFlag(true);
        user1.setPhone("+380671234567");
        user1.setUrl("http://............");
//        user1.setDepartment(null);

        User user2 = new User();
        user2.setFirstName("Dim");

        try {
            log.debug(">>> INJECTED 'getUsersLevelMock' token: " + token);

            List<User> result = new ArrayList<>();
            result.add(user1);
            result.add(user2);

            successfulResult = new SuccessfulResult(result);

            log.debug(">>> RESULT 'getUsersLevelMock' returns: " + successfulResult.toString());

            return successfulResult.getResult();
        } catch (Exception e) {
            return getErrorMapResponseEntity(e);
        }
    }

    /**
     * rest call for users who passed StartTest:
     *
     * @param token String (token of our application) - expected
     * @return JSON object: { "result": [ { "id": 0, "sysStatus": 0,
     * "createDtm": 1471524881846, "modifyDtm": 1471524881846,
     * "department_id": 1, "loginName": "Login", "userType": "U",
     * "firstName": "Alexander", "lastName": "Makedonsky", "email":
     * "alex@google.com", "skype": "makedonian_skype", "inviteDate":
     * 1471524881846, "passwordCanExpireFlag": true, "phone":
     * "+380671234567", "url": "http://............", "department":
     * null, "roles": [], "groups": [], "events": [], "dayBooks": [ {
     * "id": 0, "sysStatus": 0, "createDtm": 1471524881846, "modifyDtm":
     * null, "mark": 76, "user_id": 1, "event_id": 1, "markDate":
     * 1471524881846, "event": null, "user": null, "type": "Start test"
     * } ] }, { "id": 0, "sysStatus": 0, "createDtm": 1471524881846,
     * "modifyDtm": 1471524881846, "department_id": 1, "loginName":
     * "SecondLogin", "userType": "U", "firstName": "Dmitry",
     * "lastName": "Neveroyatniy", "email": "dom@yahoo.net", "skype":
     * "it-s-my-skype", "inviteDate": 1471524881846,
     * "passwordCanExpireFlag": true, "phone": "+380507654321", "url":
     * "http://............", "department": null, "roles": [], "groups":
     * [], "events": [], "dayBooks": [ { "id": 0, "sysStatus": 0,
     * "createDtm": 1471524881846, "modifyDtm": null, "mark": 94,
     * "user_id": 2, "event_id": 2, "markDate": 1471524881846, "event":
     * null, "user": null, "type": "Start test" } ] } ] }
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "rest/mock/usersStartTest", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(UserView.RedundantInfoLevel.class)
    public ResponseEntity<Map<String, Object>> getUsersStartTestMock(
            @RequestHeader("token") String token) {

        Department department2 = new Department();
        department2.setId(1L);

        DayBook dayBook1 = new DayBook();
        dayBook1.setId(1L);
        dayBook1.setEvent(null);
        dayBook1.setMark(76);
        dayBook1.setType("Start test");
        dayBook1.setMarkDate(new Date());
        //dayBook1.setUser_id(1);
        // dayBook1.setEvent_id(1);
        dayBook1.setUser(null);

        DayBook dayBook2 = new DayBook();
        dayBook2.setId(2L);
        dayBook2.setEvent(null);
        dayBook2.setMark(94);
        dayBook2.setType("Start test");
        dayBook2.setMarkDate(new Date());
        //dayBook2.setUser_id(2);
        //dayBook2.setEvent_id(2);
        dayBook2.setUser(null);

        List<DayBook> dayBooks1 = new ArrayList<>();
        dayBooks1.add(dayBook1);

        List<DayBook> dayBooks2 = new ArrayList<>();
        dayBooks2.add(dayBook2);

        User user1 = new User();
        user1.setId(1L);
        user1.setLoginName("Login");
        user1.setUserType("U");
        user1.setFirstName("Alexander");
        user1.setLastName("Makedonsky");
        user1.setEmail("alex@google.com");
        user1.setSkype("makedonian_skype");
        user1.setInviteDate(new Date());
        user1.setPasswordCanExpireFlag(true);
        user1.setPhone("+380671234567");
        user1.setUrl("http://............");
        user1.setDepartment(department2);
        user1.setDayBooks(dayBooks1);

        User user2 = new User();
        user2.setId(2L);
        user2.setLoginName("SecondLogin");
        user2.setUserType("U");
        user2.setFirstName("Dmitry");
        user2.setLastName("Neveroyatniy");
        user2.setEmail("dom@yahoo.net");
        user2.setSkype("it-s-my-skype");
        user2.setInviteDate(new Date());
        user2.setPasswordCanExpireFlag(true);
        user2.setPhone("+380507654321");
        user2.setUrl("http://............");
        user2.setDepartment(department2);
        user2.setDayBooks(dayBooks2);

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        try {
            log.debug(">>> INJECTED 'getUsersStartTestMock' token: " + token);

            successfulResult = new SuccessfulResult(users);

            log.debug(">>> RESULT 'getUsersStartTestMock' returns: " + successfulResult.toString());

            return successfulResult.getResult();
        } catch (Exception e) {
            return getErrorMapResponseEntity(e);
        }
    }

    /**
     * rest call for number users by level Get:
     *
     * @param token String (token of our application) - expected
     * @return JSON object: { "result": { "Beginner": 23,
     * "Upper-intermediate": 18, "Advanced": 12,
     * "Pre-intermediate": 47, "Elementary": 16,
     * "Intermediate": 132, "Proficient": 7 } }
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "rest/mock/usersLevel", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getUsersMock(@RequestHeader("token") String token) {

        Map<String, Integer> numberUsers = new HashMap<>();
        numberUsers.put("Beginner", 0);
        numberUsers.put("Elementary", 16);
        numberUsers.put("Pre-intermediate", 47);
        numberUsers.put("Intermediate", 132);
        numberUsers.put("Upper-intermediate", 18);
        numberUsers.put("Advanced", 12);
        numberUsers.put("Proficient", 0);

        try {
            log.debug(">>> INJECTED 'getUsersMock' token: " + token);

            successfulResult = new SuccessfulResult(numberUsers);

            log.debug(">>> RESULT 'getUsersMock' returns: " + successfulResult.toString());

            return successfulResult.getResult();
        } catch (Exception e) {
            return getErrorMapResponseEntity(e);
        }
    }

    @SuppressWarnings("unchecked")
    private ResponseEntity<Map<String, Object>> getErrorMapResponseEntity(Exception e) {
        log.error(">>> MESSAGE ERROR - " + String.valueOf(e));

        String description = String.valueOf(e);

        FalseResult falseResult = new FalseResult(e.getCause().toString(), description,
                HttpStatus.BAD_REQUEST);
        log.debug(">>> ERROR returns: " + e.toString());
        return falseResult.getResult();
    }

}
