package com.provectus.proveng.controller;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.provectus.proveng.TestAbstractController;
import com.provectus.proveng.domain.Group;
import com.provectus.proveng.domain.Role;
import com.provectus.proveng.domain.User;
import com.provectus.proveng.domain.dto.UserShortDto;
import com.provectus.proveng.enumaration.RoleName;
import com.provectus.proveng.service.GroupService;
import com.provectus.proveng.service.UserService;
import com.provectus.proveng.utils.TestEntityUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestGroupControllerDoc extends TestAbstractController {

    private ObjectNode groupNode;
    private ArrayNode usersForDeleteArrayNode;
    private Group group;
    private User user1, user2;

    @MockBean
    private GroupService groupService;
    @MockBean
    private UserService userService;

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
        groupNode.put("name", "Group1");
        groupNode.set("leader", personBasicNode3);
        groupNode.put("level", "Intermediate");
        groupNode.set("members", membersArrayNode);

        usersForDeleteArrayNode = om.createArrayNode();
        ObjectNode user1ForDeleteNode = om.createObjectNode();
        ObjectNode user2ForDeleteNode = om.createObjectNode();
        user1ForDeleteNode.put("userId", 1);
        user1ForDeleteNode.put("note", "some reason");
        user2ForDeleteNode.put("userId", 2);
        user2ForDeleteNode.put("note", "other reason");
        usersForDeleteArrayNode.add(user1ForDeleteNode);
        usersForDeleteArrayNode.add(user2ForDeleteNode);

        group = TestEntityUtils.createTestGroup(1L, "Intermediate");
        user1 = TestEntityUtils.createTestUser(1L);
        user1.addRole(new Role(RoleName.ADMIN));
        user2 = TestEntityUtils.createTestUser(2L);
    }

    @Test
    public void testCreateGroup() throws Exception {
        String url = "/rest/v1/group";
        when(groupService.create(any(Group.class))).thenReturn(this.group);
        when(groupService.refresh(any(Group.class))).thenReturn(this.group);

        mvc.perform(post(url).accept(MediaType.APPLICATION_JSON).header("token", "123")
                .contentType(MediaType.APPLICATION_JSON).content(groupNode.toString()))
                .andExpect(status().isOk()).andExpect(jsonPath("result.name").isNotEmpty())
                .andDo(document("createGroup", preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("token").description("local user's token")),
                        requestFields(fieldWithPath("name").description("group's name"),
                                fieldWithPath("leader").description("leader of the group"),
                                fieldWithPath("level").description("level of the group"),
                                fieldWithPath("members").description("array of member`s ids")),
                        responseFields(fieldWithPath("result")
                                .description("created group entity in JSON"))));

    }

    @Test
    public void testGetGroup() throws Exception {

        String url = "/rest/v1/group?id=1";

        when(groupService.getById(any(Long.class))).thenReturn(this.group);

        when(userService.getUserIdByToken(any(String.class))).thenReturn(1L);
        when(userService.getRoleNamesForUser(any(Long.class)))
                .thenReturn(new ArrayList<String>(Arrays.asList(RoleName.TEACHER)));
        when(groupService.getMembersDtoForGroupById(group.getId()))
                .thenReturn(new ArrayList<UserShortDto>(Arrays.asList(UserShortDto.convertToDto(user2))));

        mvc.perform(get(url).accept(MediaType.APPLICATION_JSON).header("token", "123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("result.level").value("Intermediate"))
                .andDo(document("getGroup", preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("token").description("local user's token")),
                        requestParameters(parameterWithName("id")
                                .description("Integer: group identifier (optional)")),
                        responseFields(
                                fieldWithPath("result").description("group entity in JSON"))));
    }

    @Test
    public void testGetGroups() throws Exception {
        String url = "/rest/v1/groups";

        when(groupService.getGroupsForUser(any(User.class)))
                .thenReturn(TestEntityUtils.createTestGroups());
        when(groupService.getAllGroups()).thenReturn(TestEntityUtils.createTestGroups());
        when(groupService.getGroupsForTeacher(any(User.class)))
                .thenReturn(TestEntityUtils.createTestGroups());
        when(userService.getUserFromSessionByToken(any(String.class))).thenReturn(this.user1);

        mvc.perform(get(url).accept(MediaType.APPLICATION_JSON).header("token", "jsession1"))
                .andExpect(status().isOk()).andExpect(jsonPath("result").isNotEmpty())
                .andDo(document("getGroups", preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("token").description("local user's token")),
                        responseFields(
                                fieldWithPath("result").description("group entities in JSON"))));
    }

    @Test
    public void testUpdateGroup() throws Exception {
        String url = "/rest/v1/group";

        when(groupService.update(any(Group.class))).thenReturn(this.group);
        when(groupService.getById(any(Long.class))).thenReturn(this.group);
        when(userService.getById(any(Long.class))).thenReturn(this.user1);

        mvc.perform(put(url).accept(MediaType.APPLICATION_JSON).header("token", "jsession1")
                .contentType(MediaType.APPLICATION_JSON).content(groupNode.put("id", 1).toString()))
                .andExpect(status().isOk()).andExpect(jsonPath("result.id").value(1))
                .andDo(document("updateGroup", preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("token").description("local user's token")),
                        requestFields(fieldWithPath("id").description("group id "),
                                fieldWithPath("name").description("group's name"),
                                fieldWithPath("leader").description("leader of the group"),
                                fieldWithPath("level").description("level of the group"),
                                fieldWithPath("members").description("array of member`s ids")),
                        responseFields(fieldWithPath("result")
                                .description("updated group entity in JSON"))));
    }

    @Test
    public void testDeleteGroup() throws Exception {

        String url = "/rest/v1/group";

        mvc.perform(delete(url).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).header("token", "123").param("id", "1"))
                .andExpect(status().isOk()).andExpect(jsonPath("result").isNotEmpty())
                .andDo(document("deleteGroup", preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("token").description("local user's token")),
                        requestParameters(parameterWithName("id")
                                .description("Integer: group identifier (optional)")),
                        responseFields(fieldWithPath("result")
                                .description("message about sucess result"))));
    }

    @Test
    public void testDeleteGroupUser() throws Exception {

        // String url = "/rest/v1/group_user?group_id=1&user_id=1&note=low
        // attendance";
        // mvc.perform(delete(url).accept(MediaType.APPLICATION_JSON).header("token",
        // "123")
        // .param("group_id", "1").param("user_id", "1").param("note", "low
        // atendance"))
        // .andExpect(status().isOk())
        // .andDo(document("deleteGroupUser", preprocessRequest(prettyPrint()),
        // preprocessResponse(prettyPrint()),
        // requestHeaders(headerWithName("token").description("local user's
        // token")),
        // requestParameters(
        // parameterWithName("group_id")
        // .description("Integer: group identifier"),
        // parameterWithName("user_id")
        // .description("Integer: user identifier"),
        // parameterWithName("note")
        // .description("String: reason for deleting user")),
        // responseFields(fieldWithPath("result")
        // .description("message about sucess result"))));
    }

    @Test
    public void testDeleteUsersFromGroup() throws Exception {

        String url = "/rest/v1/group_users?groupId=1";

        mvc.perform(delete(url).accept(MediaType.APPLICATION_JSON).header("token", "jsession1")
                .param("groupId", "1").contentType(MediaType.APPLICATION_JSON)
                .content(usersForDeleteArrayNode.toString())).andExpect(status().isOk())
                .andExpect(jsonPath("result.status").value(200))
                .andDo(document("deleteUsersFromGroup", preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("token").description("local user's token")),
                        requestParameters(parameterWithName("groupId")
                                .description("Integer: group identifier")),
                        requestFields(
                                fieldWithPath("[].userId")
                                        .description("Integer: user's identifier"),
                                fieldWithPath("[].note")
                                        .description("reason for deleting user from group")),
                        responseFields(fieldWithPath("result")
                                .description("message about sucess result"))));
    }
}
