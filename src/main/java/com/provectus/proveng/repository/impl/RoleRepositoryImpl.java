package com.provectus.proveng.repository.impl;

import com.provectus.proveng.domain.Role;
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
public class RoleRepositoryImpl implements com.provectus.proveng.repository.RoleRepository {

    private static final Logger log = LogManager.getLogger(RoleRepositoryImpl.class);

    static {
        log.info(">>>Started RoleRepository...");
    }

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Save the role in the database.
     */
    @Override
    public void create(Role role) {
        log.debug("INJECTED user: " + role.toString());
        entityManager.persist(role);
    }

    //    ЗАМЕНА СТАТУТСА!!! Но не удалять. Удалить связи.
    @Override
    public void delete(Role role) {
        Serializable id = new Long(role.getId());
        Object persistentInstance = entityManager.find(Role.class, id);
        if (persistentInstance != null) {
            entityManager.remove(persistentInstance);
        }
    }

    /**
     * Return all the roles stored in the database.
     */
    @Override
    @SuppressWarnings("unchecked")
    public List getAll() {
        return entityManager.createQuery("from Role").getResultList();
    }

    /**
     * Return the role having the passed name.
     */
    @Override
    public Role getByName(String name) {
        return (Role) entityManager.createQuery(
                "from Role where name = :name AND sys_status = 0")
                .setParameter("name", name)
                .getSingleResult();
    }

    /**
     * Return the role having the passed id.
     */
    @Override
    public Role getById(long id) {
        return entityManager.find(Role.class, id);
    }

    /**
     * Update the passed role in the database.
     */
    @Override
    public void update(Role role) {
        entityManager.merge(role);
    }
}
