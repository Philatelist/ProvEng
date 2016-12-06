package com.provectus.proveng.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.provectus.proveng.domain.*;
import com.provectus.proveng.enumaration.*;
import com.provectus.proveng.service.*;
import com.provectus.proveng.utils.FalseResult;
import com.provectus.proveng.utils.SuccessfulResult;
import com.provectus.proveng.utils.view.EventView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class EventController {

    private static final Logger log = LogManager.getLogger(EventController.class);

    static {
        log.info("Started EventController");
    }

    @Autowired
    private EventService eventService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private LocationService locationService;

    @Autowired
    private LoginSessionService loginSessionService;

    @Autowired
    private FalseResult falseResult;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private TestService testService;

    @Autowired
    private SuccessfulResult successfulResult;

    @Autowired
    private UserService userService;


    /**
     * {
     * "result": {
     * "id": 1,
     * "name": "ParentEvent1",
     * "type": "Lifetime",
     * "dateStart": 1471467600000,
     * "regular": "Once",
     * "dateEnd": 1479506400000,
     * "note": "Notes"
     * }
     * }
     **/
    @RequestMapping(value = "/rest/v1/event", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    @JsonView(EventView.RelationLevel.class)
    ResponseEntity getEvent(@RequestHeader("token") String token,
                            @RequestParam(value = "id") long event_id) {
        User user = loginSessionService.getLoginSessionByToken(token).getUser();

        if (!user.hasPermission(PermissionType.EVENT, PermissionType.READ))
            return new FalseResult(ErrorType.NO_ACCESS).getResult();

        try {
            log.debug(">>> INJECTED 'getEvent' token: " + token);

            Event event = eventService.getById(event_id);

            if (event == null || event.getSysStatus() != 0) {
                return new FalseResult(ErrorType.NOT_IN_DB).getResult();
            }
            if (isUserHasRole(user, RoleName.TEACHER)) {
                List<Event> childsEvent = new ArrayList<>();
                for (Event child : event.getChildEvents()) {
                    if (child.getSysStatus() == 0) {
                        childsEvent.add(child);
                    }
                }
                event.removeAllEventChilds();
                event.setChildEvents(childsEvent);
            }

            successfulResult = new SuccessfulResult(event);
            if (!isUserHasRole(user, RoleName.ADMIN) && !isUserHasRole(user, RoleName.TEACHER)) {
                List<Event> childsEvent = new ArrayList<>();
                for (Event child : event.getChildEvents()) {
                    if (child.getCreater() != null && user.getId() == child.getCreater().getId() && child.getSysStatus() == 0) {
                        childsEvent.add(child);
                    }
                }
                event.removeAllEventChilds();

                event.setChildEvents(childsEvent);
            }

            log.debug(">>> RESULT 'getEvent' returns: " + successfulResult.toString());

            return successfulResult.getResult();
        } catch (Exception e) {
            return getErrorMapResponseEntity(e);
        }
    }

    /**
     * rest call for create event
     *
     * @return Event entity in Json with next params:
     * {
     * "result": {
     * "@id": "1",
     * "id": 0,
     * "name": "Name For Post Mock Event",
     * "type": "Lesson",
     * "note": "Many notes here"
     * }
     * }
     * @body {
     * "name": "someName",
     * "leader_id": someId,
     * "group_id": someId,
     * "type": "someType",
     * "dateStart": 1471347123868L,
     * "dateEnd": 1471352523868L,
     * "regular": "someRegular",
     * "note": "someNote",
     * "location_id": someId,
     * }
     * @header token String (local token of our application)
     */

    @RequestMapping(value = "/rest/v1/event", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    @JsonView(EventView.MediumInfoLevel.class)
    ResponseEntity setEvent(@RequestHeader("token") String token,
                            @RequestParam(value = "level", required = false) String level,
                            @RequestBody Event event) {
        User user = loginSessionService.getLoginSessionByToken(token).getUser();

        if (!user.hasPermission(PermissionType.EVENT, PermissionType.CREATE))
            return new FalseResult(ErrorType.NO_ACCESS).getResult();

        Group group = new Group();
        if (isUserHasRole(user, RoleName.TEACHER) && event.getType().equals(EventType.WORKSHOP)) {
            if (level == null) {
                level = LevelType.ELEMENTARY.getStringValue();
            }
            group.setLevel(level);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MMM.yyyy");
            group.setName(EventType.WORKSHOP + " - " + dateFormat.format(event.getDateStart()));
            group.setLeader(user);
            group.setPrimaryGroupFlag(false);
            groupService.create(group);
        }

        try {
            log.debug(">>> INJECTED 'setEvent' token: " + token);

            if (chekDateInEvent(event.getDateStart().getTime(), event.getDateEnd().getTime())) {
                return new FalseResult(ErrorType.BAD_INPUT).getResult();
            }

            if (event.getName() != null && event.getLeader() != null && event.getType() != null) {
                if (event.getType().equals(EventType.WORKSHOP)) {
                    event.setGroup(group);
                }
                event.setCreater(user);
                eventService.create(event);
                long resultId = event.getId();
                Event result = eventService.getById(resultId);
                result.setLocation(locationService.getById(event.getLocation().getId()));
                result.setGroup(groupService.getById(event.getGroup().getId()));
                return new SuccessfulResult(result).getResult();
            } else {
                return new FalseResult(ErrorType.EMPTY_FIELDS).getResult();
            }

        } catch (Exception e) {
            return getErrorMapResponseEntity(e);
        }
    }

    @RequestMapping(value = "/rest/v1/event", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    @JsonView(EventView.MediumInfoLevel.class)
    ResponseEntity deleteEvent(@RequestHeader("token") String token,
                               @RequestParam long id) {
        User user = loginSessionService.getLoginSessionByToken(token).getUser();

        if (!user.hasPermission(PermissionType.EVENT, PermissionType.DELETE))
            return new FalseResult(ErrorType.NO_ACCESS).getResult();

        Event event = eventService.getById(id);

        try {
            log.debug(">>> INJECTED 'deleteEvent' token: " + token);

            if (event != null) {
                event.setSysStatus(SysStatus.OFF.getNumber());
                if (event.getType().equals(EventType.WORKSHOP)) {
                    groupService.turnOffById(event.getGroup().getId());
                }
                eventService.update(event);
                return new SuccessfulResult(SuccessType.DELETE_OK).getResult();
            } else {
                return new FalseResult(ErrorType.NOT_IN_DB).getResult();
            }
        } catch (Exception e) {
            return getErrorMapResponseEntity(e);
        }
    }

    @RequestMapping(value = "/rest/v1/event", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    @JsonView(EventView.MediumInfoLevel.class)
    ResponseEntity updateEvent(@RequestHeader("token") String token,
                               @RequestBody Event event) {
        User user = loginSessionService.getLoginSessionByToken(token).getUser();

        if (!user.hasPermission(PermissionType.EVENT, PermissionType.WRITE))
            return new FalseResult(ErrorType.NO_ACCESS).getResult();

        try {
            log.debug(">>> INJECTED 'updateEvent' token: " + token);

            Event checkDate;

            if (chekDateInEvent(event.getDateStart().getTime(), event.getDateEnd().getTime())) {
                return new FalseResult(ErrorType.BAD_INPUT).getResult();
            } else if (eventService.getById(event.getId()) == null) {
                return new FalseResult(ErrorType.NOT_IN_DB).getResult();
            } else {
                checkDate = eventService.getById(event.getId());
            }

            if (checkDate.getSysStatus() != 0) {
                return new FalseResult(ErrorType.NOT_IN_DB).getResult();
            } else if (event.getId() > 0 && event.getName() != null && event.getLeader() != null && event.getType() != null && event.getDateStart().getTime() == checkDate.getDateStart().getTime()) {
                eventService.update(event);
                successfulResult = new SuccessfulResult(eventService.getById(event.getId()));
            } else if (event.getId() > 0 && event.getName() != null && event.getLeader() != null && event.getType() != null && event.getDateStart().getTime() != checkDate.getDateStart().getTime()) {
                Event oldEvent = eventService.getById(event.getId());
                oldEvent.setSysStatus(SysStatus.OFF.getNumber());
                eventService.update(oldEvent);
                event.setId(0); //delete Event_id before create new Event
                eventService.create(event);
                long resultId = event.getId();
                Event result = eventService.getById(resultId);
                result.setLocation(locationService.getById(event.getLocation().getId()));
                result.setGroup(groupService.getById(event.getGroup().getId()));
                successfulResult = new SuccessfulResult(result);
            } else {
                return new FalseResult(ErrorType.EMPTY_FIELDS).getResult();
            }

            log.debug(">>> RESULT 'updateEvent' returns: " + successfulResult.toString());

            return successfulResult.getResult();
        } catch (Exception e) {
            return getErrorMapResponseEntity(e);
        }
    }

    /**
     * rest call for accept event
     *
     * @param id Long - expected
     * @return New JSON object:
     * {
     * "result": {
     * "@id": "1",
     * "id": 31,
     * "name": "Accepted by user",
     * "type": "Accepted"
     * }
     * }
     * @header token String (local token of our application)
     */
    @RequestMapping(value = "/rest/v1/eventAccepted", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    @JsonView(EventView.ShortInfoLevel.class)
    ResponseEntity eventAccepted(@RequestHeader("token") String token,
                                 @RequestParam(value = "id") long id,
                                 @RequestParam(value = "note", required = false) String note) {

        User creater = loginSessionService.getLoginSessionByToken(token).getUser();
        if (!creater.hasPermission("Accept", PermissionType.ACCEPT, PermissionType.CREATE)) {
            return new FalseResult(ErrorType.NO_ACCESS).getResult();
        }

        Event parentEvent = eventService.getById(id);
        Queue<User> usersInQueue = new LinkedList<>(parentEvent.getQueue());
        Queue<User> queue = new LinkedList<>();

        for (User user : usersInQueue) {
            queue.offer(user);
        }

        try {
            log.debug(">>> INJECTED 'eventAccepted' token: " + token);

            if (parentEvent.getSysStatus() != 0) {
                return new FalseResult(ErrorType.NOT_IN_DB).getResult();
            }

            if (parentEvent.getType().equals(EventType.WORKSHOP)) {
                isStudentOrGuest(queue);
                queue.add(creater);
                parentEvent.setQueue(queue);
                eventService.update(parentEvent);
                Group group = parentEvent.getGroup();
                if (!(group.getMembers().size() < MaxUsers.MAX_WORKSHOP_MEMBERS)) {
                    return new FalseResult(ErrorType.NO_ADD_TO_WORKSHOP).getResult();
                }

                List<User> parentGroup = new ArrayList<>(group.getMembers());
                for (User user : parentGroup) {
                    if (user.getId() == creater.getId()) {
                        return new FalseResult(ErrorType.ALREADY_IN_GROUP).getResult();
                    }
                }
                parentGroup.add(queue.element());
                group.setMembers(parentGroup);
                groupService.update(group);
                parentEvent.setQueue(queue);
                eventService.update(parentEvent);
            }

            User leader = parentEvent.getLeader();

            Event eventAccepted = new Event("Accepted by user", leader, creater, "Accepted", note, parentEvent);

            List<Event> parentChilds = (List<Event>) parentEvent.getChildEvents();
            for (Event childEvent : parentChilds) {
                if (childEvent.getCreater() == creater) {
                    eventService.delete(childEvent);
                }
            }

            eventService.create(eventAccepted);
            long resultId = eventAccepted.getId();
            Event result = eventService.getById(resultId);
            successfulResult = new SuccessfulResult(result);

            log.debug(">>> RESULT 'eventAccepted' returns: " + successfulResult.toString());

            return successfulResult.getResult();
        } catch (Exception e) {
            return getErrorMapResponseEntity(e);
        }
    }

    /**
     * rest call for denied event
     *
     * @param id Long - expected
     * @return New JSON object:
     * {
     * "result": {
     * "@id": "1",
     * "id": 21,
     * "name": "Denied by user",
     * "type": "Denied"
     * }
     * }
     * @header token String (local token of our application)
     */
    @RequestMapping(value = "/rest/v1/eventDenied", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    @JsonView(EventView.ShortInfoLevel.class)
    ResponseEntity eventDenied(@RequestHeader("token") String token,
                               @RequestParam(value = "id") long id,
                               @RequestParam(value = "note", required = false) String note) {
        User creater = loginSessionService.getLoginSessionByToken(token).getUser();
        Event parentEvent = eventService.getById(id);
        Group parentGroup = parentEvent.getGroup();

        List<User> usersInQueue = (List<User>) parentEvent.getQueue();
        Queue<User> queue = new LinkedList<>();
        for (User user : usersInQueue) {
            queue.offer(user);
        }
        isStudentOrGuest(queue);

        groupService.deleteUserFromGroup(parentGroup.getId(), creater.getId());


        if (!creater.hasPermission("Denied", PermissionType.DENIED, PermissionType.CREATE)) {
            return new FalseResult(ErrorType.NO_ACCESS).getResult();
        }

        try {
            log.debug(">>> INJECTED 'eventDenied' token: " + token);

            if (parentEvent.getSysStatus() != 0) {
                return new FalseResult(ErrorType.NOT_IN_DB).getResult();
            }

            if (queue.size() > 0) {
                parentEvent.addUser(queue.element());
            }
            eventService.update(parentEvent);

            User leader = parentEvent.getLeader();
            List<Event> parentChilds = (List<Event>) parentEvent.getChildEvents();
            for (Event childEvent : parentChilds) {
                if (childEvent.getCreater() == creater) {
                    eventService.delete(childEvent);
                }
            }
            Event eventDenied = new Event("Denied by user", leader, creater, "Denied", note, parentEvent);
            eventService.create(eventDenied);
            long resultId = eventDenied.getId();
            Event result = eventService.getById(resultId);
            successfulResult = new SuccessfulResult(result);

            log.debug(">>> RESULT 'eventDenied' returns: " + successfulResult.toString());

            return successfulResult.getResult();
        } catch (Exception e) {
            return getErrorMapResponseEntity(e);
        }
    }

    /**
     * rest call for accept event
     *
     * @param event_id Long - expected
     * @return New JSON object:
     * {
     * "result": {,
     * <p>
     * }
     * }
     * @body List(Users)
     */
    @RequestMapping(value = "/rest/v1/eventVisited", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    @JsonView(EventView.ShortInfoLevel.class)
    ResponseEntity eventVisited(@RequestHeader("token") String token,
                                @RequestParam(value = "event_id") long event_id,
                                @RequestParam(value = "group_id") long group_id,
                                @RequestBody List<User> users) {

        User leader = loginSessionService.getLoginSessionByToken(token).getUser();
        if (!leader.hasRole(RoleName.TEACHER)) {
            return new FalseResult(ErrorType.NO_ACCESS).getResult();
        }
        Event event = eventService.getById(event_id);
        List<Event> oldchildsEvent = (List<Event>) event.getChildEvents();
        List<Event> newChildsEvent = new ArrayList<>(event.getChildEvents());
        List<Event> returnEvents = new ArrayList<>();
        Group group = groupService.getById(group_id);

        try {
            log.debug(">>> INJECTED 'eventVisited' token: " + token);

            for (Event e : oldchildsEvent) {
                if (e.getType().equals(EventType.VISITED)) {
                    e.setSysStatus(SysStatus.OFF.getNumber());
                    eventService.update(e);
                } else newChildsEvent.add(e);
            }
            event.removeAllEventChilds();
            event.setChildEvents(newChildsEvent);
            eventService.update(event);

            if (event == null || event.getSysStatus() != 0) {
                return new FalseResult(ErrorType.NOT_IN_DB).getResult();
            }

            for (User creater : users) {
                Event eventVisited = new Event("Marked visited from teacher.", leader, creater, EventType.VISITED, "visited", event);
                eventVisited.setGroup(group);
                eventService.create(eventVisited);
                long resultId = eventVisited.getId();
                Event resultEvent = eventService.getById(resultId);
                returnEvents.add(resultEvent);
                event.addEventChild(resultEvent);
                eventService.update(event);
            }

            return new SuccessfulResult(returnEvents).getResult();

        } catch (Exception e) {
            return getErrorMapResponseEntity(e);
        }
    }


    @RequestMapping(value = "/rest/v1/feed", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    @JsonView(EventView.RelationLevel.class)
    ResponseEntity getFeed(@RequestHeader("token") String token) {

        User user = loginSessionService.getLoginSessionByToken(token).getUser();

        if (!user.hasPermission(PermissionType.FEED, PermissionType.READ))
            return new FalseResult(ErrorType.NO_ACCESS).getResult();

        try {
            log.debug(">>> INJECTED 'getFeed' token: " + token);

            List<Event> eventsForFeed = new ArrayList<>(eventService.getFeed(user));

            if (eventsForFeed.size() > 0) {
                Collections.sort(eventsForFeed, (object1, object2) -> {
                    Long obj1;
                    if (isEventForFeed(object1) && isEventForFeed(object2)) {
                        return object1.getCreateDtm().compareTo(object2.getCreateDtm());
                    }
                    if (isEventForFeed(object1) && !isEventForFeed(object2)) {
                        return object1.getCreateDtm().compareTo(object2.getDateStart());
                    }
                    if (!isEventForFeed(object1) && isEventForFeed(object2)) {
                        return object1.getDateStart().compareTo(object2.getCreateDtm());
                    } else obj1 = object1.getDateStart().getTime();
                    return obj1.compareTo(object2.getDateStart().getTime());
                });
                Collections.reverse(eventsForFeed);
            }
            successfulResult = new SuccessfulResult(eventsForFeed);

            log.debug(">>> RESULT 'getFeed' returns: " + successfulResult.toString());

            return successfulResult.getResult();
        } catch (Exception e) {
            return getErrorMapResponseEntity(e);
        }
    }

    private boolean isEventForFeed(Event obj) {
        return obj.getType().equals(EventType.WORKSHOP) || obj.getType().equals(EventType.NOTIFICATION);
    }

    @RequestMapping(value = "/rest/v1/calendar", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    @JsonView(EventView.CalendarInfoLevel.class)
    ResponseEntity getCalendar(@RequestHeader("token") String token,
                               @RequestParam(value = "date", required = false) Long date) {

        User user = loginSessionService.getLoginSessionByToken(token).getUser();

        Calendar fromDate = Calendar.getInstance(TimeZone.getTimeZone("UTC")),
                toDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        if (date != null) {
            fromDate.setTime(new Date(date));
            toDate.setTime(new Date(date));
        } else {
            fromDate.setTime(new Date());
            toDate.setTime(new Date());
        }
        fromDate.set(fromDate.get(Calendar.YEAR), fromDate.get(Calendar.MONTH), 1, 0, 0, 0);
        toDate.set(toDate.get(Calendar.YEAR), toDate.get(Calendar.MONTH) + 1, 1, 0, 0, 0);

        if (!user.hasPermission(PermissionType.CALENDAR, PermissionType.READ))
            return new FalseResult(ErrorType.NO_ACCESS).getResult();

        try {
            log.debug(">>> INJECTED 'getCalendar' token: " + token);

            List<Event> events = eventService.getCalendar(user, new Date(fromDate.getTimeInMillis()), new Date(toDate.getTimeInMillis()));
            if (events.size() == 0) {
                return new FalseResult(ErrorType.NOT_IN_GROUPS).getResult();
            } else {
                successfulResult = new SuccessfulResult(events);
            }

            log.debug(">>> RESULT 'getCalendar' returns: " + successfulResult.toString());

            return successfulResult.getResult();
        } catch (Exception e) {
            return getErrorMapResponseEntity(e);
        }
    }

    @RequestMapping(value = "/rest/v1/events", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    @JsonView(EventView.MediumInfoLevel.class)
    ResponseEntity getEvents(@RequestHeader("token") String token,
                             @RequestParam(value = "group_id", required = false) Long group_id,
                             @RequestParam(value = "user_id", required = false) Long user_id,
                             @RequestParam(value = "fromDate", required = false) Long fromDate,
                             @RequestParam(value = "toDate", required = false) Long toDate,
                             @RequestParam(value = "lastVisited", required = false) Long lastVisited,
                             @RequestParam(value = "type", required = false) String type,
                             @RequestParam(value = "limit", required = false) Integer limit) {

        User user = loginSessionService.getLoginSessionByToken(token).getUser();
        if (!user.hasPermission(PermissionType.EVENT, PermissionType.READ))
            return new FalseResult(ErrorType.NO_ACCESS).getResult();

        try {
            log.debug(">>> INJECTED 'getEvents' token: " + token);
            if (limit == null) {
                limit = 0;
            }
            List<Event> events = new ArrayList<>(eventService.getAll(limit));


            if (user_id != null) {
                for (int i = events.size() - 1; i >= 0; --i) {
                    if (events.get(i).getGroup() == null || !isUserInGroup(events.get(i).getGroup().getId(), userService.getById(user_id))) {
                        events.remove(i);
                    }
                }
            }
            if (fromDate != null) {
                for (int i = events.size() - 1; i >= 0; --i) {
                    if (events.get(i).getDateStart() == null || events.get(i).getDateStart().getTime() < fromDate) {
                        events.remove(i);
                    }
                }
            }
            if (toDate != null) {
                for (int i = events.size() - 1; i >= 0; --i) {
                    if (events.get(i).getDateEnd() == null || events.get(i).getDateEnd().getTime() > toDate) {
                        events.remove(i);
                    }
                }
            }
            if (group_id != null) {
                for (int i = events.size() - 1; i >= 0; --i) {
                    if (events.get(i).getGroup() == null || events.get(i).getGroup().getId() != group_id) {
                        events.remove(i);
                    }
                }
            }
            if (lastVisited != null) {
                for (int i = events.size() - 1; i >= 0; --i) {
                    if (events.get(i).getModifyDtm() == null || events.get(i).getModifyDtm().getTime() < lastVisited) {
                        events.remove(i);
                    }
                }
            }
            if (type != null) {
                for (int i = events.size() - 1; i >= 0; --i) {
                    if (events.get(i).getType() == null || !events.get(i).getType().equals(type)) {
                        events.remove(i);
                    }
                }
            }


            successfulResult = new SuccessfulResult(events);

            log.debug(">>> RESULT 'getEvents' returns: " + successfulResult.toString());

            return successfulResult.getResult();
        } catch (Exception e) {
            return getErrorMapResponseEntity(e);
        }
    }

    private boolean isUserInGroup(long id, User user) {
        List<Group> groups = groupService.getGroupsForUser(user);
        return groups.contains(groupService.getById(id));
    }

    /**
     * rest call for create schedule
     *
     * @param group_id - expected
     * @return Event entities in Json with next params:
     * {
     * "result": [
     * {
     * "leader": 1,
     * "type": "Schedule",
     * "dateStart": 480600000,
     * "dateEnd": 1474987500000,
     * "group": {
     * "id": 1,
     * "name": "Newbies",
     * "level": "Beginner"
     * },
     * "location": {
     * "id": 1,
     * "place": "China, Tower",
     * "roominess": 15
     * },
     * "id": 256
     * },
     * {
     * "leader": 1,
     * "type": "Schedule",
     * "dateStart": 484200000,
     * "dateEnd": 1474994700000,
     * "group": {
     * "id": 1,
     * "name": "Newbies",
     * "level": "Beginner"
     * },
     * "location": {
     * "id": 2,
     * "place": "San Diego, USA",
     * "roominess": 8
     * },
     * "id": 271
     * },
     * {
     * "leader": 1,
     * "type": "Lifetime",
     * "dateStart": 1475442000000,
     * "dateEnd": 1483477200000,
     * "note": "someNotes",
     * "group": {
     * "id": 1,
     * "name": "Newbies",
     * "level": "Beginner"
     * },
     * "id": 255
     * }
     * ]
     * }
     * @header token String (local token of our application)
     * @body [
     * {
     * "type": "Schedule",
     * "regular": "Weekly",
     * "dateStart": 480600000,
     * "dateEnd": 1474987500000,
     * "location":{"id":1}
     * },
     * {
     * "type": "Schedule",
     * "regular": "Weekly",
     * "dateStart": 484200000,
     * "dateEnd": 1474994700000,
     * "location":2
     * },
     * {
     * "type": "Lifetime",
     * "note": "someNotes",
     * "dateStart": 1475442000000,
     * "dateEnd": 1483477200000
     * }]
     */
    @RequestMapping(value = "/rest/v1/schedule", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    @JsonView(EventView.ScheduleInfoLevel.class)
    ResponseEntity setSchedule(@RequestHeader("token") String token,
                               @RequestParam(value = "group_id") Long group_id,
                               @RequestBody List<Event> events) {
        User user = loginSessionService.getLoginSessionByToken(token).getUser();
        Group group = groupService.getById(group_id);

        if (!user.hasPermission(PermissionType.SCHEDULE, PermissionType.CREATE)) {
            return new FalseResult(ErrorType.NO_ACCESS).getResult();
        }

        if (eventService.getSchedule(group_id).size() > 0) {
            return new FalseResult(ErrorType.DOUBLE).getResult();
        }
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC")),
                startDateGroup = Calendar.getInstance(TimeZone.getTimeZone("UTC")),
                endDateGroup = Calendar.getInstance(TimeZone.getTimeZone("UTC")),
                firstLesson = Calendar.getInstance(TimeZone.getTimeZone("UTC")),
                end = Calendar.getInstance(TimeZone.getTimeZone("UTC")),
                lifeTimeStart = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        Event lesson, lifetime = null;
        if (events.size() != 3) {
            return new FalseResult(ErrorType.BAD_INPUT).getResult();
        }
        try {
            log.debug(">>> INJECTED 'setSchedule' token: " + token);
            for (Event event : events) {
                if (chekDateInEvent(event.getDateStart().getTime(), event.getDateEnd().getTime())) {
                    return new FalseResult(ErrorType.BAD_INPUT).getResult();
                }
                if (event.getType() != null && event.getDateStart() != null &&
                        event.getDateEnd() != null && (event.getType().equals(EventType.LIFETIME) ||
                        event.getType().equals(EventType.SCHEDULE) && event.getLocation() != null)) {
                    log.debug(">>> RESULT 'setSchedule' event: " + event);
                } else {
                    return new FalseResult(ErrorType.EMPTY_FIELDS).getResult();
                }
            }
            for (Event event : events) {
                if (event.getType().equals(EventType.LIFETIME)) {
                    lifeTimeStart.setTime(event.getDateStart());
                    if (checkCorrectLifitime(lifeTimeStart)) {
                        return new FalseResult(ErrorType.BAD_INPUT).getResult();
                    }
                    startDateGroup.setTime(event.getDateStart());
                    endDateGroup.setTime(event.getDateEnd());
                    lifetime = event;
                    lifetime.setLeader(user);
                    lifetime.setGroup(group);
                    eventService.create(lifetime);
                }
            }

            for (Event event : events) {
                if (event.getType().equals(EventType.SCHEDULE)) {
                    calendar.setTime(event.getDateStart());
                    end.setTime(event.getDateEnd());
                    long startLesson = calendar.get(Calendar.HOUR_OF_DAY) * 3600000 + calendar.get(Calendar.MINUTE) * 60000;
                    long endLesson = end.get(Calendar.HOUR_OF_DAY) * 3600000 + end.get(Calendar.MINUTE) * 60000;
                    Long durationLesson = endLesson - startLesson;
                    int dayLesson = calendar.get(Calendar.DAY_OF_WEEK);
                    firstLesson.setTimeInMillis(startDateGroup.getTimeInMillis() + startLesson);
                    firstLesson.set(Calendar.DAY_OF_WEEK, dayLesson);
                    if (firstLesson.before(startDateGroup)) {
                        firstLesson.set(Calendar.DAY_OF_MONTH, firstLesson.get(Calendar.DAY_OF_MONTH) + 7);
                    }
                    event.setLeader(user);
                    event.setGroup(group);
                    event.setLocation(locationService.getById(event.getLocation().getId()));
                    event.setParentEvent(lifetime);
                    event.setDateStart(firstLesson.getTime());
                    eventService.create(event);
                    long endSchedule = 0;
                    for (long i = firstLesson.getTimeInMillis(); i < endDateGroup.getTimeInMillis(); i += 604800000) {
                        if (i > endSchedule) {
                            endSchedule = i;
                        }
                        lesson = new Event(user, EventType.LESSON, new Date(i), "Once", new Date(i + durationLesson), group, event.getLocation(), event);
                        lesson.setName(EventType.LESSON);
                        eventService.create(lesson);
                    }
                    event.setDateEnd(new Date(endSchedule + durationLesson));
                    eventService.update(event);

                }
            }
            log.debug(">>> RESULT 'setSchedule' returns: " + new SuccessfulResult(events).getResult());
            return new SuccessfulResult(events).getResult();
        } catch (Exception e) {
            return getErrorMapResponseEntity(e);
        }
    }

    /**
     * rest call for create schedule
     *
     * @param group_id - expected
     * @return Event entities in Json with next params:
     * {
     * "result": [
     * {
     * "leader": 1,
     * "type": "Schedule",
     * "dateStart": 480600000,
     * "dateEnd": 1474987500000,
     * "group": {
     * "id": 1,
     * "name": "Newbies",
     * "level": "Beginner"
     * },
     * "location": {
     * "id": 1,
     * "place": "China, Tower",
     * "roominess": 15
     * },
     * "id": 256
     * },
     * {
     * "leader": 1,
     * "type": "Schedule",
     * "dateStart": 484200000,
     * "dateEnd": 1474994700000,
     * "group": {
     * "id": 1,
     * "name": "Newbies",
     * "level": "Beginner"
     * },
     * "location": {
     * "id": 2,
     * "place": "San Diego, USA",
     * "roominess": 8
     * },
     * "id": 271
     * },
     * {
     * "leader": 1,
     * "type": "Lifetime",
     * "dateStart": 1475442000000,
     * "dateEnd": 1483477200000,
     * "note": "someNotes",
     * "group": {
     * "id": 1,
     * "name": "Newbies",
     * "level": "Beginner"
     * },
     * "id": 255
     * }
     * ]
     * }
     * @header token String (local token of our application)
     */
    @RequestMapping(value = "/rest/v1/schedule", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    @JsonView(EventView.ScheduleInfoLevel.class)
    ResponseEntity getSchedule(@RequestHeader("token") String token,
                               @RequestParam(value = "group_id") Long group_id) {
        User user = loginSessionService.getLoginSessionByToken(token).getUser();

        if (!user.hasPermission(PermissionType.SCHEDULE, PermissionType.READ)) {
            return new FalseResult(ErrorType.NO_ACCESS).getResult();
        }

        List<Event> eventsReturn;

        try {
            log.debug(">>> INJECTED 'getSchedule' token: " + token);
            eventsReturn = eventService.getSchedule(group_id);
            log.debug(">>> RESULT 'getSchedule' returns: " + new SuccessfulResult(eventsReturn).getResult());
            return new SuccessfulResult(eventsReturn).getResult();
        } catch (Exception e) {
            return getErrorMapResponseEntity(e);
        }
    }

    /**
     * rest call for update schedule
     *
     * @param group_id - expected
     * @return Event entities in Json with next params:
     * @header token String (local token of our application)
     */
    @RequestMapping(value = "/rest/v1/schedule", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    @JsonView(EventView.ScheduleInfoLevel.class)
    ResponseEntity updateSchedule(@RequestHeader("token") String token,
                                  @RequestParam(value = "group_id") Long group_id,
                                  @RequestBody List<Event> events) {
        User user = loginSessionService.getLoginSessionByToken(token).getUser();
        Group group = groupService.getById(group_id);

        if (!user.hasPermission(PermissionType.SCHEDULE, PermissionType.WRITE)) {
            return new FalseResult(ErrorType.NO_ACCESS).getResult();
        }

        if (!(eventService.getSchedule(group_id).size() > 0)) {
            return new FalseResult(ErrorType.BAD_INPUT).getResult();
        }
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC")),
                startDateGroup = Calendar.getInstance(TimeZone.getTimeZone("UTC")),
                endDateGroup = Calendar.getInstance(TimeZone.getTimeZone("UTC")),
                firstLesson = Calendar.getInstance(TimeZone.getTimeZone("UTC")),
                end = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

        List<Event> groupEvents = eventService.getAllbyGroupIdSpec(group_id);
        List<Event> lessons;
        Event newLifetime, oldLifetime = null, lesson, schedule;

        for (Event e : groupEvents) {
            if (e.getType().equals(EventType.LIFETIME)) {
                oldLifetime = e;
                startDateGroup.setTime(e.getDateStart());
                endDateGroup.setTime(e.getDateEnd());
            }
        }
        for (Event e : events) {
            if (e.getType().equals(EventType.LIFETIME)) {
                newLifetime = e;
                if (!isCheckEqualsDateEvent(newLifetime, oldLifetime)) {
                    return new FalseResult(ErrorType.BAD_INPUT).getResult();
                }
            }
        }

        try {
            log.debug(">>> INJECTED 'updateSchedule' token: " + token);
            for (Event event : events) {
                if (chekDateInEvent(event.getDateStart().getTime(), event.getDateEnd().getTime())) {
                    return new FalseResult(ErrorType.BAD_INPUT).getResult();
                }
                if (event.getType() != null && event.getDateStart() != null &&
                        event.getDateEnd() != null && (event.getType().equals(EventType.LIFETIME) ||
                        event.getType().equals(EventType.SCHEDULE) && event.getLocation() != null)) {
                    log.debug(">>> RESULT 'updateSchedule' event: " + event);
                } else {
                    return new FalseResult(ErrorType.EMPTY_FIELDS).getResult();
                }
            }

            for (Event e : events) {
                if (e.getType().equals(EventType.SCHEDULE) && e.getDateStart().getTime() != eventService.getById(e.getId()).getDateStart().getTime()) {
                    lessons = eventService.getAllbyParent(e.getId());
                    for (Event les : lessons) {
                        if (les.getDateStart().after(new Date())) {
                            lesson = eventService.getById(les.getId());
                            lesson.setSysStatus(SysStatus.OFF.getNumber());
                            eventService.update(lesson);
                        }
                    }
                    calendar.setTime(e.getDateStart());
                    end.setTime(e.getDateEnd());
                    long startLesson = calendar.get(Calendar.HOUR_OF_DAY) * 3600000 + calendar.get(Calendar.MINUTE) * 60000;
                    long endLesson = end.get(Calendar.HOUR_OF_DAY) * 3600000 + end.get(Calendar.MINUTE) * 60000;
                    Long durationLesson = endLesson - startLesson;
                    int dayLesson = calendar.get(Calendar.DAY_OF_WEEK);
                    firstLesson.setTimeInMillis(oldLifetime.getDateStart().getTime() + startLesson);
                    firstLesson.set(Calendar.DAY_OF_WEEK, dayLesson);
                    e.setLeader(user);
                    e.setGroup(group);
                    e.setLocation(locationService.getById(e.getLocation().getId()));
                    e.setParentEvent(oldLifetime);
                    e.setDateStart(firstLesson.getTime());
                    eventService.update(e);
                    long endSchedule = 0;
                    Location newLocation = locationService.getById(e.getLocation().getId());
                    for (long i = firstLesson.getTimeInMillis(); i < endDateGroup.getTimeInMillis(); i += 604800000) {
                        if (i > endSchedule) {
                            endSchedule = i;
                        }
                        if (i > new Date().getTime()) {
                            lesson = new Event(user, EventType.LESSON, new Date(i), "Once", new Date(i + durationLesson), group, newLocation, e);
                            lesson.setName(EventType.LESSON);
                            eventService.create(lesson);
                        }
                    }
                    e.setDateEnd(new Date(endSchedule + durationLesson));
                    eventService.update(e);
                }
                if (e.getType().equals(EventType.SCHEDULE) && (e.getDateEnd().getTime() != eventService.getById(e.getId()).getDateEnd().getTime()
                        || e.getLocation() != eventService.getById(e.getId()).getLocation())) {
                    lessons = eventService.getAllbyParent(e.getId());
                    for (Event e1 : lessons) {
                    }
                    for (Event les : lessons) {
                        if (les.getDateStart().getTime() > (new Date().getTime())) {
                            lesson = eventService.getById(les.getId());
                            lesson.setDateEnd(e.getDateEnd());
                            lesson.setLocation(locationService.getById(e.getLocation().getId()));
                            eventService.update(lesson);
                        }
                    }
                    schedule = eventService.getById(e.getId());
                    schedule.setDateEnd(e.getDateEnd());
                    schedule.setLocation(locationService.getById(e.getLocation().getId()));
                    eventService.update(schedule);
                }
            }

            log.debug(">>> RESULT 'updateSchedule' returns: " + new SuccessfulResult(events).getResult());
            return new SuccessfulResult(eventService.getSchedule(group_id)).getResult();
        } catch (Exception e) {
            return getErrorMapResponseEntity(e);
        }
    }

    private boolean isCheckEqualsDateEvent(Event newLifetime, Event oldLifetime) {
        return newLifetime.getId() == oldLifetime.getId() && newLifetime.getDateStart().getTime() == oldLifetime.getDateStart().getTime() && newLifetime.getDateEnd().getTime() == oldLifetime.getDateEnd().getTime();
    }

    @RequestMapping(value = "/rest/v1/eventUsersStatus", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    @JsonView(EventView.ShortInfoLevel.class)
    ResponseEntity getEventUsersStatus(@RequestHeader("token") String token,
                                       @RequestParam(value = "id") Long id) {
        User user = loginSessionService.getLoginSessionByToken(token).getUser();

        if (!user.hasPermission(PermissionType.EVENT, PermissionType.READ)) {
            return new FalseResult(ErrorType.NO_ACCESS).getResult();
        }

        List<Event> eventsReturn;

        try {
            log.debug(">>> INJECTED 'getEventUsersStatus' token: " + token);
            eventsReturn = eventService.getEventUsersStatus(id);
            log.debug(">>> RESULT 'getEventUsersStatus' returns: " + new SuccessfulResult(eventsReturn).getResult());
            return new SuccessfulResult(eventsReturn).getResult();
        } catch (Exception e) {
            return getErrorMapResponseEntity(e);
        }
    }

    @RequestMapping(value = "/rest/v1/openMaterial", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(EventView.RelationLevel.class)
    ResponseEntity openMaterials(@RequestHeader("token") String token,
                                 @RequestParam(value = "material_id") Long material_id,
                                 @RequestParam(value = "group_id", required = false) Long group_id,
                                 @RequestParam(value = "fromDate", required = false) Long fromDate,
                                 @RequestParam(value = "toDate", required = false) Long toDate,
                                 @RequestParam(value = "name", required = false) String name,
                                 @RequestParam(value = "note", required = false) String note) {

        User user = loginSessionService.getLoginSessionByToken(token).getUser();
        if (!user.hasPermission(PermissionType.EVENT, PermissionType.CREATE)) {
            return new FalseResult(ErrorType.NO_ACCESS).getResult();
        }
        try {
            List<Event> checkEvents = eventService.getAllbyType(EventType.MATERIAL);
            for (Event e : checkEvents) {
                Material m = (Material) e.getEventableItem();
                if (e.getType().equals(EventType.MATERIAL) && e.getSysStatus() == SysStatus.ON.getNumber() && (group_id == null || e.getGroup() != null && e.getGroup().getId() == group_id) && m.getId() == material_id) {
                    return new FalseResult(ErrorType.DOUBLE).getResult();
                }
            }
            if (fromDate != null && toDate != null && chekDateInEvent(fromDate, toDate)) {
                return new FalseResult(ErrorType.BAD_INPUT).getResult();
            }
            if (fromDate == null) fromDate = new Date().getTime();
            if (toDate == null) toDate = fromDate + 7884000000L;
            Event event = new Event(user, "New material", EventType.MATERIAL, new Date(fromDate), "Once", new Date(toDate), "Notes");
            if (group_id != null && group_id > 0) {
                event.setGroup(groupService.getById(group_id));
            }
            if (note != null) {
                event.setNote(note);
            }
            if (name != null) {
                event.setName(name);
            }
            event.setEventableItem(materialService.getActiveById(material_id));
            eventService.create(event);
            long resultId = event.getId();
            Event result = eventService.getById(resultId);

            return new SuccessfulResult(result).getResult();
        } catch (Exception e) {
            return getErrorMapResponseEntity(e);
        }
    }

    @RequestMapping(value = "/rest/v1/openTest", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(EventView.RelationLevel.class)
    ResponseEntity openTest(@RequestHeader("token") String token,
                            @RequestParam(value = "test_id") Long test_id,
                            @RequestParam(value = "group_id", required = false) Long group_id,
                            @RequestParam(value = "fromDate", required = false) Long fromDate,
                            @RequestParam(value = "toDate", required = false) Long toDate,
                            @RequestParam(value = "name", required = false) String name,
                            @RequestParam(value = "note", required = false) String note) {

        User user = loginSessionService.getLoginSessionByToken(token).getUser();
        if (!user.hasPermission(PermissionType.EVENT, PermissionType.CREATE)) {
            return new FalseResult(ErrorType.NO_ACCESS).getResult();
        }
        try {
            List<Event> checkEvents = eventService.getAllbyType(EventType.TEST);
            for (Event e : checkEvents) {
                Test test = (Test) e.getEventableItem();
                if (e.getType().equals(EventType.TEST) && e.getSysStatus() == SysStatus.ON.getNumber() && (group_id == null || e.getGroup() != null && e.getGroup().getId() == group_id) && test.getId() == test_id) {
                    return new FalseResult(ErrorType.DOUBLE).getResult();
                }
            }
            if (fromDate != null && toDate != null && chekDateInEvent(fromDate, toDate)) {
                return new FalseResult(ErrorType.BAD_INPUT).getResult();
            }
            if (fromDate == null) fromDate = new Date().getTime();
            if (toDate == null) toDate = fromDate + 7884000000L;
            Event event = new Event(user, "New test", EventType.TEST, new Date(fromDate), "Once", new Date(toDate), "Notes");
            if (group_id != null && group_id > 0) {
                event.setGroup(groupService.getById(group_id));
            }
            if (note != null) {
                event.setNote(note);
            }
            if (name != null) {
                event.setName(name);
            }
            event.setEventableItem(testService.getActiveById(test_id));
            eventService.create(event);
            long resultId = event.getId();
            Event result = eventService.getById(resultId);

            return new SuccessfulResult(result).getResult();
        } catch (Exception e) {
            return getErrorMapResponseEntity(e);
        }
    }

    @RequestMapping(value = "/rest/v1/getMaterials", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(EventView.RelationLevel.class)
    ResponseEntity getMaterialsForUser(@RequestHeader("token") String token) {

        User user = loginSessionService.getLoginSessionByToken(token).getUser();
        if (!user.hasPermission(PermissionType.MATERIAL, PermissionType.READ)) {
            return new FalseResult(ErrorType.NO_ACCESS).getResult();
        }
        try {
            List<Material> materials = eventService.getMaterialsForUser(user.getId());
            return new SuccessfulResult(materials).getResult();
        } catch (Exception e) {
            return getErrorMapResponseEntity(e);
        }
    }

    @RequestMapping(value = "/rest/v1/getTests", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(EventView.RelationLevel.class)
    ResponseEntity getTestsForUser(@RequestHeader("token") String token) {

        User user = loginSessionService.getLoginSessionByToken(token).getUser();
        if (!user.hasPermission(PermissionType.TEST, PermissionType.READ)) {
            return new FalseResult(ErrorType.NO_ACCESS).getResult();
        }
        try {
            List<Test> tests = eventService.getTestsForUser(user.getId());
            return new SuccessfulResult(tests).getResult();
        } catch (Exception e) {
            return getErrorMapResponseEntity(e);
        }
    }

    private ResponseEntity getErrorMapResponseEntity(Exception e) {
        log.error(">>> MESSAGE ERROR - " + String.valueOf(e));

        String description = String.valueOf(e);

        log.debug(">>> ERROR returns: " + e.toString());
        return new FalseResult(e.getCause().toString(), description, HttpStatus.BAD_REQUEST).getResult();
    }

    private boolean chekDateInEvent(Long startTime, Long endTime) {
        return startTime > endTime;
    }

    private boolean checkCorrectLifitime(Calendar calendar) {
        return calendar.get(Calendar.HOUR_OF_DAY) > 0 || calendar.get(Calendar.MINUTE) > 0 ||
                calendar.get(Calendar.SECOND) > 0 || calendar.get(Calendar.MILLISECOND) > 0;
    }

    private boolean isUserHasRole(User user, String roleForCheck) {
        List<Role> roles = new ArrayList<>(user.getRoles());
        for (Role role : roles
                ) {
            if (role.getName().equals(roleForCheck)) {
                return true;
            }
        }
        return false;
    }

    private void isStudentOrGuest(Queue<User> queue) {
        for (User user : queue) {
            List<Role> roles = (List<Role>) user.getRoles();
            for (Role role : roles) {
                if (!role.getName().equals(RoleName.STUDENT) || !role.getName().equals(RoleName.GUEST)) {
                    queue.remove(user);
                }
            }
        }
    }
}
