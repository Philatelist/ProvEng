package com.provectus.proveng.repository.impl;

import com.provectus.proveng.domain.Location;
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
public class LocationRepositoryImpl implements com.provectus.proveng.repository.LocationRepository {

    private static final Logger log = LogManager.getLogger(LocationRepositoryImpl.class);

    static {
        log.info(">>>Started LocationRepository...");
    }

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Save the location in the database.
     */
    @Override
    public void create(Location location) {
        log.debug("INJECTED location: " + location.toString());
        entityManager.persist(location);
    }

    //    ЗАМЕНА СТАТУТСА!!! Но не удалять. Удалить связи.
    @Override
    public void delete(Location location) {
        Serializable id = new Long(location.getId());
        Object persistentInstance = entityManager.find(Location.class, id);
        if (persistentInstance != null) {
            entityManager.remove(persistentInstance);
        }
    }

    /**
     * Return all the location stored in the database.
     */
    @Override
    @SuppressWarnings("unchecked")
    public List getAll() {
        return entityManager.createQuery("from Location").getResultList();
    }

    /**
     * Return the location having the passed name.
     */
    @Override
    public Location getByName(String name) {
        return (Location) entityManager.createQuery(
                "from Location where name = :name AND sys_status = 0")
                .setParameter("name", name)
                .getSingleResult();
    }

    /**
     * Return the location having the passed id.
     */
    @Override
    public Location getById(long id) {
        return entityManager.find(Location.class, id);
    }

    /**
     * Update the passed location in the database.
     */
    @Override
    public void update(Location location) {
        entityManager.merge(location);
    }
}
