package com.provectus.proveng;

import com.provectus.proveng.domain.Password;
import com.provectus.proveng.service.PasswordService;
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
public class PasswordAndDBTests extends Assert {

    @Autowired
    PasswordService passwordService;

    @Test
    public void InjectPasswordToDBTest() {
        Password password = new Password("New password");
        passwordService.create(password);
        Password newpassword = passwordService.getByPassword("New password");
        assertNotNull(newpassword);
    }

    @Test
    public void FindPasswordByPassword() {
        Password password = passwordService.getByPassword("password111");
        assertNotNull(password);
        assertEquals("password111", password.getPassword());
    }

    @Test
    public void FindPasswordByIdDBTest() {
        Password password = passwordService.getById(3);
        assertNotNull(password);
        assertEquals(3, password.getId());
        assertEquals("password33333", password.getPassword());
    }

    @Test
    public void DeletePasswordFromDBTest() {
        Password password = passwordService.getById(1);
        passwordService.delete(password);
        Password passwordResult = passwordService.getById(1);
        assertNull(passwordResult);
    }

    @Test
    public void UpdatePasswordDBTest() {
        Password password = passwordService.getById(2);
        password.setPassword("NewPassword");
        password.setSysStatus(-1);
        passwordService.update(password);
        assertEquals("NewPassword", password.getPassword());
        assertEquals(-1, password.getSysStatus());
    }


}
