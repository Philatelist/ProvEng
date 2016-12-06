package com.provectus.proveng;

import com.provectus.proveng.domain.User;
import com.provectus.proveng.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@SqlGroup(value = {
        @Sql("/database-scripts/schema.sql"),
        @Sql("/database-scripts/dataForTest.sql"),
})
public class UserAndDBTests extends Assert {

    @Autowired
    UserService userService;

    @Test
    public void InjectUserToDBTest() {
        User user = new User("nameForTest", "lastnameForTest", "emailForTest");
        userService.create(user);
        User newuser = userService.getByEmail("emailForTest");
        assertNotNull(newuser);
    }

    @Test
    public void FindUserByEmailDBTest() {
        User user = userService.getByEmail("email@gmail.com");
        assertNotNull(user);
        assertEquals("email@gmail.com", user.getEmail());
    }

    @Test
    public void FindUserByIdDBTest() {
        User user = userService.getById(1);
        assertNotNull(user);
        assertEquals("Maria", user.getFirstName());
    }

    @Test
    public void DeleteUserFromDBTest() {
        User user = userService.getById(1);
        userService.delete(user);
        User userResult = userService.getById(1);
        assertNull(userResult);
    }

    @Test
    public void UpdateUserToDBTest() {
        User user = userService.getById(2);
        user.setSkype("SKYPE_TEST");
        user.setPhone("PHONE_TEST");
        userService.update(user);
        assertEquals("PHONE_TEST", user.getPhone());
    }


}
