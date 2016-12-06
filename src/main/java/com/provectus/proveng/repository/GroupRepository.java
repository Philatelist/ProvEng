package com.provectus.proveng.repository;

import com.provectus.proveng.domain.Group;
import com.provectus.proveng.domain.User;
import com.provectus.proveng.domain.dto.UserShortDto;

import java.util.List;

public interface GroupRepository {

    void create(Group group);

    void delete(Group group);

    List<Group> getAll();

    List<Group> getAllActiveGroup();

    Group getByName(String name);

    Group getByLeader_idAndLevel(long leader_id, String level);

    Group getById(long id);

    void update(Group fromGroup);

    void turnOffGroup(Group group);

    void deleteUserFromGroup(Long groupId, Long userId);

    List<Group> getGroupForTeacher(User user);

    List<Group> getGroupsForUser(User user);

    Group refres(Group group);

    void addUserToGroup(Long groupId, Long userId);

    List<UserShortDto> getMembersDtoForGroupById(long id);
}
