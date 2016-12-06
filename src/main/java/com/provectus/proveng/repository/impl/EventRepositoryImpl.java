package com.provectus.proveng.repository.impl;

import com.provectus.proveng.domain.Event;
import com.provectus.proveng.enumaration.EventType;
import com.provectus.proveng.enumaration.SysStatus;
import org.apache.catalina.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class EventRepositoryImpl implements com.provectus.proveng.repository.EventRepository {

    private static final Logger log = LogManager.getLogger(EventRepositoryImpl.class);

    static {
        log.info(">>>Started EventRepository...");
    }

    @PersistenceContext
    private EntityManager entityManager;

    private Session session;


    /**
     * Save the event in the database.
     */
    @Override
    public void create(Event event) {
        log.debug("INJECTED event: " + event.toString());
        entityManager.persist(event);
        entityManager.flush();
    }

    //    ЗАМЕНА СТАТУТСА!!! Но не удалять. Удалить связи.
    @Override
    public void delete(Event event) {
        entityManager.merge(event);
    }

    @Override
    public void remove(Event event) {
        Serializable id = event.getId();
        Object persistentInstance = entityManager.find(Event.class, id);
        if (persistentInstance != null) {
            entityManager.remove(persistentInstance);
        }
    }

    /**
     * Return all the event stored in the database.
     */
    @Override
    @SuppressWarnings("unchecked")
    public List getAll(int limit) {
        return entityManager.createQuery("from Event where type != 'Lifetime' " +
                "AND type != 'Schedule' AND sys_status = :sys_status")
                .setParameter("sys_status", SysStatus.ON.getNumber())
                .setMaxResults(limit)
                .getResultList();
    }

    /**
     * Return all the event stored in the database.
     */
    @Override
    @SuppressWarnings("unchecked")
    public List getSchedule(Long group_id) {
        return entityManager.createQuery("from Event where group_id = :group_id AND (type = 'Lifetime' " +
                "OR type = 'Schedule') AND sys_status = :sys_status")
                .setParameter("sys_status", SysStatus.ON.getNumber())
                .setParameter("group_id", group_id)
                .getResultList();
    }

    /**
     * Return all the event for feed.
     */
    @Override
    @SuppressWarnings("unchecked")
    public List getFeed() {
        return entityManager.createQuery("from Event e where " +
                "(e.type = :workshop OR (e.type = :lesson AND e.dateStart < :tomorrow AND e.dateStart > :limitDate) " +
                "OR (e.type = :test AND e.dateStart < :today) " +
                "OR (e.type = :material AND e.dateStart < :today) " +
                "OR e.type = :notification) AND " +
                "e.sysStatus = :sys_status")
                .setParameter("sys_status", SysStatus.ON.getNumber())
                .setParameter("workshop", EventType.WORKSHOP)
                .setParameter("lesson", EventType.LESSON)
                .setParameter("test", EventType.TEST)
                .setParameter("material", EventType.MATERIAL)
                .setParameter("notification", EventType.NOTIFICATION)
                .setParameter("tomorrow", new Date(new Date().getTime() + 86400000)) // +24 hours
                .setParameter("today", new Date())
                .setParameter("limitDate", new Date(new Date().getTime() - 7776000000L)) //Three month
                .getResultList();
    }

    @Override
    public List getEventableForUser(String type) {
        return entityManager.createQuery("from Event e " +
                "where e.type = :typeItem " +
                "AND e.dateEnd > :today " +
                "AND e.sysStatus = :sys_status")
                .setParameter("sys_status", SysStatus.ON.getNumber())
                .setParameter("typeItem", type)
                .setParameter("today", new Date())
                .getResultList();
    }

    /**
     * Return all the event for calendar.
     */
    @Override
    @SuppressWarnings("unchecked")
    public List getCalendar(Date fromDate, Date toDate) {
        return entityManager.createQuery("from Event where (type = 'Workshop' " +
                "OR type = 'Lesson' OR type = 'Challenge') AND (dateStart >= :fromDate AND dateStart < :toDate) AND sys_status = :sys_status")
                .setParameter("sys_status", SysStatus.ON.getNumber())
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate)
                .getResultList();
    }

    /**
     * Return all the event by Group_id stored in the database.
     */
    @Override
    @SuppressWarnings("unchecked")
    public List getAllbyGroupId(Long group_id) {
        return entityManager.createQuery("from Event e where e.group.id = :group_id " +
                "AND e.type != 'Lifetime' AND e.type != 'Schedule' AND e.sysStatus = :sys_status")
                .setParameter("sys_status", SysStatus.ON.getNumber())
                .setParameter("group_id", group_id)
                .getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List getAllbyGroupIdSpec(Long group_id) {
        return entityManager.createQuery("from Event e where e.group.id = :group_id " +
                "AND e.sysStatus = :sys_status")
                .setParameter("sys_status", SysStatus.ON.getNumber())
                .setParameter("group_id", group_id)
                .getResultList();
    }

    /**
     * Return all the event by user_id stored in the database.
     */
    @Override
    @SuppressWarnings("unchecked")
    public List getAllbyUserId(Long user_id) {
        return entityManager.createQuery("from Event where user_id = :user_id " +
                "AND type != 'Lifetime' AND type != 'Schedule' AND sys_status = :sys_status")
                .setParameter("sys_status", SysStatus.ON.getNumber())
                .setParameter("user_id", user_id)
                .getResultList();
    }

    /**
     * Return all the event from date.
     */
    @Override
    @SuppressWarnings("unchecked")
    public List getAllfromDate(Date dateStart) {
        return entityManager.createQuery("from Event where date_start > :dateStart " +
                "AND type != 'Lifetime' AND type != 'Schedule' AND sys_status = :sys_status")
                .setParameter("sys_status", SysStatus.ON.getNumber())
                .setParameter("dateStart", dateStart)
                .getResultList();
    }

    /**
     * Return all the event to date.
     */
    @Override
    @SuppressWarnings("unchecked")
    public List getAlltoDate(Date dateEnd) {
        return entityManager.createQuery("from Event where date_end > :dateEnd " +
                "AND type != 'Lifetime' AND type != 'Schedule' AND sys_status = :sys_status")
                .setParameter("sys_status", SysStatus.ON.getNumber())
                .setParameter("dateEnd", dateEnd)
                .getResultList();
    }

    /**
     * Return all the event from last visited.
     */
    @Override
    @SuppressWarnings("unchecked")
    public List getAllfromLastVisited(Date lastVisited) {
        return entityManager.createQuery("from Event where modify_dtm > :lastVisited " +
                "AND type != 'Lifetime' AND type != 'Schedule' AND sys_status = :sys_status")
                .setParameter("sys_status", SysStatus.ON.getNumber())
                .setParameter("lastVisited", lastVisited)
                .getResultList();
    }

    /**
     * Return all the event by type stored in the database.
     */
    @Override
    @SuppressWarnings("unchecked")
    public List getAllbyType(String type) {
        return entityManager.createQuery("from Event where type = :type " +
                "AND type != 'Lifetime' AND type != 'Schedule' AND sys_status = :sys_status")
                .setParameter("sys_status", SysStatus.ON.getNumber())
                .setParameter("type", type)
                .getResultList();
    }

    /**
     * Return all the event by level stored in the database.
     */
    @Override
    @SuppressWarnings("unchecked")
    public List getAllbyLevel(String level) {
        return entityManager.createQuery("from Event where level = :level " +
                "AND type != 'Lifetime' AND type != 'Schedule' AND sys_status = :sys_status")
                .setParameter("sys_status", SysStatus.ON.getNumber())
                .setParameter("level", level)
                .getResultList();
    }

    /**
     * Return all the event by limit stored in the database.
     */
    @Override
    @SuppressWarnings("unchecked")
    public List getAllbyLimit(int limit) {
        return entityManager.createQuery("from Event where type != 'Lifetime' AND type != 'Schedule' " +
                "AND sys_status = :sys_status ORDER BY dateStart asc ")
                .setParameter("sys_status", SysStatus.ON.getNumber())
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public List getAllbyParent(Long parent_id) {
        return entityManager.createQuery("from Event e where e.parentEvent.id = :parent_id " +
                "AND e.sysStatus = :sys_status")
                .setParameter("sys_status", SysStatus.ON.getNumber())
                .setParameter("parent_id", parent_id)
                .getResultList();
    }

    /**
     * Return the event having the passed name.
     */
    @Override
    public Event getByName(String name) {
        return (Event) entityManager.createQuery("from Event where name = :name AND sys_status = :sys_status")
                .setParameter("sys_status", SysStatus.ON.getNumber())
                .setParameter("name", name)
                .getSingleResult();
    }

    /**
     * Return the event having the passed id.
     */
    @Override
    public Event findByIdForUpdate(long id) {
        return (Event) entityManager.createQuery("from Event where id = :id AND sys_status = :sys_status")
                .setParameter("sys_status", SysStatus.ON.getNumber())
                .setParameter("id", id)
                .getSingleResult();

    }

    /**
     * Return the event having the passed id.
     */
    @Override
    public Event getById(long id) {
        return entityManager.find(Event.class, id);

    }

    /**
     * Update the passed event in the database.
     */
    @Override
    public void update(Event event) {
        entityManager.merge(event);
    }
}
