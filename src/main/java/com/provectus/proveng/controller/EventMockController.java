package com.provectus.proveng.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.provectus.proveng.domain.*;
import com.provectus.proveng.enumaration.ErrorType;
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

import java.util.*;

@Controller
public class EventMockController {

    private static final Logger log = LogManager.getLogger(EventMockController.class);

    static {
        log.info("Started EventMockController");
    }

    @Autowired
    private SuccessfulResult successfulResult;
    private DayBook dayBook1, dayBook2;

    private User user0 = new User(1L, "Alla", "Efremova", "teacher@provectus-it.com");
    private User user1 = new User(2L, "Leon", "Tolstoy", "tolstoy@mail.ru");
    private User user2 = new User(3L, "Alexander", "Pushkin", "arap@yahoo.com");
    private User user3 = new User(4L, "Gennady", "Skorikov", "gennnna@gmail.com");
    private User user4 = new User(5L, "Eugene", "Kolmikov", "ea@gmail.com");

    private Event event0 = new Event(1L, user0, null, 1L, "ParentEvent", "Lifetime", new Date(), "once", new Date(new Date().getTime() + 7948800000L), "Notes");
    private Event eventA1 = new Event(2L, user0, null, 1L, "Monday Lesson", "Schedule", new Date(), "weekly", new Date(new Date().getTime() + 7948800000L), "Notes");

    private Event eventA2 = new Event(3L, user0, null, 1L, "Wednesday Lesson", "Schedule", new Date(), "weekly", new Date(new Date().getTime() + 7948800000L), "Notes");
    private Event eventB1 = new Event(4L, user0, null, 1L, "Introductory lesson.", "Lesson", new Date(), "once", new Date(new Date().getTime() + 5400000L), "This first lesson for beginners group Elementary level");
    private Event eventB2 = new Event(5L, user0, null, 1L, "Second lesson.", "Lesson", new Date(), "once", new Date(new Date().getTime() + 5400000L), "This second lesson for beginners group Elementary level");
    private Event eventB3 = new Event(6L, user0, null, 1L, "Third lesson.", "Lesson", new Date(), "once", new Date(new Date().getTime() + 5400000L), "This third lesson for beginners group Elementary level");
    private Event eventB4 = new Event(7L, user0, null, 1L, "Workshop for beginners", "Workshop", new Date(), "once", new Date(new Date().getTime() + 5400000L), "This workshop for beginners");
    private Event eventB41 = new Event(8L, user0, null, 1L, "Workshop for beginners", "Workshop", new Date(), "once", new Date(new Date().getTime() + 5400000L), "This workshop for beginners");
    private Event eventB42 = new Event(9L, user0, null, 1L, "Workshop for beginners", "Workshop", new Date(), "once", new Date(new Date().getTime() + 5400000L), "This workshop for beginners");
    private Event eventB5 = new Event(10L, user0, null, 1L, "Challenge for intermediate", "Challenge", new Date(), "once", new Date(new Date().getTime() + 5400000L), "This challenge for users who passed all FinalTests");

    private Event eventB6 = new Event(11L, user0, user1, 1L, "Notification for user", "Notification", new Date(), null, new Date(new Date().getTime() + 5400000L), "Arnold shared a link");
    private Event eventС1 = new Event(12L, user0, user2, 1L, "Accepted by user", "Accepted", new Date(), null, new Date(new Date().getTime() + 5400000L), "I can");
    private Event eventС2 = new Event(13L, user0, user3, 1L, "Denied by user", "Denied", new Date(), null, new Date(new Date().getTime() + 5400000L), "I can`t");
    private Event eventС3 = new Event(14L, user0, user1, 1L, "Added mark 'visited' from teacher", "Visited", new Date(), null, new Date(new Date().getTime() + 5400000L), "User3 visited");
    private Event eventС4 = new Event(15L, user0, user4, 1L, "Added mark 'visited' from teacher", "Visited", new Date(), null, new Date(new Date().getTime() + 5400000L), "User4 visited");

    private Group group1 = new Group(user0, "Newbies", "Beginner");
    private Group group2 = new Group(user0, "Group #1", "Pre-intermediate");

    private Location location1 = new Location(1L, "China, Tower", "China, Tower", 15);
    private Location location2 = new Location(2L, "San Diego, USA", "San Diego, USA", 8);

    /**
     * rest call for event info
     *
     * @param id Long
     * @return Event entity in Json with next params:
     * {
     * "result": {
     * "id": 0, (BasicInfoLevel)
     * "sysStatus": 0, (hide)
     * "createDtm": 1471350145526, (hide)
     * "modifyDtm": null, (hide)
     * "leader_id": 1, (BasicInfoLevel)
     * "location_id": 1, (BasicInfoLevel)
     * "group_id": 1, (BasicInfoLevel)
     * "name": "Introductory lesson.", (StandartInfoLevel)
     * "type": "Lesson", (StandartInfoLevel)
     * "dateStart": 1471350145526, (StandartInfoLevel)
     * "regular": "once", (StandartInfoLevel)
     * "dateEnd": 1471355545526, (StandartInfoLevel)
     * "note": "This first lesson for beginners group Elementary level",  (FullInfoLevel)
     * "eventB": [],(FullInfoLevel)
     * "dayBook": [],(FullInfoLevel)
     * "group": null, (FullInfoLevel)
     * "location": null, (FullInfoLevel)
     * "users": [],(FullInfoLevel)
     * "eventA": null (FullInfoLevel)
     * }
     * }
     * @header token String (local token of our application)
     */
    @RequestMapping(value = "/rest/mock/event", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    @JsonView(EventView.AllInfoLevel.class)
    ResponseEntity getEventMock(@RequestHeader("token") String token,
                                @RequestParam(value = "id") long id) {
        try {
            log.debug(">>> INJECTED 'getEventMock' token: " + token);

            Group group = group1;
            group1.setId(1L);
            group.addUser(user1);
            group.addUser(user2);
            group.addUser(user3);

            Event event = eventB1;
//            event.setChildEvents();
//            event.setDayBook();
            event.setGroup(group);
            event.setLocation(location1);
//            event.setUsers();
            event.setParentEvent(eventA1);

            SuccessfulResult successfulResult = new SuccessfulResult(event);

            log.debug(">>> RESULT 'getEventMock' returns: " + successfulResult.toString());

            return successfulResult.getResult();
        } catch (Exception e) {
            return getErrorMapResponseEntity(e);
        }
    }

    /**
     * rest call for events info by parameters
     *
     * @param group_id Long - optionally
     * @param fromDate Long - optionally
     * @param toDate   Long - optionally
     * @param type     String - optionally
     * @param level    String - optionally
     * @param limit    String - optionally
     * @return Event entities in Json with next params:
     * {
     * «result» : [
     * {
     * "id": 0,
     * "sysStatus": 0,
     * "createDtm": 1471347123868,
     * "modifyDtm": null,
     * "leader_id": 1,
     * "location_id": 1,
     * "group_id": 1,
     * "name": "Introductory lesson.",
     * "type": "Lesson",
     * "dateStart": 1471347123868,
     * "regular": "once",
     * "dateEnd": 1471352523868,
     * "note": "This first lesson for beginners group Elementary level",
     * "eventB": [],
     * "dayBook": [],
     * "group": null,
     * "location": null,
     * "users": [],
     * "eventA": null
     * },
     * {
     * "id": 0,
     * "sysStatus": 0,
     * "createDtm": 1471354201943,
     * "modifyDtm": null,
     * "leader_id": 1,
     * "location_id": 2,
     * "group_id": 1,
     * "name": "Second lesson.",
     * "type": "Lesson",
     * "dateStart": 1471354201943,
     * "regular": "once",
     * "dateEnd": 1471359601943,
     * "note": "This second lesson for beginners group Elementary level",
     * "eventB": [],
     * "dayBook": [],
     * "group": null,
     * "location": null,
     * "users": [],
     * "eventA": null
     * }
     * ]
     * }
     * @header token String (local token of our application)
     */
    @RequestMapping(value = "/rest/mock/events", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    ResponseEntity getEventsMock(@RequestHeader("token") String token,
                                 @RequestParam(value = "group_id", required = false) Long group_id,
                                 @RequestParam(value = "fromDate", required = false) Long fromDate,
                                 @RequestParam(value = "toDate", required = false) Long toDate,
                                 @RequestParam(value = "type", required = false) String type,
                                 @RequestParam(value = "level", required = false) String level,
                                 @RequestParam(value = "user_id", required = false) Long user_id,
                                 @RequestParam(value = "limit", required = false) String limit) {
        try {
            log.debug(">>> INJECTED 'getEventsMock' token: " + token);


            Set<Event> work1 = new HashSet<>();
            work1.add(eventС1);
            eventB41.setChildEvents(work1);

            Set<Event> work2 = new HashSet<>();
            work2.add(eventС2);
            eventB42.setChildEvents(work2);

            Set<Event> events = new HashSet<>();
            events.add(eventB2);
            events.add(eventB3);
            events.add(eventB4);
            events.add(eventB41);
            events.add(eventB42);
            events.add(eventB5);
            events.add(eventB6);

            SuccessfulResult successfulResult = new SuccessfulResult(events);

            log.debug(">>> RESULT 'getEventsMock' returns: " + successfulResult.toString());

            return successfulResult.getResult();
        } catch (Exception e) {
            return getErrorMapResponseEntity(e);
        }
    }


    /**
     * rest call for change event
     *
     * @param id Long - expected
     * @return Event entity in Json with next params:
     * {
     * «result» : {
     * "id": 0,
     * "sysStatus": 0,
     * "createDtm": 1471354201943,
     * "modifyDtm": null,
     * "leader_id": someId,
     * "location_id": someId,
     * "group_id": someId,
     * "name": "someName",
     * "type": "someType",
     * "dateStart": 1471354201943,
     * "regular": "someRegular",
     * "dateEnd": 1471359601943,
     * "note": "someNote",
     * "eventB": [],
     * "dayBook": [],
     * "group": null,
     * "location": null,
     * "users": [],
     * "eventA": null
     * }
     * }
     * @body {
     * All fields for change.
     * }
     * @header token String (local token of our application)
     */
    @RequestMapping(value = "/rest/mock/event", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    ResponseEntity changeEventMock(@RequestHeader("token") String token,
                                   @RequestParam(value = "id") Long id,
                                   @RequestBody Event event) {

        SuccessfulResult successfulResult = new SuccessfulResult(event);
        return successfulResult.getResult();
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
    @RequestMapping(value = "/rest/mock/event", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    @JsonView(EventView.ShortInfoLevel.class)
    ResponseEntity setEvent(@RequestHeader("token") String token,
                            @RequestBody Event event) {
        try {
            log.debug(">>> INJECTED 'changeEventMock' token: " + token);

            SuccessfulResult successfulResult;
            if (event.getName() != null && event.getLeader() != null && event.getGroup() != null &&
                    event.getType() != null && event.getDateStart() != null && event.getDateEnd() != null &&
                    event.getRegular() != null && event.getNote() != null && event.getLocation() != null) {
                successfulResult = new SuccessfulResult(event);
            } else {
                return new FalseResult(ErrorType.EMPTY_FIELDS).getResult();
            }

            log.debug(">>> RESULT 'changeEventMock' returns: " + successfulResult.toString());

            return successfulResult.getResult();
        } catch (Exception e) {
            return getErrorMapResponseEntity(e);
        }
    }

    /**
     * rest call for delete event
     *
     * @param id Long - expected
     * @return New JSON object:
     * {
     * «result» : {
     * "Status": OK,
     * "Message": "Event with id remove successfully"
     * }
     * }
     * @header token String (local token of our application)
     */
    @RequestMapping(value = "/rest/mock/event", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    ResponseEntity deleteEvent(@RequestHeader("token") String token,
                               @RequestParam(value = "id") Long id) {

        Map<String, String> result = new HashMap<>();
        result.put("Status", String.valueOf(HttpStatus.OK));
        result.put("Message", "Event with id: '" + id + "', remove successfuly");
        SuccessfulResult successfulResult = new SuccessfulResult(result);
        return successfulResult.getResult();
    }

    /**
     * rest call for cancel event
     *
     * @param id Long - expected
     * @return New JSON object:
     * {
     * "result": {
     * "id": 13,
     * "creater": {"id": 4},
     * "name": "Denied by user",
     * "type": "Denied",
     * "note": "I can`t"
     * }
     * }
     * @header token String (local token of our application)
     */
    @RequestMapping(value = "/rest/mock/eventDenied", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    @JsonView(EventView.ShortInfoLevel.class)
    ResponseEntity eventCancelledMock(@RequestHeader("token") String token,
                                      @RequestParam(value = "id") long id) {
        try {
            log.debug(">>> INJECTED 'eventCancelledMock' token: " + token);

            SuccessfulResult successfulResult = new SuccessfulResult(eventС2);

            log.debug(">>> RESULT 'eventCancelledMock' returns: " + successfulResult.toString());

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
    @RequestMapping(value = "/rest/mock/eventAccepted", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    @JsonView(EventView.ShortInfoLevel.class)
    ResponseEntity eventAcceptedMock(@RequestHeader("token") String token,
                                     @RequestParam(value = "id") long id) {
        try {
            log.debug(">>> INJECTED 'eventAcceptedMock' token: " + token);

            SuccessfulResult successfulResult = new SuccessfulResult(eventС1);

            log.debug(">>> RESULT 'eventAcceptedMock' returns: " + successfulResult.toString());

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
     * @body Set(Users) with id only
     */
    @RequestMapping(value = "/rest/mock/eventVisited", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    @JsonView(EventView.ShortInfoLevel.class)
    ResponseEntity eventVisitedMock(@RequestHeader("token") String token,
                                    @RequestParam(value = "id") long id,
                                    @RequestBody ArrayList<Map> users) {
        try {
            log.debug(">>> INJECTED 'eventVisitedMock' token: " + token);

            ArrayList<Map> out = new ArrayList<>();
            JsonFactory jFactory = new JsonFactory();
            JsonParser jParser = jFactory.createParser(String.valueOf(users));
            ObjectMapper mapper = new ObjectMapper();
            JsonNode input = mapper.readTree(jParser);
            JsonNode results = input.get("id");
            results = results.get("id");

            for (final JsonNode element : results) {
                JsonNode fieldname = element.get("id");
                Iterator<Map.Entry<String, JsonNode>> fieldsIterator = fieldname.fields();
                Map<String, String> map = new HashMap<String, String>();
                while (fieldsIterator.hasNext()) {
                    Map.Entry<String, JsonNode> field = fieldsIterator.next();
                    map.put(field.getKey(), field.getValue().toString());
                }
                out.add(map);
            }
            System.out.println(out);

            successfulResult = new SuccessfulResult(eventС1);

            log.debug(">>> RESULT 'eventVisitedMock' returns: " + successfulResult.toString());

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
     * "result": [
     * {
     * "id": 12,
     * "creater": {"id": 3},
     * "name": "Accepted by user",
     * "type": "Accepted",
     * "note": "I can"
     * },
     * {
     * "id": 13,
     * "creater": {"id": 4},
     * "name": "Cancelled by user",
     * "type": "Cancelled",
     * "note": "I can`t"
     * },
     * {
     * "id": 14,
     * "name": "Added mark 'visited' from teacher",
     * "creater": {"id": 2},
     * "type": "Visited",
     * "note": "User3 visited"
     * }
     * }
     * @header token String (local token of our application)
     */
    @RequestMapping(value = "/rest/mock/eventUsersStatus", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    @JsonView(EventView.ShortInfoLevel.class)
    ResponseEntity eventUsersStatusMock(@RequestHeader("token") String token,
                                        @RequestParam(value = "id") long id) {
        try {
            log.debug(">>> INJECTED 'eventUsersStatusMock' token: " + token);

            Set<Event> events = new HashSet<>();
            events.add(eventС1);
            events.add(eventС2);
            events.add(eventС3);
            events.add(eventС4);
            SuccessfulResult successfulResult = new SuccessfulResult(events);

            log.debug(">>> RESULT 'eventUsersStatusMock' returns: " + successfulResult.toString());

            return successfulResult.getResult();
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
     * "id": 0, "sysStatus": 0, "createDtm": 1471432640752, "modifyDtm": null, "leader_id": null,
     * "location_id": null, "group_id": null, "name": null, "type": "schedule", "dateStart": 1471347123868,
     * "regular": "weekly", "dateEnd": 1471352523868,"note": null,"eventB": [],"dayBook": [],"group": null,
     * "location": null,"users": [],"eventA": null
     * },
     * {
     * "id": 0,"sysStatus": 0,"createDtm": 1471432640753,"modifyDtm": null,"leader_id": null,"location_id": null,
     * "group_id": null,"name": null,"type": "schedule","dateStart": 1471347123868,"regular": "weekly",
     * "dateEnd": 1471352523868,"note": null,"eventB": [],"dayBook": [],"group": null,"location": null,
     * "users": [],"eventA": null
     * },
     * {
     * "id": 0,"sysStatus": 0,"createDtm": 1471432640753,"modifyDtm": null,"leader_id": null,"location_id": null,
     * "group_id": null,"name": null,"type": "lifetime","dateStart": 1471347123868,"regular": "Once","dateEnd": 1471352523868,
     * "note": "someNotes","eventB": [],"dayBook": [],"group": null,"location": null,"users": [],"eventA": null
     * }
     * ]
     * }
     * @header token String (local token of our application)
     * @body [
     * {
     * "type": "schedule",
     * "regular": "weekly",
     * "startTime": 1471347123868L,
     * "endTime": 1471352523868L,
     * },
     * {
     * "type": "schedule",
     * "regular": "weekly",
     * "startTime": 1471347123868L,
     * "endTime": 1471352523868L,
     * },
     * {
     * "type": "lifetime",
     * "note": "someNotes",
     * "dateStart": 1471347123868L,
     * "dateEnd": 1471352523868L,
     * }
     * ]
     */
    @RequestMapping(value = "/rest/mock/schedule", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    @JsonView(EventView.CalendarInfoLevel.class)
    ResponseEntity setScheduleMock(@RequestHeader("token") String token,
                                   @RequestParam(value = "group_id") Long group_id,
                                   @RequestBody List<Event> events) {
        try {
            log.debug(">>> INJECTED 'setScheduleMock' token: " + token);

            SuccessfulResult successfulResult;
            if (events.get(0).getType() != null && events.get(0).getDateStart() != null && events.get(0).getDateEnd() != null &&
                    (events.get(0).getType().equals("lifetime") || events.get(0).getType().equals("schedule") && events.get(0).getLocation() != null) &&
                    events.get(1).getType() != null && events.get(1).getDateStart() != null && events.get(1).getDateEnd() != null &&
                    (events.get(1).getType().equals("lifetime") || events.get(1).getType().equals("schedule") && events.get(1).getLocation() != null) &&
                    events.get(2).getType() != null && events.get(2).getDateStart() != null && events.get(2).getDateEnd() != null &&
                    (events.get(2).getType().equals("lifetime") || events.get(2).getType().equals("schedule") && events.get(2).getLocation() != null)) {
                successfulResult = new SuccessfulResult(events);
            } else {
                return new FalseResult(ErrorType.EMPTY_FIELDS).getResult();
            }

            log.debug(">>> RESULT 'setScheduleMock' returns: " + successfulResult.toString());

            return successfulResult.getResult();
        } catch (Exception e) {
            return getErrorMapResponseEntity(e);
        }
    }

    /**
     * rest call for schedule
     *
     * @param group_id - expected
     * @return Event entities in Json with next params:
     * {
     * "result": [
     * {
     * "id": 0,
     * "sysStatus": 0,
     * "createDtm": 1471434308030,
     * "modifyDtm": null,
     * "leader_id": 1,
     * "location_id": 1,
     * "group_id": 1,
     * "name": "Monday Lesson",
     * "type": "Schedule",
     * "dateStart": 1471434308030,
     * "regular": "weekly",
     * "dateEnd": 1479383108030,
     * "note": "Notes",
     * "eventB": [],
     * "dayBook": [],
     * "group": null,
     * "location": null,
     * "users": [],
     * "eventA": null
     * },
     * {
     * "id": 0,
     * "sysStatus": 0,
     * "createDtm": 1471434308030,
     * "modifyDtm": null,
     * "leader_id": 1,
     * "location_id": 2,
     * "group_id": 1,
     * "name": "Wednesday Lesson",
     * "type": "Schedule",
     * "dateStart": 1471434308030,
     * "regular": "weekly",
     * "dateEnd": 1479383108030,
     * "note": "Notes",
     * "eventB": [],
     * "dayBook": [],
     * "group": null,
     * "location": null,
     * "users": [],
     * "eventA": null
     * },
     * {
     * "id": 0,
     * "sysStatus": 0,
     * "createDtm": 1471434308030,
     * "modifyDtm": null,
     * "leader_id": 1,
     * "location_id": 1,
     * "group_id": 1,
     * "name": "ParentEvent",
     * "type": "Lifetime",
     * "dateStart": 1471434308030,
     * "regular": "once",
     * "dateEnd": 1479383108030,
     * "note": "Notes",
     * "eventB": [],
     * "dayBook": [],
     * "group": null,
     * "location": null,
     * "users": [],
     * "eventA": null
     * }
     * ]
     * }
     * @header token String (local token of our application)
     */
    @RequestMapping(value = "/rest/mock/schedule", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    @JsonView(EventView.MediumInfoLevel.class)
    ResponseEntity getScheduleMock(@RequestHeader("token") String token,
                                   @RequestParam(value = "group_id") Long group_id) {
        try {
            log.debug(">>> INJECTED 'getScheduleMock' token: " + token);

            eventA1.setLocation(location1);
            eventA2.setLocation(location2);
            List<Event> result = new ArrayList<>();
            result.add(eventA1);
            result.add(eventA2);
            result.add(event0);

            SuccessfulResult successfulResult = new SuccessfulResult(result);

            log.debug(">>> RESULT 'getScheduleMock' returns: " + successfulResult.toString());

            return successfulResult.getResult();
        } catch (Exception e) {
            return getErrorMapResponseEntity(e);
        }
    }

    private ResponseEntity getErrorMapResponseEntity(Exception e) {
        log.error(">>> MESSAGE ERROR - " + String.valueOf(e));

        String description = String.valueOf(e);

        FalseResult falseResult = new FalseResult(e.getCause().toString(), description, HttpStatus.BAD_REQUEST);
        log.debug(">>> ERROR returns: " + e.toString());
        return falseResult.getResult();
    }
}
