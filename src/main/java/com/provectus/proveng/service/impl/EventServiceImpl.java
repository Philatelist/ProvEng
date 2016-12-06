package com.provectus.proveng.service.impl;

import com.provectus.proveng.domain.*;
import com.provectus.proveng.enumaration.EventType;
import com.provectus.proveng.enumaration.RoleName;
import com.provectus.proveng.exception.TestServiceException;
import com.provectus.proveng.repository.EventRepository;
import com.provectus.proveng.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("eventService")
public class EventServiceImpl implements EventService {

    private static Logger logger = LogManager.getLogger(EventServiceImpl.class);
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private GroupService groupService;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private TestService testService;

    @Autowired
    private UserService userService;

    public Event create(Event event) {
        eventRepository.create(event);
        return event;
    }

    public Event getById(long id) {

        return eventRepository.getById(id);
    }

    public Event findByIdForUpdate(long id) {

        return eventRepository.getById(id);
    }

    public Event getByName(String name) {

        return eventRepository.getByName(name);
    }

    public List<Event> getAll(int limit) {

        return eventRepository.getAll(limit);
    }

    public List<Event> getEventUsersStatus(Long id) {

        List<Event> events = (List<Event>) eventRepository.getById(id).getChildEvents();
        List<Event> result = new ArrayList<>();
        for (Event event : events) {
            if (event.getSysStatus() == 0 && (event.getType().equals("Accepted") || event.getType().equals("Denied") || event.getType().equals("Visited"))) {
                result.add(event);
            }
        }
        return result;
    }

    public List<Material> getMaterialsForUser(Long user_id) {

        List<Event> events = eventRepository.getEventableForUser(EventType.MATERIAL);
        List<Group> groupsForUser = new ArrayList<>(groupService.getGroupsForUser(userService.getById(user_id)));
        List<Material> result = new ArrayList<>();
        for (Event event : events) {
            Material m = (Material) event.getEventableItem();
            if (groupsForUser.contains(event.getGroup()) || event.getGroup() == null) {
                result.add(materialService.getActiveById(m.getId()));
            }
        }

        for (int i = 0; i < result.size(); i++) {
            for (int j = result.size() - 1; j >= i + 1; --j) {
                if (result.get(i).getId() == result.get(j).getId()) {
                    result.remove(j);
                }
            }
        }
        return result;
    }

    public List<Test> getTestsForUser(Long user_id) throws TestServiceException {

        List<Event> events = eventRepository.getEventableForUser(EventType.TEST);
        List<Group> groupsForUser = new ArrayList<>(groupService.getGroupsForUser(userService.getById(user_id)));
        List<Test> result = new ArrayList<>();
        for (Event event : events) {
            Test test = (Test) event.getEventableItem();
            if (groupsForUser.contains(event.getGroup()) || event.getGroup() == null) {
                result.add(testService.getActiveById(test.getId()));
            }
        }

        for (int i = 0; i < result.size(); i++) {
            for (int j = result.size() - 1; j >= i + 1; --j) {
                if (result.get(i).getId() == result.get(j).getId()) {
                    result.remove(j);
                }
            }
        }
        return result;
    }

    public List<Event> getSchedule(Long group_id) {

        return eventRepository.getSchedule(group_id);
    }

    public List<Event> getFeed(User user) {


        List<Event> events = eventRepository.getFeed();
        List<Group> groupsForUser = new ArrayList<>(groupService.getGroupsForUser(user));
        List<Event> middleEvents = new ArrayList<>();

        for (Event event : events) {
            if (event.getType().equals(EventType.WORKSHOP) && !middleEvents.contains(event)) {
                middleEvents.add(event);
            }
            for (Group group : groupsForUser) {
                if (event.getGroup() == group && !middleEvents.contains(event)) {
                    middleEvents.add(event);
                }
            }
        }
        List<Event> result = new ArrayList<>();
        for (Event event : middleEvents) {
            if (event.getChildEvents().size() > 0) {
                List<Event> childsEvent = (List<Event>) event.getChildEvents();
                List<Event> childs = new ArrayList<>();
                for (Event child : childsEvent) {
                    if (child.getSysStatus() == 0) {
                        childs.add(child);
                    }
                }
                event.removeAllEventChilds();

                List<Event> resultChilds = new ArrayList<>();
                for (Event child : childs) {
                    if (child.getCreater() != null && user.getId() == child.getCreater().getId() && child.getSysStatus() == 0) {
                        resultChilds.add(child);
                    }
                }
                event.setChildEvents(resultChilds);
            }
            result.add(event);
        }
        return result;
    }

    public List<Event> getCalendar(User user, Date fromDate, Date toDate) {

        List<Group> result = null;
        List<Role> roles = (List<Role>) user.getRoles();
        for (Role role : roles) {
            if (role.getName().equals(RoleName.STUDENT)) {
                result = groupService.getGroupsForUser(user);
            }
            if (role.getName().equals(RoleName.TEACHER)) {
                result = groupService.getGroupsForTeacher(user);
            }
        }

        List<Event> events = eventRepository.getCalendar(fromDate, toDate);
        List<Event> returnResult = new ArrayList<>();
        for (Event event : events) {
            for (Group group : result) {
                if (event.getGroup() == group) {
                    returnResult.add(event);
                }
            }
        }

        return returnResult;
    }

    public List<Event> getAllbyGroupId(Long group_id) {

        return eventRepository.getAllbyGroupId(group_id);
    }

    public List<Event> getAllbyGroupIdSpec(Long group_id) {

        return eventRepository.getAllbyGroupIdSpec(group_id);
    }

    public List<Event> getAllbyUserId(Long user_id) {

        return eventRepository.getAllbyUserId(user_id);
    }

    public List<Event> getAllfromDate(Date fromDate) {

        return eventRepository.getAllfromDate(fromDate);
    }

    public List<Event> getAlltoDate(Date toDate) {

        return eventRepository.getAlltoDate(toDate);
    }

    public List<Event> getAllfromLastVisited(Date lastVisited) {

        return eventRepository.getAllfromLastVisited(lastVisited);
    }

    public List<Event> getAllbyType(String type) {

        return eventRepository.getAllbyType(type);
    }

    public List<Event> getAllbyLevel(String level) {

        return eventRepository.getAllbyLevel(level);
    }

    public List<Event> getAllbyLimit(int limit) {

        return eventRepository.getAllbyLimit(limit);
    }

    @Override
    public List<Event> getAllbyParent(Long parent_id) {
        return eventRepository.getAllbyParent(parent_id);
    }

    public Event update(Event event) {
        event.putDate();
        eventRepository.update(event);
        return event;
    }

    public void delete(Event event) {
        event.setSysStatus(-1);
        eventRepository.delete(event);
    }

    public void remove(Event event) {

        eventRepository.remove(event);
    }
}
