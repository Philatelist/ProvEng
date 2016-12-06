package com.provectus.proveng.repository.impl;

import com.provectus.proveng.domain.User;
import com.provectus.proveng.enumaration.DaybookType;
import com.provectus.proveng.enumaration.SysStatus;
import com.provectus.proveng.exception.ErrorResponseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

@Repository
@Transactional
public class UserRepositoryImpl implements com.provectus.proveng.repository.UserRepository {

    private static final Logger log = LogManager.getLogger(UserRepositoryImpl.class);

    static {
        log.info(">>>Started UserRepository...");
    }

    // An EntityManager will be automatically injected from entityManagerFactory
    // setup on DatabaseConfig class.
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Save the user in the database.
     */
    @Override
    public void create(User user) {
        log.debug("INJECTED user: " + user.toString());
        entityManager.persist(user);
        entityManager.flush();
    }

    /**
     * Delete the user from the database.
     */
    // public void delete(User user) {
    // if (entityManager.contains(user)) {
    // entityManager.remove(user);
    // log.debug("DELETED user: " + user.toString());
    // } else {
    // entityManager.remove(entityManager.merge(user));
    // log.debug("DELETED merged user: " + user.toString());
    // }
    // }

    // ЗАМЕНА СТАТУТСА!!! Но не удалять. Удалить связь с френдом и удалить
    // френда из листа
    @Override
    public void changeStatus(User user) {
        Serializable id = user.getId();
        Object persistentInstance = entityManager.find(User.class, id);
        if (persistentInstance != null) {
            entityManager.merge(persistentInstance);
        }
    }

    @Override
    public void delete(User user) {
        Serializable id = user.getId();
        Object persistentInstance = entityManager.find(User.class, id);
        if (persistentInstance != null) {
            entityManager.remove(persistentInstance);
        }
    }

    /**
     * Return all the users stored in the database.
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAll() {
        return entityManager.createQuery("from User where sys_status = :sysStatus")
                .setParameter("sysStatus", SysStatus.ON.getNumber()).getResultList();
    }

    /**
     * Return all the users stored in the database by level.
     */
    @Override
    public List getByLevel(String level) {
        return entityManager
                .createQuery(
                        "from User u left join fetch u.dayBooks d where u.level= :level AND u.sysStatus = :sys_status AND d.type = :typeDaybook AND d.mark >= 0")
                .setParameter("sys_status", SysStatus.ON.getNumber())
                .setParameter("typeDaybook", DaybookType.START_TEST.getDaybookType())
                .setParameter("level", level).getResultList();
    }

    /**
     * Return all the users stored in the database by roleName.
     */
    @Override
    public List getByRole(String roleName) {
        return entityManager
                .createQuery(
                        "from User u left JOIN fetch u.roles r where r.name = :roleName AND u.sysStatus = :sysStatus")
                .setParameter("sysStatus", SysStatus.ON.getNumber())
                .setParameter("roleName", roleName).getResultList();
    }

    /**
     * Return all the users stored in the database by level.
     */
    @Override
    public List getByPassedStartLevel() {
        return entityManager
                .createQuery(
                        "from User u left JOIN fetch u.dayBooks d where u.sysStatus = :sys_status AND d.type = :typeDaybook AND d.mark >= 0")
                .setParameter("sys_status", SysStatus.ON.getNumber())
                .setParameter("typeDaybook", DaybookType.START_TEST.getDaybookType())
                .getResultList();
    }

    /**
     * Return the user having the passed email.
     */
    @Override
    public User getByEmail(String email) {
        return (User) entityManager
                .createQuery("from User where email = :email AND sys_status = :sysStatus")
                .setParameter("sysStatus", SysStatus.ON.getNumber()).setParameter("email", email)
                .getSingleResult();
    }

    /**
     * Return the user having the passed id.
     */
    @Override
    public User getById(long id) {
        return entityManager.find(User.class, id);
    }

    /**
     * Update the passed user in the database.
     */
    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    public User saveOrUpdate(User user) {
        return (entityManager.merge(user));
    }

    @Override
    public Long getUserIdByToken(String token) {
        Query query = entityManager
                .createNativeQuery("SELECT user_id FROM loginhistory WHERE jsession_id = ?");
        query.setParameter(1, token);
        BigInteger bid = null;
        try {
            bid = (BigInteger) query.getSingleResult();
        } catch (NoResultException e) {
            throw new ErrorResponseException("sessionError", "no active session for token",
                    HttpStatus.BAD_REQUEST);
        }
        Long id = bid.longValue();

        return id;
    }

    @Override
    public List<String> getRoleNamesForUser(Long id) {
        Query query = entityManager.createNativeQuery(
                "SELECT name FROM role JOIN userrole ON role.id = userrole.role_id "
                        + "WHERE userrole.user_id = ?");
        query.setParameter(1, id);
        @SuppressWarnings("unchecked")
        List<String> names = query.getResultList();

        return names;
    }

}