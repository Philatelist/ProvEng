package com.provectus.proveng.repository.impl;

import com.provectus.proveng.domain.Developer;
import com.provectus.proveng.enumaration.SysStatus;
import com.provectus.proveng.repository.DeveloperRepository;
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
public class DeveloperRepositoryImpl implements DeveloperRepository {

    private static final Logger log = LogManager.getLogger(DeveloperRepositoryImpl.class);

    static {
        log.info(">>>Started DeveloperRepository...");
    }

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Save the developer in the database.
     */
    @Override
    public Developer create(Developer developer) {
        log.debug("INJECTED developer: " + developer.toString());
        entityManager.persist(developer);
        return developer;
    }

    @Override
    public void delete(Developer developer) {
        Serializable id = developer.getId();
        Object persistentInstance = entityManager.find(Developer.class, id);
        if (persistentInstance != null) {
            entityManager.remove(persistentInstance);
        }
    }

    /**
     * Return the developer having the passed id.
     */
    @Override
    public Developer getById(long id) {
        return entityManager.find(Developer.class, id);
    }

    @Override
    public List<Developer> getByEmail(String email) {
        return entityManager.createQuery(
                "from Developer where email = :email AND sys_status = 0")
                .setParameter("email", email)
                .getResultList();
    }

    /**
     * Update the passed developer in the database.
     */
    @Override
    public void update(Developer developer) {
        entityManager.merge(developer);
    }

    @Override
    public Developer refresh(Developer developer) {
        entityManager.refresh(developer);
        return developer;
    }

    @Override
    public List<Developer> getAllDevelopers() {
        return entityManager.createQuery("from Developer where sys_status = 0").getResultList();
    }

    @Override
    public List<Developer> checkIfDeveloper(String email) {
        return entityManager.createQuery("from Developer where email = :email AND sys_status = 0")
                .setParameter("email", email)
                .getResultList();
    }

    @Override
    public void turnOffDeveloper(String email) {
        Developer mark = (Developer) entityManager.createQuery(
                "from Developer where email = :email AND sys_status = 0")
                .setParameter("email", email)
                .getSingleResult();
        if (mark != null) {
            mark.setSysStatus(SysStatus.OFF.getNumber());
        }

    }
}
