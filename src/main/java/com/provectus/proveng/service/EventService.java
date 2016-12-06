package com.provectus.proveng.service;

import com.provectus.proveng.domain.Event;
import com.provectus.proveng.domain.Material;
import com.provectus.proveng.domain.Test;
import com.provectus.proveng.domain.User;
import com.provectus.proveng.exception.TestServiceException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("eventService")
public interface EventService {

    Event create(Event event);

    Event getById(long id);

    Event findByIdForUpdate(long id);

    Event getByName(String name);

    List<Event> getAll(int limit);

    List<Event> getEventUsersStatus(Long event_id);

    List<Material> getMaterialsForUser(Long user_id);

    List<Test> getTestsForUser(Long user_id) throws TestServiceException;

    List getSchedule(Long group_id);

    List<Event> getFeed(User user);

    List<Event> getCalendar(User user, Date fromDate, Date toDate);

    List<Event> getAllbyGroupId(Long group_id);

    List<Event> getAllbyGroupIdSpec(Long group_id);

    List<Event> getAllbyUserId(Long user_id);

    List<Event> getAllfromDate(Date fromDate);

    List<Event> getAlltoDate(Date toDate);

    List<Event> getAllfromLastVisited(Date lastVisited);

    List<Event> getAllbyType(String type);

    List getAllbyLevel(String level);

    List<Event> getAllbyLimit(int limit);

    List<Event> getAllbyParent(Long parent_id);

    Event update(Event event);

    void delete(Event event);

    void remove(Event event);


}
