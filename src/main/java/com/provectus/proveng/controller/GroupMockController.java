package com.provectus.proveng.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.provectus.proveng.domain.Group;
import com.provectus.proveng.exception.ErrorResponseException;
import com.provectus.proveng.utils.ResponseUtils;
import com.provectus.proveng.utils.TestEntityUtils;
import com.provectus.proveng.utils.view.GroupView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class GroupMockController {

    private Logger logger = LogManager.getLogger(this.getClass());

    /**
     * @param token
     * @param groupId
     * @param userId
     * @param note
     * @return
     */
    @RequestMapping(value = "/rest/mock/group_user", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> deleteGroupUser(@RequestHeader("token") String token,
                                                               @RequestParam("group_id") Long groupId, @RequestParam("user_id") Long userId,
                                                               @RequestParam("note") String note) {

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("status", 200);
        result.put("message", "user deleted from group");
        return ResponseUtils.buildSuccessfulResponse(result);

    }

    @RequestMapping(value = "/rest/mock/group_users", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> deleteGroupUsers(@RequestHeader("token") String token,
                                                                @RequestParam("group_id") Long groupId, @RequestBody Map<String, Object>[] body) {
        for (Map<String, Object> map : body) {
            System.out.println(map.get("user_id"));
            System.out.println(map.get("note"));
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("status", 200);
        result.put("message", "users deleted from group");
        return ResponseUtils.buildSuccessfulResponse(result);

    }

    /**
     * request info about group
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "/rest/mock/group", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(GroupView.AllInfoLevel.class)
    public ResponseEntity<Map<String, Object>> getGroup(@RequestHeader("token") String token,
                                                        @RequestParam(value = "id", required = false) Long groupId) {

        logger.debug(">>> getGroup()");


        if (groupId == null)
            groupId = 1L;
        logger.debug(">> group_id = " + groupId);
        Group group = TestEntityUtils.createTestGroup(groupId);

        logger.debug("<<< getGroup()");
        return ResponseUtils.buildSuccessfulResponse(group);
    }

    /**
     * request info about groups
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "/rest/mock/groups", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(GroupView.ShortInfoLevel.class)
    public ResponseEntity<Map<String, Object>> getGroups(@RequestHeader("token") String token) {

        logger.debug(">>> getGroup()");

        List<Group> list = new ArrayList<>();
        list.add(TestEntityUtils.createTestGroup(1L, "Elementary"));
        list.add(TestEntityUtils.createTestGroup(2L, "Elementary"));
        list.add(TestEntityUtils.createTestGroup(3L, "Intermediate"));
        list.add(TestEntityUtils.createTestGroup(4L, "Intermediate"));
        list.add(TestEntityUtils.createTestGroup(5L, "Upper-intermediate"));
        list.add(TestEntityUtils.createTestGroup(6L, "Pre-intermediate"));
        list.add(TestEntityUtils.createTestGroup(7L, "Advanced"));

        logger.debug("<<< getGroup()");
        return ResponseUtils.buildSuccessfulResponse(list);
    }

    /**
     * request create group
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "/rest/mock/group", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> createGroup(@RequestHeader("token") String token,
                                                           @RequestBody Map<String, Object> request) {

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("group_id", 1);
        result.put("groupName", request.get("name"));
        return ResponseUtils.buildSuccessfulResponse(result);

    }

    /**
     * request update group
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "/rest/mock/group", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> updateGroup(@RequestHeader("token") String token,
                                                           @RequestBody Map<String, Object> request) {

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("group_id", 1);
        result.put("groupName", "groupName1");
        return ResponseUtils.buildSuccessfulResponse(result);

    }

    /**
     * request delete group
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "/rest/mock/group", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> deleteGroup(@RequestHeader("token") String token,
                                                           @RequestParam("id") Long groupId) {

        if (groupId == null)
            throw new ErrorResponseException("deleteGroupException", "group_id is missing",
                    HttpStatus.BAD_REQUEST);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("status", 200);
        result.put("message", "group deleted");
        return ResponseUtils.buildSuccessfulResponse(result);

    }

}
