package com.provectus.proveng.controller;

import com.provectus.proveng.TestAbstractController;
import com.provectus.proveng.domain.Material;
import com.provectus.proveng.enumaration.MaterialType;
import com.provectus.proveng.service.MaterialService;
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
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestMaterialControllerDoc extends TestAbstractController {

    private Material material;

    @MockBean
    private MaterialService materialService;
    @MockBean
    private UserService userService;

    @Before
    public void setup() {
        super.setup();
        material = TestEntityUtils.createTestMaterial(1L, MaterialType.AUDIO);
    }

    @Test
    public void testGetMaterials() throws Exception {
        String url = "/rest/v1/materials";

        when(materialService.getMaterialsForUser(any(Long.class)))
                .thenReturn(TestEntityUtils.createTestMaterials());

        mvc.perform(get(url).accept(MediaType.APPLICATION_JSON).header("token", "jsession1"))
                .andExpect(status().isOk()).andExpect(jsonPath("result").isNotEmpty())
                .andDo(document("getMaterials", preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("token").description("local user's token")),
                        responseFields(fieldWithPath("result")
                                .description("list of materials for user in JSON"))));
    }

    @Test
    public void testGetMaterial() throws Exception {

        String url = "/rest/v1/material";

        when(materialService.getActiveById(any(Long.class))).thenReturn(this.material);

        mvc.perform(get(url).accept(MediaType.APPLICATION_JSON).header("token", "jsession1").param("id", "1"))
                .andExpect(status().isOk()).andExpect(jsonPath("result.name").value(material.getName()))
                .andDo(document("getMaterial", preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("token").description("local user's token")),
                        requestParameters(
                                parameterWithName("id").description("Integer: test's identifier")),
                        responseFields(
                                fieldWithPath("result").description("material entity in JSON"))));
    }

}
