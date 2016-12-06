package com.provectus.proveng.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.provectus.proveng.domain.Group;
import com.provectus.proveng.domain.User;
import com.provectus.proveng.domain.dto.GroupAllDto;
import com.provectus.proveng.domain.dto.GroupShortDto;
import com.provectus.proveng.domain.dto.UserShortDto;
import com.provectus.proveng.enumaration.LevelType;
import com.provectus.proveng.enumaration.RoleName;
import com.provectus.proveng.exception.ErrorResponseException;
import com.provectus.proveng.exception.GroupServiceException;
import com.provectus.proveng.exception.UserServiceException;
import com.provectus.proveng.service.GroupService;
import com.provectus.proveng.service.UserService;
import com.provectus.proveng.utils.ResponseUtils;
import com.provectus.proveng.utils.view.GroupView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class GroupController {

    private static Logger logger = LogManager.getLogger(UserController.class);

    static {
        logger.info(">> loaded GroupController");
    }

    @Autowired
    private GroupService groupService;
    @Autowired
    private UserService userService;
    @Value("${max_users.primary_group}")
    private int MAX_GROUP_MEMBERS;

    @RequestMapping(value = "/rest/v1/group", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(GroupView.AllInfoLevel.class)
    ResponseEntity<Map<String, Object>> createGroup(@RequestHeader("token") String token,
                                                    @RequestBody Group group) {

        logger.debug(">>> createGroup()");

        group = groupService.create(group);
        group = groupService.refresh(group);

        logger.debug("<<< createGroup()");
        return ResponseUtils.buildSuccessfulResponse(group);

    }

    @RequestMapping(value = "/rest/v1/group", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    // @JsonView(GroupView.AllInfoLevel.class)
    public ResponseEntity<Map<String, Object>> getGroup(@RequestHeader("token") String token,
                                                        @RequestParam(value = "id", required = false) Long groupId) {

        logger.debug(">>> getGroup()");
        Group group = null;

        Long userId = userService.getUserIdByToken(token);

        List<String> roleNames = userService.getRoleNamesForUser(userId);
        boolean isTeacher = false;
        boolean isAdmin = false;
        if (roleNames.contains(RoleName.ADMIN))
            isAdmin = true;
        if (roleNames.contains(RoleName.TEACHER))
            isTeacher = true;

        group = groupService.getById(groupId);

        List<UserShortDto> members = groupService.getMembersDtoForGroupById(group.getId());

        if (!(isTeacher || isAdmin)) {
            Long mId = -1L;
            for (UserShortDto m : members) {
                if ((mId = m.getId()) == userId)
                    break;
            }
            if ((mId != userId) && group.isMainGroup()) {
                throw new ErrorResponseException("getGroupError", "user can't see this group",
                        HttpStatus.BAD_REQUEST);
            }
        }

        GroupAllDto groupAllDto = GroupAllDto.convertToDto(group, members);

        logger.debug("<<< getGroup()");
        return ResponseUtils.buildSuccessfulResponse(groupAllDto);
    }

    @RequestMapping(value = "/rest/v1/groups", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    //@JsonView(GroupView.ShortInfoLevel.class)
    public ResponseEntity<Map<String, Object>> getGroups(@RequestHeader("token") String token) {

        logger.debug(">>> getGroups()");
        Long userId = userService.getUserIdByToken(token);

        List<String> roleNames = userService.getRoleNamesForUser(userId);
        List<Group> groupList = new ArrayList<>();
        List<GroupShortDto> groupDtoList = new ArrayList<>();

        boolean isTeacher = false;
        boolean isAdmin = false;
        if (roleNames.contains(RoleName.ADMIN))
            isAdmin = true;
        if (roleNames.contains(RoleName.TEACHER))
            isTeacher = true;

        User user = userService.getById(userId);
        if (isAdmin) {
            groupList = groupService.getAllGroups();
        } else if (isTeacher) {
            groupList = groupService.getGroupsForTeacher(user);
        } else {
            groupList = groupService.getGroupsForUser(user);
        }
        for (Group group : groupList) {
            GroupShortDto dto = GroupShortDto.convertToShortDto(group);
            groupDtoList.add(dto);
        }
        logger.debug("<<< getGroups()");
        return ResponseUtils.buildSuccessfulResponse(groupDtoList);
    }

    /**
     * request delete group
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "/rest/v1/group", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> deleteGroup(@RequestHeader("token") String token,
                                                           @RequestParam("id") Long id) {

        logger.debug(">>> deleteGroup()");

        try {
            groupService.checkIfExist(id);
        } catch (GroupServiceException e) {
            throw new ErrorResponseException("deleteGroupError", e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }

        groupService.turnOffById(id);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("status", 200);
        result.put("message", "group deleted");
        logger.debug(">>> deleteGroup()");
        return ResponseUtils.buildSuccessfulResponse(result);

    }

    /**
     * request update group
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "/rest/v1/group", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(GroupView.AllInfoLevel.class)
    public ResponseEntity<Map<String, Object>> updateGroup(@RequestHeader("token") String token,
                                                           @RequestBody Group group) {
        if (group.getId() == 0) {
            throw new ErrorResponseException("updateGroupError", "group id can't be '0'",
                    HttpStatus.BAD_REQUEST);
        }
        if (groupService.getById(group.getId()) == null) {
            throw new ErrorResponseException("updateGroupError", "group with this id doesn't exist",
                    HttpStatus.BAD_REQUEST);
        }
        if (group.getName().isEmpty())
            throw new ErrorResponseException("updateGroupError", "name is missing",
                    HttpStatus.BAD_REQUEST);
        if (group.getLevel().isEmpty())
            throw new ErrorResponseException("updateGroupError", "level is missing",
                    HttpStatus.BAD_REQUEST);
        User leader = userService.getById(group.getLeader().getId());
        if (leader == null)
            throw new ErrorResponseException("updateGroupError",
                    "leader with this id doesn't exist", HttpStatus.BAD_REQUEST);
        List<User> members = (List<User>) group.getMembers();
        List<Long> unrealMemberIds = new ArrayList<>();
        int number = 0;
        for (User u : members) {
            if (userService.getById(u.getId()) == null) {
                unrealMemberIds.add(u.getId());
            }
            number++;
        }

        if (number > MAX_GROUP_MEMBERS) {
            throw new ErrorResponseException("createGrouperror",
                    "members can't be greater than " + MAX_GROUP_MEMBERS, HttpStatus.BAD_REQUEST);
        }
        if (unrealMemberIds.size() > 0) {
            throw new ErrorResponseException("createGroupError",
                    "no users with id : " + unrealMemberIds, HttpStatus.BAD_REQUEST);
        }

        group = groupService.update(group);
        group = groupService.getById(group.getId());

        return ResponseUtils.buildSuccessfulResponse(group);

    }

    /**
     * @param token
     * @param groupId
     * @param body
     * @return
     */
    @RequestMapping(value = "/rest/v1/group_users", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> deleteUsersFromGroup(
            @RequestHeader("token") String token, @RequestParam("groupId") Long groupId,
            @RequestBody Map<String, Object>[] body) {

        try {
            groupService.checkIfExist(groupId);
        } catch (GroupServiceException e) {
            throw new ErrorResponseException("deleteUsersFromGroupError", e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }

        Map<Long, String> reasons = new HashMap<>();

        for (Map<String, Object> map : body) {

            Integer uId = (Integer) map.get("userId");
            if (uId == null)
                throw new ErrorResponseException("deleteUsersFromGroupError", "userId is missing",
                        HttpStatus.BAD_REQUEST);

            String note = (String) map.get("note");

            reasons.put(new Long(uId), note);

        }

        try {
            userService.checkIfExist(reasons.keySet());
        } catch (UserServiceException e) {
            throw new ErrorResponseException("deleteUsersFromGroupError", e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }

        groupService.deleteUsersFromGroup(groupId, reasons.keySet());

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("status", 200);
        result.put("message", "users deleted from group");
        return ResponseUtils.buildSuccessfulResponse(result);

    }

    /******
     * Rest call about English language levels for groups
     ******/
    @RequestMapping(value = "/rest/v1/levels", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getLevels(@RequestHeader("token") String token) {

        List<Map<String, Object>> result = new ArrayList<>();

        for (LevelType type : LevelType.values()) {
            if (type.getNumberValue() != 0) {
                Map<String, Object> levelObj = new HashMap<>();
                levelObj.put("name", type.getStringValue());
                levelObj.put("value", type.getNumberValue());
                result.add(levelObj);
            }
        }
        // result.put("name", LevelType.convertToString(intValue));
        return ResponseUtils.buildSuccessfulResponse(result);
    }
}
