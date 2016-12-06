package com.provectus.proveng.repository.impl;

import com.provectus.proveng.domain.DayBook;
import com.provectus.proveng.enumaration.SysStatus;
import com.provectus.proveng.repository.DayBookRepository;
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
public class DayBookRepositoryImpl implements DayBookRepository {

    private static final Logger log = LogManager.getLogger(DayBookRepositoryImpl.class);

    static {
        log.info(">>>Started DayBookRepository...");
    }

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Save the dayBook in the database.
     */
    @Override
    public DayBook create(DayBook dayBook) {
        log.debug("INJECTED dayBook: " + dayBook.toString());
        entityManager.persist(dayBook);
        return dayBook;
    }

    //    ЗАМЕНА СТАТУТСА!!! Но не удалять. Удалить связи.
    @Override
    public void delete(DayBook dayBook) {
        Serializable id = new Long(dayBook.getId());
        Object persistentInstance = entityManager.find(DayBook.class, id);
        if (persistentInstance != null) {
            entityManager.remove(persistentInstance);
        }
    }

    /**
     * Return all the dayBook stored in the database.
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<DayBook> getAll() {
        return entityManager.createQuery("from DayBook").getResultList();
    }

    @Override
    public List<DayBook> getAllUsersDaybook(long user_id) {
        return entityManager.createQuery("from DayBook d where d.user.id = :user_id " +
                "AND d.sysStatus = :sys_status")
                .setParameter("sys_status", SysStatus.ON.getNumber())
                .setParameter("user_id", user_id)
                .getResultList();
    }

    /**
     * Return the dayBook having the passed name.
     */
    @Override
    public DayBook getByType(String type) {
        return (DayBook) entityManager.createQuery(
                "from DayBook where type = :type AND sys_status = 0")
                .setParameter("type", type)
                .getSingleResult();
    }

    /**
     * Return the dayBook having the passed id.
     */
    @Override
    public DayBook getById(long id) {
        return entityManager.find(DayBook.class, id);
    }

    /**
     * Update the passed dayBook in the database.
     */
    @Override
    public void update(DayBook dayBook) {
        entityManager.merge(dayBook);
    }

    @Override
    public DayBook refresh(DayBook dayBook) {

        entityManager.refresh(dayBook);
        return dayBook;
    }

    @Override
    public void turnOffDayBook(long id) {
        DayBook mark = entityManager.find(DayBook.class, id);
        mark.setSysStatus(SysStatus.OFF.getNumber());

    }
}
