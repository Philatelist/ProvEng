package com.provectus.proveng.controller;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.provectus.proveng.TestAbstractController;
import com.provectus.proveng.domain.*;
import com.provectus.proveng.domain.dto.TestResultDto;
import com.provectus.proveng.enumaration.LevelType;
import com.provectus.proveng.service.DayBookService;
import com.provectus.proveng.service.RoleService;
import com.provectus.proveng.service.TestService;
import com.provectus.proveng.service.UserService;
import com.provectus.proveng.utils.TestEntityUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

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

public class TestTestControllerDoc extends TestAbstractController {

    private com.provectus.proveng.domain.Test test;
    private ArrayNode choices;
    private ArrayNode testCards;
    private DayBook dayBook;
    private User user;
    private Role role;
    private TestAttempt testAttempt;
    private ObjectNode testNode;

    @MockBean
    private TestService testService;
    @MockBean
    private UserService userService;
    @MockBean
    private DayBookService dayBookService;
    @MockBean
    private RoleService roleService;

    @Before
    public void setup() {
        super.setup();
        test = TestEntityUtils.createTestTest(1L);
        dayBook = TestEntityUtils.createTestDayBook(1L);
        user = TestEntityUtils.createTestUser(1L);
        role = TestEntityUtils.createTestRol(1L);
        testAttempt = TestEntityUtils.createTestAttempt(1L);

        choices = om.createArrayNode();
        ObjectNode obj1 = om.createObjectNode();
        ObjectNode obj2 = om.createObjectNode();
        ObjectNode obj3 = om.createObjectNode();
        obj1.put("id", 1);
        obj2.put("id", 2);
        obj3.put("id", 3);
        ObjectNode choice1 = om.createObjectNode();
        choice1.set("testCard", obj1);
        choice1.set("testAnswer", obj1);
        ObjectNode choice2 = om.createObjectNode();
        choice2.set("testCard", obj2);
        choice2.set("testAnswer", obj3);
        choices.add(choice1);
        choices.add(choice2);

        testNode = om.createObjectNode();
        testNode.put("name", test.getName());
        testNode.put("duration", test.getDuration());
        testNode.put("weight", test.getWeight());
        testNode.put("passMark", test.getPassMark());
        testNode.put("allowedAttempts", test.getAllowedAttempts());
        testNode.put("minLevel", test.getMinLevel());
        testNode.put("type", test.getType());

        ObjectNode testCard1 = om.createObjectNode();
        testCard1.put("name", test.getTestCards().get(0).getName());
        testCard1.put("question", test.getTestCards().get(0).getQuestion());
        ArrayNode testAnswers1 = om.createArrayNode();
        om.createObjectNode();
        om.createObjectNode();
        om.createObjectNode();
        om.createObjectNode();

        for (TestAnswer answ : test.getTestCards().get(0).getTestAnswers()) {
            ObjectNode answNode = om.createObjectNode();
            answNode.put("name", answ.getName());
            answNode.put("text", answ.getText());
            if (answ.getIsCorrect() != null && answ.getIsCorrect() == true)
                answNode.put("isCorrect", answ.getIsCorrect());
            testAnswers1.add(answNode);
        }
        testCard1.set("testAnswers", testAnswers1);
        testCards = om.createArrayNode();
        testCards.add(testCard1);

        testNode.set("testCards", testCards);

    }

    @Test
    public void testCreateTest() throws Exception {
        String url = "/rest/v1/test";

        when(testService.create(any(com.provectus.proveng.domain.Test.class)))
                .thenReturn(this.test);

        mvc.perform(post(url).accept(MediaType.APPLICATION_JSON).header("token", "jsession1")
                .contentType(MediaType.APPLICATION_JSON).content(testNode.toString()))
                .andExpect(status().isOk()).andExpect(jsonPath("result").isNotEmpty())
                .andDo(document("createTest", preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("token").description("local user's token")),
                        responseFields(fieldWithPath("result")
                                .description("name and id of created test"))));

    }

    @Test
    public void testUpdateTest() throws Exception {
        String url = "/rest/v1/test";

        when(testService.update(any(com.provectus.proveng.domain.Test.class)))
                .thenReturn(this.test);
        mvc.perform(put(url).accept(MediaType.APPLICATION_JSON).header("token", "jsession1")
                .contentType(MediaType.APPLICATION_JSON).content(testNode.put("id", 1).toString()))
                .andExpect(status().isOk()).andExpect(jsonPath("result").isNotEmpty())
                .andDo(document("updateTest", preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("token").description("local user's token")),
                        responseFields(
                                fieldWithPath("result").description("updated test entity"))));

    }

    @Test
    public void testGetTests() throws Exception {
        String url = "/rest/v1/tests";

        when(testService.getTestsForUser(any(User.class)))
                .thenReturn(TestEntityUtils.createTestTests());

        mvc.perform(get(url).accept(MediaType.APPLICATION_JSON).header("token", "jsession1"))
                .andExpect(status().isOk()).andExpect(jsonPath("result").isNotEmpty())
                .andDo(document("getTests", preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("token").description("local user's token")),
                        responseFields(fieldWithPath("result")
                                .description("list of tests for user in JSON"))));
    }

    @Test
    public void testGetTest() throws Exception {

        String url = "/rest/v1/test?id=1";

        when(testService.getActiveById(any(Long.class))).thenReturn(this.test);

        mvc.perform(get(url).accept(MediaType.APPLICATION_JSON).header("token", "jsession1"))
                .andExpect(status().isOk()).andExpect(jsonPath("result.name").value(test.getName()))
                .andDo(document("getTest", preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("token").description("local user's token")),
                        requestParameters(
                                parameterWithName("id").description("Integer: test's identifier")),
                        responseFields(
                                fieldWithPath("result").description("test entity in JSON"))));
    }

    @Test
    public void testCreateTestResult() throws Exception {
        String url = "/rest/v1/test_result?testId=4&duration=3600000";

        when(userService.getUserFromSessionByToken(any(String.class))).thenReturn(user);
        when(testService.makeTestResult(any(DayBook.class)))
                .thenReturn(new TestResultDto(true, 3, LevelType.INTERMEDIATE.getStringValue()));
        when(testService.checkTest(any(TestAttempt.class))).thenReturn(dayBook);
        when(roleService.getByName(any(String.class))).thenReturn(role);
        when(testService.createTestAttempt(any(Long.class), any(TestCardChoice[].class)))
                .thenReturn(testAttempt);

        mvc.perform(post(url).accept(MediaType.APPLICATION_JSON).header("token", "jsession1")

                .contentType(MediaType.APPLICATION_JSON).content(choices.toString()))
                .andExpect(status().isOk()).andExpect(jsonPath("result").isNotEmpty())
                .andDo(document("createTestResult", preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("token").description("local user's token")),
                        requestParameters(
                                parameterWithName("testId").description("test identifier"),
                                parameterWithName("duration")
                                        .description("duration of user's attempt in ms")),
                        requestFields(fieldWithPath("[]").description("test card choices")),
                        responseFields(
                                fieldWithPath("result").description("results for test in JSON"))));
    }

}
