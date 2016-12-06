package com.provectus.proveng;

import com.provectus.proveng.domain.Role;
import com.provectus.proveng.service.RoleService;
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
public class RoleAndDBTests extends Assert {

    @Autowired
    RoleService roleService;

    @Test
    public void InjectRoleToDBTest() {
        Role role = new Role("nameForTest");
        roleService.create(role);
        Role newrole = roleService.getByName("nameForTest");
        assertNotNull(newrole);
    }

    @Test
    public void FindRoleByNameDBTest() {
        Role role = roleService.getByName("Teacher");
        assertNotNull(role);
        assertEquals("Teacher", role.getName());
    }

    @Test
    public void FindRoleByIdDBTest() {
        Role role = roleService.getById(4);
        assertNotNull(role);
        assertEquals("Guest", role.getName());
    }

    @Test
    public void DeleteRoleFromDBTest() {
        Role role = roleService.getById(1);
        roleService.delete(role);
        Role roleResult = roleService.getById(1);
        assertNull(roleResult);
    }

    @Test
    public void UpdateRoleDBTest() {
        Role role = roleService.getById(2);
        role.setName("newNameForTest");
        role.setSysStatus(-1);
        roleService.update(role);
        assertEquals("newNameForTest", role.getName());
        assertEquals(-1, role.getSysStatus());
    }


}
