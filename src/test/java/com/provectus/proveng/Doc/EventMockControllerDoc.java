package com.provectus.proveng.Doc;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.provectus.proveng.TestAbstractController;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;

import java.util.Date;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EventMockControllerDoc extends TestAbstractController {

    private ObjectNode eventNodeParent;
    private ObjectNode eventNode;


    @Before
    public void setup() {
        super.setup();
        long start = new Date().getTime();
        ObjectNode user0 = om.createObjectNode();
        user0.put("id", 1L);
        user0.put("firstName", "Alla");
        user0.put("lastName", "Efremova");
        user0.put("email", "teacher@provectus-it.com");
        ObjectNode user2 = om.createObjectNode();
        user2.put("id", 3L);
        user2.put("firstName", "Alexander");
        user2.put("lastName", "Pushkin");
        user2.put("email", "arap@yahoo.com");

        eventNodeParent = om.createObjectNode();
        eventNodeParent.put("id", 1L);

        eventNode = om.createObjectNode();
        eventNode.put("id", 2L);
        eventNode.put("name", "Accepted by user");
        eventNode.set("leader", user0);
        eventNode.set("creater", user2);
        eventNode.put("type", "Accepted");
        eventNode.set("parentEvent", eventNodeParent);
        eventNode.put("dateStart", start);
        eventNode.put("dateEnd", start + 5400000L);
        eventNode.put("note", "I can");

    }

    @Test
    public void testGetEvent() throws Exception {

        String url = "/rest/mock/event?id=7";
        mvc.perform(get(url).accept(MediaType.APPLICATION_JSON).header("token", "Some local token"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetEvents() throws Exception {

        String url = "/rest/mock/events";
        mvc.perform(get(url).accept(MediaType.APPLICATION_JSON).header("token", "Some local token"))
                .andExpect(status().isOk())
                .andDo(document("getEvents", preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("token").description("local user's token")),
                        requestParameters(parameterWithName("group_id (optional) Integer").optional().description("Find events for certain group"),
                                parameterWithName("user_id (optional) Integer").optional().description("Find events for certain user"),
                                parameterWithName("fromDate (optional) Long").optional().description("Find events from certain date"),
                                parameterWithName("toDate (optional) Long").optional().description("Find events to certain date"),
                                parameterWithName("type (optional) String").optional().description("Find events by type"),
                                parameterWithName("level (optional) String").optional().description("Find all events by level"),
                                parameterWithName("limit (optional) Integer").optional().description("Find events with limit quantity")),
                        responseFields(
                                fieldWithPath("result").description("Event entities in JSON"))));

    }

    @Test
    public void testPostAcceptedEvent() throws Exception {

        String url = "/rest/mock/eventAccepted?id=1";
        mvc.perform(post(url).accept(MediaType.APPLICATION_JSON).header("token", "123")
                .contentType(MediaType.APPLICATION_JSON).content(eventNode.toString()))
                .andExpect(status().isOk()).andExpect(jsonPath("result.id").isNotEmpty())
                .andExpect(status().isOk())
                .andDo(document("eventAccepted", preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("token")
                                .description("local user's token")),
                        requestParameters(parameterWithName("id")
                                .description("Accepted event by id (expected) Integer")),
                        responseFields(
                                fieldWithPath("result").description("Event entities in JSON"))));

    }
}

