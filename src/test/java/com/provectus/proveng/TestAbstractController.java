package com.provectus.proveng;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@WebAppConfiguration
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
@AutoConfigureMockMvc

public abstract class TestAbstractController extends TestAbstract {

    // @Rule
    // public final RestDocumentation restDoc = new
    // RestDocumentation("target/generated-snippets");
    @Autowired
    protected MockMvc mvc;
    @Autowired
    protected WebApplicationContext webApplicationContext;
    @Autowired
    protected ObjectMapper om;

    @Before
    protected void setup() {
        // mvc =
        // MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

}
