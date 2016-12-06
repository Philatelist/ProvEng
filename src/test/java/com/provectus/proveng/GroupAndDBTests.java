package com.provectus.proveng;

import com.provectus.proveng.domain.Group;
import com.provectus.proveng.domain.User;
import com.provectus.proveng.exception.ErrorResponseException;
import com.provectus.proveng.service.GroupService;
import com.provectus.proveng.utils.TestEntityUtils;
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
public class GroupAndDBTests extends Assert {

    @Autowired
    GroupService groupService;

    //TODO commented due to change in model Groups

    @Test
    public void InjectGroupToDBTest() {
        User leader = TestEntityUtils.createTestUser(3L);
        Group group = new Group(leader, "nameForTest");
        group.setLevel("Intermediate");
        groupService.create(group);
        Group newgroup = groupService.getByName("nameForTest");
        assertNotNull(newgroup);
    }

    @Test
    public void FindGroupByName() {
        Group group = groupService.getByName("Middle-2");
        assertNotNull(group);
        assertEquals("Intermediate", group.getLevel());
    }

    @Test
    public void FindGroupByLeader_idAndLevel() {
        Group group = groupService.getByLeader_idAndLevel(2, "Intermediate");
        assertNotNull(group);
        assertEquals("Middle-2", group.getName());
    }

    @Test
    public void FindGroupByIdDBTest() {
        Group group = groupService.getById(4);
        assertNotNull(group);
        assertEquals("Intermediate", group.getLevel());
        assertEquals("Middle-2", group.getName());
    }

    @Test(expected = ErrorResponseException.class)
    public void DeleteGroupFromDBTest() {
        Group group = groupService.getById(1);
        groupService.delete(group);
        Group groupResult = groupService.getById(1);
        assertNull(groupResult);
    }

    @Test
    public void UpdateGroupDBTest() {
        Group group = groupService.getById(2);
        group.setName("newNameForTest");
        group.setSysStatus(-1);

        groupService.update(group);

        assertEquals("newNameForTest", group.getName());
        assertEquals(-1, group.getSysStatus());
    }


}
