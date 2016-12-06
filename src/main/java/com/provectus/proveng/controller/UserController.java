package com.provectus.proveng.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.provectus.proveng.domain.DayBook;
import com.provectus.proveng.domain.LoginSession;
import com.provectus.proveng.domain.User;
import com.provectus.proveng.domain.dto.UserAllDto;
import com.provectus.proveng.enumaration.DaybookType;
import com.provectus.proveng.exception.ErrorResponseException;
import com.provectus.proveng.service.DayBookService;
import com.provectus.proveng.service.DeveloperService;
import com.provectus.proveng.service.LoginSessionService;
import com.provectus.proveng.service.UserService;
import com.provectus.proveng.utils.ResponseUtils;
import com.provectus.proveng.utils.view.UserView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    public static final String GTOKEN_URL_LOCAL = "/rest/v1/validate_token?access_token=";
    public static final String USERINFO_URL_LOCAL = "/rest/v1/gUser?access_token=";
    public static final String GTOKEN_INFO_URL_GOOGLE = "https://www.googleapis.com/oauth2/v3/tokeninfo?access_token=";
    public static final String USER_INFO_URL_GOOGLE = "https://www.googleapis.com/oauth2/v3/userinfo?access_token=";

    private static Logger logger = LogManager.getLogger(UserController.class);

    static {
        logger.info(">> loaded UserController");
    }

    @Value("${server.address}")
    private String SERVER_HOSTNAME;
    @Value("${server.port}")
    private String SERVER_PORT;

    @Autowired
    private UserService userService;
    @Autowired
    private LoginSessionService loginSessionService;
    @Autowired
    private DayBookService dayBookService;
    @Autowired
    private DeveloperService developerService;

    /**
     * logout rest call POST
     *
     * @param token String token : token for user session
     * @return Status OK JSON { result { message:String status:String} }
     */
    @RequestMapping(value = "rest/v1/logout", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> logout(@RequestHeader("token") String token) {
        // TODO implement mock
        logger.debug(">>> logout()");
        logger.debug("token = " + token);

        userService.logoutUserByToken(token);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status", 200);
        resultMap.put("message", HttpStatus.OK);
        logger.debug("<<< logout()");
        return ResponseUtils.buildSuccessfulResponse(resultMap);
    }

    /**
     * Authentification call POST:
     *
     * @param request a JSON: validateGtoken: Google access token String email:
     *                email (non required)String for testing, can skip auth by
     *                google
     * @return in JSON token:String (token of our application)
     * userID:Integer(will be removed in future)
     */
    @SuppressWarnings({"unchecked", "unused"})
    @RequestMapping(value = "/rest/v1/auth-by-google", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(UserView.AllInfoLevel.class)
    ResponseEntity<Map<String, Object>> authByGoogle(@RequestBody Map<String, String> request) {
        // TODO add session expire validation
        logger.debug(">>> authByGoogle()");

        String gToken = request.get("gToken");
        String email = request.get("email");

        email = null;
        logger.debug(">>> gToken = " + gToken + "\nemail = " + email);

        if (gToken == null || gToken.isEmpty()) {

            logger.debug("<<< authByGoogle()");
            throw new ErrorResponseException("authError", "gToken is misssing",
                    HttpStatus.BAD_REQUEST);
        }

        User user = null;

        // for testing purpose skip auth by Google and create random user
        if (email != null && (userService.isValidEmailDomain(email) || developerService.checkIfDeveloper(email))) {
            user = userService.getUserByEmail(email);
            logger.debug(">> user from db =  " + user);
            if (user == null) {
                int ind = (int) (Math.random() * 10000);
                user = new User("Fname" + ind, "Lname" + ind, email);
                userService.create(user);
                // register new user
                logger.debug(">> regestering user");
                userService.registerUser(user);
            }
        }

        if (email == null) {

            // get gToken_info from Google(could be local rest call)
            String urlString = GTOKEN_INFO_URL_GOOGLE + gToken;
            // "http://" + SERVER_HOSTNAME + ":" + SERVER_PORT +
            // GTOKEN_URL_LOCAL" + validateGtoken;
            logger.debug(">> url = " + urlString);
            RestTemplate restTemplate = new RestTemplate();

            Map<String, String> responseMap = null;
            try {

                responseMap = restTemplate.getForObject(urlString, HashMap.class);
                logger.debug(">> response from gTokenInfo = " + responseMap);
            } catch (Exception e) {
                throw new ErrorResponseException("authError", "gToken is not valid",
                        HttpStatus.BAD_REQUEST);
            }
            email = responseMap.get("email");
            logger.debug(">> email from gToken_info = " + email);
            if (email == null) {
                throw new ErrorResponseException("authError", "gToken is not valid",
                        HttpStatus.BAD_REQUEST);
            }

        }

        // now user is validated by Google and we have his e-mail

        // trying to find user in our database by email
        if (userService.isValidEmailDomain(email) || developerService.checkIfDeveloper(email)) {
            user = userService.getUserByEmail(email);
            logger.debug(">> user from db =  " + user);
        }

        // if new user than get info from Google
        if (user == null) {

            // for new user check for domain
            if (!userService.isValidEmailDomain(email) && !developerService.checkIfDeveloper(email)) {

                throw new ErrorResponseException("authError", "email is not valid",
                        HttpStatus.BAD_REQUEST);
            }
            // get user info from Google (local rest call) and register him
            String urlString = USER_INFO_URL_GOOGLE + gToken;
            // String urlString = "http://" + SERVER_HOSTNAME + ":" +
            // SERVER_PORT + USERINFO_URL_LOCAL + gToken;

            logger.debug(">> calling for user info, url = " + urlString);
            RestTemplate restTemplate = new RestTemplate();

            Map<String, String> responseMap = null;
            try {

                responseMap = restTemplate.getForObject(urlString, HashMap.class);
                logger.debug(">> response from gTokenInfo = " + responseMap);
            } catch (Exception e) {
                throw new ErrorResponseException("authError", "gToken is not valid",
                        HttpStatus.BAD_REQUEST);
            }

            logger.debug(">> response from gUser = " + responseMap);
            email = responseMap.get("email");
            logger.debug(">> email from gToken_info = " + email);
            if (email == null) {
                throw new ErrorResponseException("authError", "gToken is not valid",
                        HttpStatus.BAD_REQUEST);
            }

            // create user

            user = new User();
            user.setFirstName(responseMap.get("given_name"));
            user.setLastName(responseMap.get("family_name"));
            user.setEmail(email);
            user.setUrl(responseMap.get("picture"));
            user.setLoginName(email);

            // register new user
            logger.debug(">> regestering user");
            userService.registerUser(user);

        }

        // check is user have session
        logger.debug(">> getting session for user = " + user);
        LoginSession userSession = userService.getActiveSessionByUser(user);

        logger.debug(">> userSession = " + userSession);

        if (userSession == null) {
            logger.debug(">> createLoginSession for user, userid = " + user.getId());
            userSession = loginSessionService.createLoginSession(user);
        }

        String token = null;
        if (userSession != null) {
            token = userSession.getToken();
        }

        Map<String, Object> response = new HashMap<>();
        // for test
        // response.put("email", user.getEmail());
        response.put("token", token);
        response.put("userId", user.getId());
        logger.debug("<<< authByGoogle()");
        return ResponseUtils.buildSuccessfulResponse(response);

    }

    /**
     * rest call for user info Get:
     *
     * @param token  String (token of our application)
     * @param userId Integer
     * @return User entity in Json
     */
    @RequestMapping(value = "rest/v1/user", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    // @JsonView(UserView.AllInfoLevel.class)
    public ResponseEntity<Map<String, Object>> getUser(@RequestHeader("token") String token,
                                                       @RequestParam(value = "id", required = false) Long userId) {

        logger.debug(">>> getUser()");
        User user;
        Long userIdFromToken = userService.getUserIdByToken(token);
        if (userId == null) {
            userId = userIdFromToken;
        }

        user = userService.getById(userId);
        if (user == null)
            throw new ErrorResponseException("getUserError", "user with this id doesn't exist",
                    HttpStatus.NOT_FOUND);

        List<DayBook> dayBooks = dayBookService.getAllUsersDaybook(userId);
        DayBook dayBook = new DayBook();
        int totalPoints = 0;
        for (DayBook d : dayBooks) {
            if (d != null) {
                totalPoints += d.getMark();
            }
        }
        dayBook.setType(DaybookType.TOTAL_POINTS.getDaybookType());
        dayBook.setMark(totalPoints);
        user.addDayBook(dayBook);

        logger.debug("<<< getUser()");

        return ResponseUtils.buildSuccessfulResponse(UserAllDto.convertToDto(user));

    }

}
