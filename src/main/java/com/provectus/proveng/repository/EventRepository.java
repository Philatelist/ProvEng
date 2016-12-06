package com.provectus.proveng.repository;

import com.provectus.proveng.domain.Event;

import java.util.Date;
import java.util.List;

public interface EventRepository {

    void create(Event event);

    void delete(Event event);

    void remove(Event event);

    List getAll(int limit);

    List getSchedule(Long group_id);

    List getFeed();

    List getEventableForUser(String type);

    List getCalendar(Date fromDate, Date toDate);

    List getAllbyGroupId(Long group_id);

    List getAllbyGroupIdSpec(Long group_id);

    List getAllbyUserId(Long user_id);

    List getAllfromDate(Date dateStart);

    List getAlltoDate(Date dateEnd);

    List getAllfromLastVisited(Date lastVisited);

    List getAllbyType(String type);

    List getAllbyLevel(String level);

    List getAllbyLimit(int limit);

    List getAllbyParent(Long parent_id);

    Event getByName(String name);

    Event findByIdForUpdate(long id);

    Event getById(long id);

    void update(Event event);
}
