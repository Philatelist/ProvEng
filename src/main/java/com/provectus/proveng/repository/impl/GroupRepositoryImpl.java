package com.provectus.proveng.repository.impl;

import com.provectus.proveng.domain.Group;
import com.provectus.proveng.domain.User;
import com.provectus.proveng.domain.dto.UserShortDto;
import com.provectus.proveng.enumaration.SysStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class GroupRepositoryImpl implements com.provectus.proveng.repository.GroupRepository {

    private static final Logger logger = LogManager.getLogger(GroupRepositoryImpl.class);

    static {
        logger.info(">>>loaded GroupRepository...");
    }

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Save the group in the database.
     */
    @Override
    public void create(Group group) {
        logger.debug(">>> createGroup()");
        entityManager.persist(group);

        logger.debug("<<< createGroup()");
    }

    // ЗАМЕНА СТАТУТСА!!! Но не удалять. Удалить связи.
    @Override
    public void delete(Group group) {
        Serializable id = new Long(group.getId());
        Object persistentInstance = entityManager.find(Group.class, id);
        if (persistentInstance != null) {
            entityManager.remove(persistentInstance);
        }
    }

    /**
     * Return all the groups stored in the database.
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Group> getAll() {
        return entityManager.createQuery("from Group").getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Group> getAllActiveGroup() {

        return entityManager.createQuery("from Group where sysStatus = :status")
                .setParameter("status", SysStatus.ON.getNumber()).getResultList();
    }

    /**
     * Return the groups having the passed name.
     */
    @Override
    public Group getByName(String name) {
        return (Group) entityManager.createQuery("from Group where name = :name AND sys_status = 0")
                .setParameter("name", name).getSingleResult();
    }

    /**
     * Return the groups having the passed leader_id and level.
     */
    @Override
    public Group getByLeader_idAndLevel(long leader_id, String level) {
        return (Group) entityManager
                .createQuery("from Group " + "where leader_id = :leader_id " + "AND sysStatus = 0 "
                        + "AND level = :level")
                .setParameter("leader_id", leader_id).setParameter("level", level)
                .getSingleResult();
    }

    /**
     * Return the groups having the passed id.
     */
    @Override
    public Group getById(long id) {
        return entityManager.find(Group.class, id);
    }

    /**
     * Update the passed group in the database.
     *
     * @return
     */

    @Override
    public void update(Group fromGroup) {
        logger.debug(">>> update()");

        entityManager.merge(fromGroup);

        logger.debug("<<< update()");

    }

    @Override
    public void turnOffGroup(Group group) {
        logger.debug(">>> turnOffGroup()");

        group.setSysStatus(SysStatus.OFF.getNumber());
        this.update(group);
    }

    @Override
    public void deleteUserFromGroup(Long groupId, Long userId) {

        Group group = entityManager.find(Group.class, groupId);

        User user = entityManager.find(User.class, userId);

        group.getMembers().remove(user);

    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Group> getGroupForTeacher(User user) {
        return entityManager
                .createQuery("from Group where sysStatus = :status and leader = :leader")
                .setParameter("status", SysStatus.ON.getNumber()).setParameter("leader", user)
                .getResultList();

    }

    @Override
    public List<Group> getGroupsForUser(User user) {
        entityManager.refresh(user);
        List<Group> groups = (List<Group>) user.getGroups();
        List<Group> activeGroups = new ArrayList<>();
        for (Group group : groups) {
            if (group.getSysStatus() == SysStatus.ON.getNumber()) {
                activeGroups.add(group);
            }

        }
        return activeGroups;
    }

    @Override
    public Group refres(Group group) {
        entityManager.refresh(group);
        return group;
    }

    @Override
    public void addUserToGroup(Long groupId, Long userId) {

        Group group = entityManager.find(Group.class, groupId);

        User user = entityManager.find(User.class, userId);

        group.getMembers().add(user);

    }

    @Override
    public List<UserShortDto> getMembersDtoForGroupById(long id) {

        @SuppressWarnings("unchecked")
        List<Object[]> members = entityManager
                .createNativeQuery(
                        "SELECT u.id, u.firstname, u.lastname, u.url FROM po_user u "
                                + " JOIN  usergroup ON u.id = user_id WHERE group_id = ?")
                .setParameter(1, id).getResultList();

        List<UserShortDto> dtoMembers = new ArrayList<>();
        for (Object[] member : members) {
            dtoMembers.add(new UserShortDto(((BigInteger) member[0]).longValue(),
                    (String) member[1], (String) member[2], (String) member[3]));
        }
        return dtoMembers;
    }

}
