package com.provectus.proveng;

import com.provectus.proveng.domain.Permission;
import com.provectus.proveng.service.PermissionService;
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
public class PermissionAndDBTests extends Assert {

    @Autowired
    PermissionService permissionService;

    @Test
    public void InjectPermissionToDBTest() {
        Permission permission = new Permission("nameForTest", "R", "User");
        permissionService.create(permission);
        Permission newpermission = permissionService.getByName("nameForTest");
        assertNotNull(newpermission);
    }

    @Test
    public void FindPermissionByNameAndFlagAndRoleDBTest() {
        Permission permission = permissionService.getByNameAndFlagAndRole("ProfileRead", "R", 1);
        assertNotNull(permission);
        assertEquals("ProfileRead", permission.getName());
    }

    @Test
    public void FindPermissionByIdDBTest() {
        Permission permission = permissionService.getById(2);
        assertNotNull(permission);
        assertEquals("ProfileRead", permission.getName());
        assertEquals("R", permission.getAccessFlag());
    }

    @Test
    public void DeletePermissionFromDBTest() {
        Permission permission = permissionService.getById(1);
        permissionService.delete(permission);
        Permission permissionResult = permissionService.getById(1);
        assertNull(permissionResult);
    }

    @Test
    public void UpdatePermissionDBTest() {
        Permission permission = permissionService.getById(2);
        permission.setName("newNameForTest");
        permission.setSysStatus(-1);
        permissionService.update(permission);
        assertEquals("newNameForTest", permission.getName());
        assertEquals(-1, permission.getSysStatus());
    }


}
