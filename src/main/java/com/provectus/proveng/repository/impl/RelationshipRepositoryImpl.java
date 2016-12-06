package com.provectus.proveng.repository.impl;

import com.provectus.proveng.domain.Relationship;
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
public class RelationshipRepositoryImpl implements com.provectus.proveng.repository.RelationshipRepository {

    private static final Logger log = LogManager.getLogger(RelationshipRepositoryImpl.class);

    static {
        log.info(">>>Started RelationshipRepository...");
    }

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Save the relationship in the database.
     */
    @Override
    public void create(Relationship relationship) {
        log.debug("INJECTED relationship: " + relationship.toString());
        entityManager.persist(relationship);
    }

    //    ЗАМЕНА СТАТУТСА!!! Но не удалять. Удалить связи.
    @Override
    public void delete(Relationship relationship) {
        Serializable id = new Long(relationship.getId());
        Object persistentInstance = entityManager.find(Relationship.class, id);
        if (persistentInstance != null) {
            entityManager.remove(persistentInstance);
        }
    }

    /**
     * Return all the relationship stored in the database.
     */
    @Override
    @SuppressWarnings("unchecked")
    public List getAll() {
        return entityManager.createQuery("from Relationship").getResultList();
    }

    /**
     * Return the relationship having the passed type.
     */
    @Override
    public Relationship getByType(String type) {
        return (Relationship) entityManager.createQuery(
                "from Relationship where type = :type AND sys_status = 0")
                .setParameter("type", type)
                .getSingleResult();
    }

    /**
     * Return the relationship having the passed id.
     */
    @Override
    public Relationship getById(long id) {
        return entityManager.find(Relationship.class, id);
    }

    /**
     * Update the passed relationship in the database.
     */
    @Override
    public void update(Relationship relationship) {
        entityManager.merge(relationship);
    }
}
