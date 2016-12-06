package com.provectus.proveng.repository.impl;

import com.provectus.proveng.domain.Permission;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Repository
@Transactional
public class PermissionRepositoryImpl implements com.provectus.proveng.repository.PermissionRepository {

    private static final Logger log = LogManager.getLogger(PermissionRepositoryImpl.class);

    static {
        log.info(">>>Started PermissionRepository...");
    }

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Save the permission in the database.
     */
    @Override
    public void create(Permission permission) {
        log.debug("INJECTED permission: " + permission.toString());
        entityManager.persist(permission);
    }

    //    ЗАМЕНА СТАТУТСА!!! Но не удалять. Удалить связи.
    @Override
    public void delete(Permission permission) {
        Serializable id = new Long(permission.getId());
        Object persistentInstance = entityManager.find(Permission.class, id);
        if (persistentInstance != null) {
            entityManager.remove(persistentInstance);
        }
    }

    /**
     * Return all the permissions stored in the database.
     */
    @Override
    @SuppressWarnings("unchecked")
    public List getAll() {
        return entityManager.createQuery("from Permission").getResultList();
    }

    /**
     * Return the permission having the passed name.
     */
    @Override
    public Permission getByName(String name) {
        return (Permission) entityManager.createQuery(
                "from Permission where name = :name AND sys_status = 0")
                .setParameter("name", name)
                .getSingleResult();
    }

    /**
     * Return the permission having the passed name.
     */
    @Override
    public Permission getByNameAndFlagAndRole(String name, String flag, int roleId) {
        return (Permission) entityManager.createQuery(
                "from Permission " +
                        "where name = :name " +
                        "AND access_flag = :flag " +
                        "AND sysStatus = 0 " +
                        "AND role_id = :roleId")
                .setParameter("name", name)
                .setParameter("flag", flag)
                .setParameter("roleId", roleId)
                .getSingleResult();
    }

//    public List<Permission> findFirst(String name) {
//        List<Permission> permissions = entityManager.findFirstByName(name);
//    }

    /**
     * Return the permission having the passed id.
     */
    @Override
    public Permission getById(long id) {
        return entityManager.find(Permission.class, id);
    }

    /**
     * Update the passed permission in the database.
     */
    @Override
    public void update(Permission permission) {
        entityManager.merge(permission);
    }
}
