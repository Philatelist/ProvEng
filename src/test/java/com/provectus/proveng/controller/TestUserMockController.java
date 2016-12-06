package com.provectus.proveng.controller;

import com.provectus.proveng.TestAbstractController;
import com.provectus.proveng.utils.JsonUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

public class TestUserMockController extends TestAbstractController {

    @Before
    public void setup() {
        super.setup();
    }

    @Test
    public void testLogout() throws Exception {

        String url = "/rest/mock/logout";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(url)
                .accept(MediaType.APPLICATION_JSON).header("token", "123")).andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        int status = mvcResult.getResponse().getStatus();

        Assert.assertEquals("errorTest: expected status OK", 200, status);
        Assert.assertTrue("errorTest: expected responce body", content.trim().length() > 0);

        @SuppressWarnings("unchecked")
        Map<String, Object> responseMap = JsonUtils.mapFromJson(content, HashMap.class);
        @SuppressWarnings("unchecked")
        Map<String, Object> resultMap = (Map<String, Object>) responseMap.get("result");

        System.out.println(">> responseMap = " + responseMap);
        System.out.println(">> resultMap = " + resultMap);

        Assert.assertNotNull("errorTest: not null result expected", resultMap);
        Assert.assertEquals("errorTest: expected message OK", "OK", resultMap.get("message"));

    }

}
