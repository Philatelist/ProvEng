package com.provectus.proveng.service.impl;

import com.provectus.proveng.domain.Group;
import com.provectus.proveng.domain.User;
import com.provectus.proveng.domain.dto.UserShortDto;
import com.provectus.proveng.exception.ErrorResponseException;
import com.provectus.proveng.exception.GroupServiceException;
import com.provectus.proveng.repository.GroupRepository;
import com.provectus.proveng.service.GroupService;
import com.provectus.proveng.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service("groupService")

public class GroupServiceImpl implements GroupService {

    private static Logger logger = LogManager.getLogger(GroupServiceImpl.class);
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserService userService;

    @Value("${max_users.primary_group}")
    private int MAX_GROUP_MEMBERS;

    @Override
    public Group create(Group group) {
        logger.debug(">>> createGroup()");
        User leader = userService.getById(group.getLeader().getId());
        if (leader == null)
            throw new ErrorResponseException("createGroupError",
                    "leader with this id doesn't exist", HttpStatus.BAD_REQUEST);
        if (group.getName().isEmpty())
            throw new ErrorResponseException("createGroupError", "name is missing",
                    HttpStatus.BAD_REQUEST);
        if (group.getLevel().isEmpty())
            throw new ErrorResponseException("createGroupError", "level is missing",
                    HttpStatus.BAD_REQUEST);
        Collection<User> members = group.getMembers();
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
        groupRepository.create(group);

        return group;
    }

    @Override
    public Group getById(long id) {

        Group group = groupRepository.getById(id);
        if (group == null) {
            throw new ErrorResponseException("getGroupError", "group whith this id doesn't exist",
                    HttpStatus.NOT_FOUND);
        }
        return group;
    }

    @Override
    public Group getByName(String name) {

        Group group = groupRepository.getByName(name);
        return group;
    }

    @Override
    public Group getByLeader_idAndLevel(long leader_id, String level) {

        Group group = groupRepository.getByLeader_idAndLevel(leader_id, level);
        return group;
    }

    @Override
    public Group update(Group group) {
        logger.debug(">>> update())");

        groupRepository.update(group);

        logger.debug("<<< update()");
        return group;
    }

    @Override
    public void delete(Group group) {

        groupRepository.delete(group);
    }

    @Override
    public Group getMainGroupForUser(User user) {
        Group group = null;
        if (user.getGroups().isEmpty()) {
            throw new ErrorResponseException("getGroupError", "no main group for user",
                    HttpStatus.BAD_REQUEST);
        } else {
            while (user.getGroups().iterator().hasNext()) {
                if ((group = user.getGroups().iterator().next()).isMainGroup()) {
                    break;
                } else {
                    group = null;
                }
            }
        }
        return group;
    }

    @Override
    public void deleteById(Long id) {
        // TODO Auto-generated method stub
    }

    @Override
    public void turnOffById(Long id) {

        Group group = this.getById(id);

        if (group == null) {
            throw new ErrorResponseException("deleteGroupError", "group with this id doesn't exist",
                    HttpStatus.BAD_REQUEST);
        } else {
            groupRepository.turnOffGroup(group);
        }

    }

    @Override
    public void deleteUserFromGroup(Long groupId, Long userId) {

        groupRepository.deleteUserFromGroup(groupId, userId);

    }

    @Override
    public List<Group> getGroupsForTeacher(User user) {

        return groupRepository.getGroupForTeacher(user);
    }

    @Override
    public List<Group> getGroupsForUser(User user) {

        return groupRepository.getGroupsForUser(user);
    }

    @Override
    public List<Group> getAllGroups() {

        return groupRepository.getAllActiveGroup();
    }

    @Override
    public void checkIfExist(Long id) throws GroupServiceException {
        if (this.getById(id) == null) {
            throw new GroupServiceException("group with this id doesn't exist");
        }

    }

    @Override
    public void deleteUsersFromGroup(Long groupId, Set<Long> usersIdList) {

        for (Long uId : usersIdList) {
            groupRepository.deleteUserFromGroup(groupId, uId);
        }

    }

    @Override
    public Group refresh(Group group) {
        return groupRepository.refres(group);
    }

    @Override
    public void addUserToGroup(Long groupId, Long userId) {
        groupRepository.addUserToGroup(groupId, userId);

    }

    @Override
    public List<UserShortDto> getMembersDtoForGroupById(long id) {
        return groupRepository.getMembersDtoForGroupById(id);

    }

}
