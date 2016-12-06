package com.provectus.proveng.service;

import com.provectus.proveng.domain.Group;
import com.provectus.proveng.domain.User;
import com.provectus.proveng.domain.dto.UserShortDto;
import com.provectus.proveng.exception.GroupServiceException;

import java.util.List;
import java.util.Set;

public interface GroupService {

    Group create(Group group);

    Group getById(long id);

    Group getByName(String name);

    Group getByLeader_idAndLevel(long leader_id, String level);

    Group update(Group group);

    void delete(Group group);

    Group getMainGroupForUser(User user);

    List<Group> getGroupsForUser(User user);

    void deleteById(Long id);

    void turnOffById(Long id);

    void deleteUserFromGroup(Long groupId, Long userId);

    List<Group> getGroupsForTeacher(User user);

    List<Group> getAllGroups();

    void checkIfExist(Long id) throws GroupServiceException;

    void deleteUsersFromGroup(Long groupId, Set<Long> set);

    Group refresh(Group group);

    void addUserToGroup(Long groupId, Long userId);

    List<UserShortDto> getMembersDtoForGroupById(long id);


}