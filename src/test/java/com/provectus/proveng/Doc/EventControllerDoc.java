package com.provectus.proveng.Doc;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.provectus.proveng.TestAbstractController;
import com.provectus.proveng.domain.*;
import com.provectus.proveng.enumaration.ErrorType;
import com.provectus.proveng.service.EventService;
import com.provectus.proveng.service.LoginSessionService;
import com.provectus.proveng.utils.FalseResult;
import com.provectus.proveng.utils.SuccessfulResult;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class EventControllerDoc extends TestAbstractController {

    @MockBean
    private EventService eventService;
    @MockBean
    private LoginSessionService loginSessionService;
    @MockBean
    private SuccessfulResult successfulResult;
    @MockBean
    private FalseResult falseResult;

    private ObjectNode eventNode, lifetimeNode, schedule1Node, schedule2Node, leaderNode1, leaderNode2, leaderNode3;
    private ArrayNode eventsArrayNode;
    private Event event;
    private User user0 = new User(1L, "Alla", "Efremova", "teacher@provectus-it.com");
    private User user1 = new User(2L, "Leon", "Tolstoy", "tolstoy@mail.ru");
    private Group group1 = new Group(user0, "Newbies", "Beginner");
    private Event eventA1 = new Event(2L, user0, null, 1L, "Monday Lesson", "Schedule", new Date(), "Weekly", new Date(new Date().getTime() + 7948800000L), "Notes");
    private Event eventB1 = new Event(4L, user0, null, 1L, "Introductory lesson.", "Lesson", new Date(), "Once", new Date(new Date().getTime() + 5400000L), "This first lesson for beginners group Elementary level");
    private Event lt = new Event(5L, user0, null, 1L, null, "Lifetime", new Date(1475442000000L), "Once", new Date(1483477200000L), null);
    private Event schd1 = new Event(6L, user0, null, 1L, null, "Schedule", new Date(480600000L), "Once", new Date(1474987500000L), null);
    private Event schd2 = new Event(7L, user0, null, 1L, null, "Schedule", new Date(484200000L), "Once", new Date(1474994700000L), null);
    private Location location1 = new Location(1L, "China, Tower", "China, Tower", 15);
    private Location location2 = new Location(2L, "San Diego, USA", "San Diego, USA", 8);
    private List<Event> events = new ArrayList<>();
    private LoginSession loginSession;
    private SuccessfulResult successfulResultEvent;
    private FalseResult falseResultEvent;

    @Before
    public void setup() {
        super.setup();

        Group group = group1;
        group.addUser(user1);

        event = eventB1;
        event.setGroup(group);
        event.setLocation(location1);
        event.setParentEvent(eventA1);

        ResponseEntity createResultEvent = new SuccessfulResult(event).getResult();
        ResponseEntity createErrorEvent = new FalseResult(ErrorType.NO_ACCESS).getResult();

        group1.setId(1L);
        schd1.setLocation(location1);
        schd1.setGroup(group1);
        schd2.setLocation(location2);
        schd2.setGroup(group1);

        events.add(lt);
        events.add(schd1);
        events.add(schd2);

        eventsArrayNode = om.createArrayNode();
        loginSession = new LoginSession(user0, "token");

        ObjectNode leaderNode1 = om.createObjectNode();
        leaderNode1.put("id", 73);
        ObjectNode leaderNode2 = om.createObjectNode();
        leaderNode2.put("id", 72);
        ObjectNode leaderNode3 = om.createObjectNode();
        leaderNode3.put("id", 71);

        ObjectNode groupNode = om.createObjectNode();
        groupNode.put("id", 1L);
        groupNode.put("name", "Newbies");
        groupNode.put("level", "Beginner");

        ObjectNode location1Node = om.createObjectNode();
        location1Node.put("id", 1L);
        location1Node.put("place", "China, Tower");
        location1Node.put("roominess", 15);

        ObjectNode location2Node = om.createObjectNode();
        location2Node.put("id", 2L);
        location2Node.put("place", "San Diego, USA");
        location2Node.put("roominess", 8);

        ObjectNode lifetimeNode = om.createObjectNode();
        lifetimeNode.put("id", 12L);
        lifetimeNode.set("leader", leaderNode3);
        lifetimeNode.set("group", groupNode);
        lifetimeNode.put("type", "Lifetime");
        lifetimeNode.put("dateStart", 1475442000000L);
        lifetimeNode.put("dateEnd", 1483477200000L);

        ObjectNode schedule1Node = om.createObjectNode();
        schedule1Node.put("id", 13L);
        schedule1Node.set("leader", leaderNode1);
        schedule1Node.set("group", groupNode);
        schedule1Node.put("type", "Schedule");
        schedule1Node.put("dateStart", 480600000L);
        schedule1Node.put("dateEnd", 1474987500000L);
        schedule1Node.set("location", location1Node);

        ObjectNode schedule2Node = om.createObjectNode();
        schedule2Node.put("id", 14L);
        schedule2Node.set("leader", leaderNode2);
        schedule2Node.set("group", groupNode);
        schedule2Node.put("type", "Schedule");
        schedule2Node.put("dateStart", 484200000L);
        schedule2Node.put("dateEnd", 1474994700000L);
        schedule2Node.set("location", location2Node);

        eventsArrayNode.add(lifetimeNode);
        eventsArrayNode.add(schedule1Node);
        eventsArrayNode.add(schedule2Node);


    }

    @Ignore
    @Test
    public void testGetEvent() throws Exception {

        String url = "/rest/v1/event?id=1";

        when(eventService.getById(any(Long.class))).thenReturn(this.event);
        when(loginSessionService.getLoginSessionByToken(any(String.class))).thenReturn(this.loginSession);
//        when(new CreateResult(any(Event.class))).thenReturn(createResultEvent);
        when(successfulResult).thenReturn(successfulResultEvent);
        when(falseResult).thenReturn(falseResultEvent);
//        when(new CreateResult(any(Event.class))).thenReturn(createResultEvent);
        when(new FalseResult(any(ErrorType.class))).thenReturn(falseResultEvent);

        mvc.perform(get(url).accept(MediaType.APPLICATION_JSON).header("token", "jsession1"))
                .andExpect(status().isOk())
                .andDo(document("getEventMock", preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("token").description("local user's token")),
                        requestParameters(parameterWithName("id")
                                .description("Integer: event identifier (expected)"))
//                        responseFields(
//                                fieldWithPath("result").description("Event entity in JSON"))
                ));

    }

    @Ignore
    @Test
    public void testPostSchedule() throws Exception {

        String url = "/rest/v1/schedule?group_id=4";
        when(eventService.create(any(Event.class))).thenReturn(this.event);

        mvc.perform(post(url).accept(MediaType.APPLICATION_JSON).header("token", "jsession1")
                .contentType(MediaType.APPLICATION_JSON).content(eventsArrayNode.toString()))
                .andExpect(status().isOk())
                .andDo(document("postSchedule", preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("token").description("local user's token")),
                        requestParameters(parameterWithName("group_id").description("Integer: group identifier (expected)")),
                        responseFields(
                                fieldWithPath("result").description("Event entity in JSON"))));
    }

    @Ignore
    @Test
    public void testGetSchedule() throws Exception {

        String url = "/rest/v1/schedule?group_id=1";
        when(eventService.getSchedule(any(Long.class))).thenReturn(this.events);

        mvc.perform(get(url).accept(MediaType.APPLICATION_JSON).header("token", "jsession1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("getSchedule", preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("token").description("local user's token")),
                        requestParameters(parameterWithName("group_id").description("Integer: group identifier (expected)")),
                        responseFields(
                                fieldWithPath("result").description("Event entity in JSON"))));
    }

    @Ignore
    @Test
    public void testGetEvents() throws Exception {

        String url = "/rest/v1/events";
        mvc.perform(get(url).accept(MediaType.APPLICATION_JSON).header("token", "jsession1"))
                .andExpect(status().isOk());
//                .andDo(document("getEventsMock", preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        requestHeaders(headerWithName("token").description("local user's token")),
////                        requestParameters(parameterWithName("id")
////                                .description("Integer: event identifier (expected)")),
//                        responseFields(
//                                fieldWithPath("result").description("Event entities in JSON"))));

    }
}

