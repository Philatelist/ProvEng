package com.provectus.proveng.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.provectus.proveng.domain.Developer;
import com.provectus.proveng.domain.Group;
import com.provectus.proveng.domain.Role;
import com.provectus.proveng.domain.User;
import com.provectus.proveng.enumaration.ErrorType;
import com.provectus.proveng.enumaration.LevelType;
import com.provectus.proveng.enumaration.PermissionType;
import com.provectus.proveng.enumaration.RoleName;
import com.provectus.proveng.service.*;
import com.provectus.proveng.utils.FalseResult;
import com.provectus.proveng.utils.SuccessfulResult;
import com.provectus.proveng.utils.view.UserView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Controller
public class UserDataController {

    public static final String USER_INFO_URL_GOOGLE = "https://www.googleapis.com/oauth2/v3/userinfo?access_token=";
    private static final Logger log = LogManager.getLogger(UserDataController.class);

    static {
        log.info("Started UserDataController");
    }

    @Autowired
    private UserService userService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DeveloperService developerService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private SuccessfulResult successfulResult;

    @Autowired
    private FalseResult falseResult;

    @Autowired
    private LoginSessionService loginSessionService;

    /**
     * rest call for user info
     *
     * @param token String (token of our application)
     * @return User entity in Json with next params:
     * { "sub":
     * "email_verified":
     * "gender":
     * "getProfile":
     * "name":
     * "given_name":
     * "locale":
     * "family_name":
     * "picture":
     * "email":}
     */

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/rest/v1/gUser",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    ResponseEntity getProfile(@RequestHeader("token") String token) {
        try {

            log.debug("INJECTED +++ 'getProfile' token: " + token);
            String url = USER_INFO_URL_GOOGLE + token;

            RestTemplate restTemplate = new RestTemplate();

            HashMap resultMap = restTemplate.getForObject(url, HashMap.class);
            //TODO check result from google
            if (resultMap.containsValue("invalid_token")) {
                return new FalseResult(ErrorType.AUTH_FALSE).getResult();
            } else {
                User user = new User();
                user.setFirstName((String) resultMap.get("given_name"));
                user.setLastName((String) resultMap.get("family_name"));
                user.setEmail((String) resultMap.get("email"));
                user.setLoginName((String) resultMap.get("email"));
                user.setUrl((String) resultMap.get("picture"));
                successfulResult = new SuccessfulResult(user);
            }

            log.debug(">>> RESULT 'getProfile' returns: " + successfulResult.toString());

            return successfulResult.getResult();
        } catch (Exception e) {
            log.error("MESSAGE ERROR 'getProfile' - " + String.valueOf(e));

            return getErrorMapResponseEntity(e);

        }

    }

    /**
     * Create a new user with an auto-generated id and email and full name as passed values.
     */
    @RequestMapping(value = "/rest/db/user",
            method = RequestMethod.POST)
    public
    @ResponseBody
    String createUserInDB(String name, String lastname, String email) {
        try {
            log.debug(">>> createUserInDB... " +
                    "\nWITH injected email: " + email + ", name: " + name + ", lastName: " + lastname);
            User user = new User(name, lastname, email);
            log.debug(">> \nCREATED user WITH: " + user.toString());
            userService.create(user);
            log.debug(">> \nSEND to DAO user: " + user.toString());

        } catch (Exception e) {
            e.printStackTrace();

            log.debug("FULL ERROR getStacktrace: " + Arrays.toString(e.getStackTrace()));
            log.error("<<< createUserInDB...WITH ERROR: ", e);

            return "Error creating the user: " + getErrorMapResponseEntity(e).toString();
        }
        log.debug("<<< createUserInDB... WITH result: User succesfully created!");
        return "User succesfully created!";
    }

    /**
     * Create a new developer with an auto-generated id and email.
     */
    @RequestMapping(value = "/rest/v1/developer",
            method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity createDeveloper(@RequestHeader("token") String token,
                                   @RequestParam(value = "email") String email) {
        User newUser = loginSessionService.getLoginSessionByToken(token).getUser();

        if (!newUser.hasRole(RoleName.ADMIN)) {
            return new FalseResult(ErrorType.NO_ACCESS).getResult();
        }
        try {
            if (developerService.getByEmail(email).size() == 0) {
                Developer developer = new Developer(email);
                developerService.create(developer);
            } else return new FalseResult(ErrorType.DOUBLE).getResult();

        } catch (Exception e) {
            e.printStackTrace();

            return getErrorMapResponseEntity(e);
        }
        return new SuccessfulResult(developerService.getAllDevelopersEmail()).getResult();
    }

    /**
     * Turn OFF developer with the passed id.
     */
    @RequestMapping(value = "/rest/v1/developer",
            method = RequestMethod.DELETE)
    @ResponseBody
    ResponseEntity deleteDeveloper(@RequestHeader("token") String token,
                                   @RequestParam(value = "email") String email) {
        User newUser = loginSessionService.getLoginSessionByToken(token).getUser();

        if (!newUser.hasRole(RoleName.ADMIN)) {
            return new FalseResult(ErrorType.NO_ACCESS).getResult();
        }
        try {
            if (developerService.getByEmail(email).size() == 0) {
                return new FalseResult(ErrorType.NOT_IN_DB).getResult();
            }
            developerService.turnOffDeveloper(email);
        } catch (Exception ex) {
            return getErrorMapResponseEntity(ex);
        }
        return new SuccessfulResult(developerService.getAllDevelopersEmail()).getResult();
    }

    /**
     * Turn OFF developer with the passed id.
     */
    @RequestMapping(value = "/rest/v1/developer", method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity getDevelopers(@RequestHeader("token") String token) {
        User newUser = loginSessionService.getLoginSessionByToken(token).getUser();

        if (!newUser.hasRole(RoleName.ADMIN)) {
            return new FalseResult(ErrorType.NO_ACCESS).getResult();
        }
        try {
            successfulResult = new SuccessfulResult(developerService.getAllDevelopersEmail());
        } catch (Exception ex) {
            return getErrorMapResponseEntity(ex);
        }
        return successfulResult.getResult();
    }

    /**
     * Full delete the user with the passed id.
     */
    @RequestMapping(value = "/rest/db/userDelete",
            method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteUserFromDB(Long id) {
        try {
            log.debug("INJECTED 'deleteUserFromDB' id: " + id);
            User user = new User(id);
            log.debug("CREATED 'deleteUserFromDB' user: " + user.toString());
            userService.delete(user);
        } catch (Exception ex) {
            return getError(ex);
        }
        log.debug("<<< deleteUserFromDB... WITH result: User succesfully fully deleted! New userSysStatus: \"-1\"");

        return "User succesfully fully deleted!";
    }

    /**
     * Change user sysStatus with the passed id.
     */
    @RequestMapping(value = "/rest/db/user",
            method = RequestMethod.DELETE)
    @ResponseBody
    public String changeUserStatus(long id) {
        try {
            log.debug("INJECTED 'changeUserStatus' id: " + id);
            User user = userService.getById(id);
            user.setSysStatus(-1);
            log.debug("CREATED 'changeUserStatus' user: " + user.toString());
            userService.updateStatus(user);
        } catch (Exception ex) {
            return getError(ex);
        }
        log.debug("<<< changeUserStatusInDB... WITH result: User succesfully deleted!");

        return "User succesfully deleted!";
    }

    private String getError(Exception e) {
        log.error("MESSAGE ERROR - " + String.valueOf(e));

        log.debug("ERROR returns: " + getErrorMapResponseEntity(e).toString());
        return "Error deleting the user: " + getErrorMapResponseEntity(e).toString();
    }

    /**
     * Retrieve the id for the user with the passed email address.
     */
    @RequestMapping(value = "/rest/db/user",
            method = RequestMethod.GET)
    @ResponseBody
    public String getUserIdByEmail(String email) {
        String userId;
        try {
            User user = userService.getByEmail(email);
            userId = String.valueOf(user.getId());
        } catch (Exception ex) {
            return "User not found: " + ex.toString();
        }
        return "The user id is: " + userId;
    }

    /**
     * Update the email and the name for the user indentified by the passed id.
     */
    @RequestMapping(value = "/rest/v1/user", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    @JsonView(UserView.MediumInfoLevel.class)
    ResponseEntity updateUserInDB(@RequestHeader("token") String token,
                                  @RequestBody User user) {
        User newUser = loginSessionService.getLoginSessionByToken(token).getUser();

        if (!newUser.hasPermission(PermissionType.PROFILE, PermissionType.WRITE)) {
            return new FalseResult(ErrorType.NO_ACCESS).getResult();
        }

        try {
            log.debug(">>> updateUserInDB...");
            newUser.setSkype(user.getSkype());
            newUser.setPhone(user.getPhone());
            if (user.getDepartment() != null) {
                newUser.setDepartment(departmentService.getById(user.getDepartment().getId()));
            }
            userService.update(newUser);
            successfulResult = new SuccessfulResult(newUser);

        } catch (Exception e) {
            return getErrorMapResponseEntity(e);

        }
        log.debug("<<< updateUserInDB... WITH result: User succesfully updated!");

        return successfulResult.getResult();
    }

    /**
     * rest call for user info Get:
     *
     * @param token     String (token of our application) - expected
     * @param roleName  String - expected
     * @param roleName2 String - optionally
     * @return List entities of User in JSON
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "rest/v1/users", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    @JsonView(UserView.UserLevel.class)
    ResponseEntity getUsersByRole(@RequestHeader("token") String token,
                                  @RequestParam(value = "roleName") String roleName,
                                  @RequestParam(value = "roleName2", required = false) String roleName2) {

        User user = loginSessionService.getLoginSessionByToken(token).getUser();

        if (!isUserHasRole(user, RoleName.ADMIN) && !isUserHasRole(user, RoleName.TEACHER))
            return new FalseResult(ErrorType.NO_ACCESS).getResult();

        try {
            log.debug(">>> INJECTED 'getUsersRole' token: " + token);

            List<User> result = userService.getByRole(roleName);
            if (roleName2 != null) {
                List<User> result2 = userService.getByRole(roleName2);
                for (User u : result2) {
                    if (!result.contains(u)) {
                        result.add(u);
                    }
                }
            }
            Collections.sort(result, (user1, user2) -> user1.getLastName().compareTo(user2.getLastName()));

            successfulResult = new SuccessfulResult(result);

            log.debug(">>> RESULT 'getUsersRole' returns: " + successfulResult.toString());

            return successfulResult.getResult();
        } catch (Exception e) {
            return getErrorMapResponseEntity(e);
        }
    }

    /**
     * Set user role by user_id.
     */
    @RequestMapping(value = "/rest/v1/setUserRole",
            method = RequestMethod.PUT)
    @ResponseBody
    @JsonView(UserView.MediumInfoLevel.class)
    ResponseEntity setUserRole(@RequestParam(value = "user_id") long id,
                               @RequestParam(value = "role") String roleName) {
        try {
            User result = userService.getById(id);
            if (roleName.equals("")) {
                result.addRole(roleService.getById(1));
            } else if (roleService.getByName(roleName) == null) {
                return new FalseResult(ErrorType.NOT_IN_DB).getResult();
            } else result.addRole(roleService.getByName(roleName));

            userService.update(result);

            SuccessfulResult successfulResult = new SuccessfulResult(result);
            return successfulResult.getResult();

        } catch (Exception e) {
            return getErrorMapResponseEntity(e);

        }
    }

    @RequestMapping(value = "/rest/v1/deleteUserRole",
            method = RequestMethod.DELETE)
    @ResponseBody
    @JsonView(UserView.MediumInfoLevel.class)
    ResponseEntity deleteUserRole(@RequestParam(value = "user_id") long id,
                                  @RequestParam(value = "role") String roleName) {
        try {
            User user = userService.getById(id);
            if (roleService.getByName(roleName) == null) {
                return new FalseResult(ErrorType.NOT_IN_DB).getResult();
            } else {
                Role role = roleService.getByName(roleName);
                user.removeRole(role);
                userService.update(user);
                successfulResult = new SuccessfulResult(user);
            }
        } catch (Exception e) {
            return getErrorMapResponseEntity(e);
        }
        return successfulResult.getResult();
    }

    @RequestMapping(value = "rest/v1/usersStartTest", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(UserView.UserLevel.class)
    public ResponseEntity getUsersStartTest(
            @RequestHeader("token") String token,
            @RequestParam(value = "level", required = false) String level) {

        User user = loginSessionService.getLoginSessionByToken(token).getUser();

        if (!isUserHasRole(user, RoleName.ADMIN) && !isUserHasRole(user, RoleName.TEACHER))
            return new FalseResult(ErrorType.NO_ACCESS).getResult();

        try {
            log.debug(">>> INJECTED 'getUsersStartTest' token: " + token);
            List<User> users;
            List<User> result = new ArrayList<>();
            if (level == null) {
                users = userService.getByPassedStartTest();
                for (User u : users) {
                    if (u.getGroups() != null && !isHasPrimaryGroup(u) && !result.contains(u)) {
                        result.add(u);
                    }
                }
            }
            if (level != null && isCorrectLevel(level)) {
                users = userService.getByLevelOutsideGroup(level);
                for (User u : users) {
//                    if (u.getGroups() != null && !isHasPrimaryGroup(u) && !result.contains(u)) {
                    result.add(u);
//                    }
                }
            } else if (level != null && !isCorrectLevel(level)) {
                return new FalseResult(ErrorType.BAD_INPUT).getResult();
            }

            successfulResult = new SuccessfulResult(result);

            log.debug(">>> RESULT 'getUsersStartTest' returns: " + successfulResult.toString());

            return successfulResult.getResult();
        } catch (Exception e) {
            return getErrorMapResponseEntity(e);
        }
    }

    @RequestMapping(value = "rest/v1/usersLevel", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUsersByLevel(@RequestHeader("token") String token) {

        User user = loginSessionService.getLoginSessionByToken(token).getUser();

        if (!isUserHasRole(user, RoleName.ADMIN) && !isUserHasRole(user, RoleName.TEACHER))
            return new FalseResult(ErrorType.NO_ACCESS).getResult();

        try {
            log.debug(">>> INJECTED 'getUsersByLevel' token: " + token);
            Map<String, Integer> users = new HashMap<>();
            users.put(LevelType.ELEMENTARY.getStringValue(), userService.getByLevelOutsideGroup(LevelType.ELEMENTARY.getStringValue()).size());
            users.put(LevelType.PRE_INTERMEDIATE.getStringValue(), userService.getByLevelOutsideGroup(LevelType.PRE_INTERMEDIATE.getStringValue()).size());
            users.put(LevelType.INTERMEDIATE.getStringValue(), userService.getByLevelOutsideGroup(LevelType.INTERMEDIATE.getStringValue()).size());
            users.put(LevelType.UPPER_INTERMEDIATE.getStringValue(), userService.getByLevelOutsideGroup(LevelType.UPPER_INTERMEDIATE.getStringValue()).size());
            successfulResult = new SuccessfulResult(users);

            log.debug(">>> RESULT 'getUsersByLevel' returns: " + successfulResult.toString());

            return successfulResult.getResult();
        } catch (Exception e) {
            return getErrorMapResponseEntity(e);
        }
    }

    private boolean isCorrectLevel(String level) {
        return level.equals(LevelType.ELEMENTARY.getStringValue()) || level.equals(LevelType.PRE_INTERMEDIATE.getStringValue()) || level.equals(LevelType.INTERMEDIATE.getStringValue()) || level.equals(LevelType.UPPER_INTERMEDIATE.getStringValue());
    }

    private boolean isUserHasRole(User user, String roleForCheck) {
        List<Role> roles = new ArrayList<>(user.getRoles());
        for (Role role : roles) {
            if (role.getName().equals(roleForCheck)) {
                return true;
            }
        }
        return false;
    }

    private boolean isHasPrimaryGroup(User user) {
        for (Group group : user.getGroups()) {
            if (group.getPrimaryGroupFlag()) {
                return true;
            }
        }
        return false;
    }

    private ResponseEntity getErrorMapResponseEntity(Exception e) {
        log.error(">>> MESSAGE ERROR - " + String.valueOf(e));

        String description = String.valueOf(e);

        FalseResult falseResult = new FalseResult(e.getCause().toString(), description, HttpStatus.BAD_REQUEST);
        log.debug(">>> ERROR returns: " + e.toString());
        return falseResult.getResult();
    }

}