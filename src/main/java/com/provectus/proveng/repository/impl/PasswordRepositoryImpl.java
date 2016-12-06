package com.provectus.proveng.repository.impl;

import com.provectus.proveng.domain.Password;
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
public class PasswordRepositoryImpl implements com.provectus.proveng.repository.PasswordRepository {

    private static final Logger log = LogManager.getLogger(PasswordRepositoryImpl.class);

    static {
        log.info(">>>Started PasswordRepository...");
    }

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Save the password in the database.
     */
    @Override
    public void create(Password password) {
        log.debug("INJECTED password: " + password.toString());
        entityManager.persist(password);
    }

    //    ЗАМЕНА СТАТУТСА!!! Но не удалять. Удалить связи.
    @Override
    public void delete(Password password) {
        Serializable id = new Long(password.getId());
        Object persistentInstance = entityManager.find(Password.class, id);
        if (persistentInstance != null) {
            entityManager.remove(persistentInstance);
        }
    }

    /**
     * Return all the password stored in the database.
     */
    @Override
    @SuppressWarnings("unchecked")
    public List getAll() {
        return entityManager.createQuery("from Password").getResultList();
    }

    /**
     * Return the password having the passed id.
     */
    @Override
    public Password getById(long id) {
        return entityManager.find(Password.class, id);
    }

    /**
     * Return the dayBook having the passed name.
     */
    @Override
    public Password getByPassword(String password) {
        return (Password) entityManager.createQuery(
                "from Password where password = :password AND sys_status = 0")
                .setParameter("password", password)
                .getSingleResult();
    }

    /**
     * Update the passed password in the database.
     */
    @Override
    public void update(Password password) {
        entityManager.merge(password);
    }
}
