package com.provectus.proveng.controller;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.provectus.proveng.TestAbstractController;
import com.provectus.proveng.domain.Group;
import com.provectus.proveng.utils.JsonUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestGroupMockController extends TestAbstractController {

    private ObjectNode groupNode;

    @Before
    public void setup() {
        super.setup();

        ObjectNode personBasicNode1 = om.createObjectNode();
        personBasicNode1.put("id", 1);
        ObjectNode personBasicNode2 = om.createObjectNode();
        personBasicNode2.put("id", 2);
        ObjectNode personBasicNode3 = om.createObjectNode();
        personBasicNode3.put("id", 3);
        ArrayNode membersArrayNode = om.createArrayNode();
        membersArrayNode.add(personBasicNode1);
        membersArrayNode.add(personBasicNode2);

        groupNode = om.createObjectNode();
        groupNode.put("name", "New group1");
        groupNode.set("leader", personBasicNode3);
        groupNode.put("level", "INTERMEDIATE");
        groupNode.set("members", membersArrayNode);

        // System.out.println(groupNode.toString());
    }

    @Test
    public void testDeleteGroupUser() throws Exception {

        String url = "/rest/mock/group_user?group_id=1&user_id=1&note=low attendance";
        mvc.perform(delete(url).accept(MediaType.APPLICATION_JSON).header("token", "123")
                .param("group_id", "1").param("user_id", "1").param("note", "low atendance"))
                .andExpect(status().isOk());
//				.andDo(document("deleteGroupUser", preprocessRequest(prettyPrint()),
//						preprocessResponse(prettyPrint()),
//						requestHeaders(headerWithName("token").description("local user's token")),
//						requestParameters(
//								parameterWithName("group_id")
//										.description("Integer: group identifier"),
//								parameterWithName("user_id")
//										.description("Integer: user identifier"),
//								parameterWithName("note")
//										.description("String: reason for deleting user")),
//						responseFields(fieldWithPath("result")
//								.description("message about sucess result"))));
    }

    @Test
    public void testGetGroup() throws Exception {

        String url = "/rest/mock/group?id=1";
        mvc.perform(get(url).accept(MediaType.APPLICATION_JSON).header("token", "123"))
                .andExpect(status().isOk());
//				.andDo(document("getGroup", preprocessRequest(prettyPrint()),
//						preprocessResponse(prettyPrint()),
//						requestHeaders(headerWithName("token").description("local user's token")),
//						requestParameters(parameterWithName("id")
//								.description("Integer: group identifier (optional)")),
//						responseFields(
//								fieldWithPath("result").description("group entity in JSON"))));

    }

    @Test
    public void testGetGroups() throws Exception {
        String url = "/rest/mock/groups";
        mvc.perform(get(url).accept(MediaType.APPLICATION_JSON).header("token", "123"))
                .andExpect(status().isOk());
//				.andDo(document("getGroups", preprocessRequest(prettyPrint()),
//						preprocessResponse(prettyPrint()),
//						requestHeaders(headerWithName("token").description("local user's token")),
//						responseFields(
//								fieldWithPath("result").description("group entities in JSON"))));
    }

    @Test
    public void testCreateGroup() throws Exception {
        String url = "/rest/mock/group";
        mvc.perform(post(url).accept(MediaType.APPLICATION_JSON).header("token", "123")
                .contentType(MediaType.APPLICATION_JSON).content(groupNode.toString()))
                .andExpect(status().isOk()).andExpect(jsonPath("result").isNotEmpty());


    }

    @SuppressWarnings("unchecked")
    @Test
    public void testUpdateGroup() throws Exception {
        String url = "/rest/mock/group";
        MvcResult mvcResult = mvc
                .perform(put(url).accept(MediaType.APPLICATION_JSON).header("token", "123")
                        .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isOk()).andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        Map<String, Group> responseMap = new HashMap<>();

        responseMap = JsonUtils.mapFromJson(content, responseMap.getClass());
        Object obj = responseMap.get("result");
        System.out.println("result = " + responseMap.get("result"));
        Assert.assertNotNull("errorTest: not null result expected", obj);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testDeleteGroup() throws Exception {
        String url = "/rest/mock/group";
        MvcResult mvcResult = mvc.perform(delete(url).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).header("token", "123")
                .param("id", "1")).andExpect(status().isOk()).andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        Map<String, Group> responseMap = new HashMap<>();

        responseMap = JsonUtils.mapFromJson(content, responseMap.getClass());
        Object obj = responseMap.get("result");
        System.out.println("result = " + responseMap.get("result"));
        Assert.assertNotNull("errorTest: not null result expected", obj);
    }

}
