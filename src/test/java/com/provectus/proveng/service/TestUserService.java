package com.provectus.proveng.service;

import com.provectus.proveng.TestAbstract;
import com.provectus.proveng.domain.User;
import com.provectus.proveng.utils.TestEntityUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@TestPropertySource(locations = "test.properties")
@Transactional
public class TestUserService extends TestAbstract {


    @Autowired
    private UserService userService;

    @Before
    public void setup() {
        //TODO delete cache
    }

    @After
    public void cleanAfter() {

    }

    @Test
    public void testCreateTestUser() {

        User user = TestEntityUtils.createTestUser(1L);
        Assert.assertNotNull("errorTest: expected not null", user);
        Assert.assertEquals("errorTest: expected id = 1", 1L, user.getId());
    }

    @Test
    public void testValidateEmailDomain() {

        String emailWrong = "user@provectus.com";
        String emailRight1 = "user@gmail.com";
        String emailRight2 = "user@domain.com";
        Assert.assertTrue("error: expected email = " + emailRight1 + " to be valid",
                userService.isValidEmailDomain(emailRight1));
        Assert.assertTrue("error: expected email = " + emailRight2 + " to be valid",
                userService.isValidEmailDomain(emailRight2));
        Assert.assertFalse("error: expected email = " + emailWrong + " to be invalid",
                userService.isValidEmailDomain(emailWrong));

    }
}
