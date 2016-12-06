package com.provectus.proveng.repository;

import com.provectus.proveng.TestAbstract;
import com.provectus.proveng.domain.Group;
import com.provectus.proveng.domain.User;
import com.provectus.proveng.utils.TestEntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@Transactional
public class TestGroupRepository extends TestAbstract {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setup() {
        //TODO delete cache
    }

    @After
    public void cleanAfter() {

    }


    @Test
    public void testCreate() {

        Group group = TestEntityUtils.createTestGroup(null);

        groupRepository.create(group);

        assertNotNull(group.getId());

    }

    @Test
    public void addUserToGroup() {
        Group group = groupRepository.getById(1L);
        assertTrue("group should exist", group != null);
        User user = userRepository.getById(10L);
        assertTrue("user should exist", user != null);

        assertFalse("group should not contain user", group.getMembers().contains(user));
        groupRepository.addUserToGroup(group.getId(), user.getId());
        assertTrue("group should contain user", group.getMembers().contains(user));
    }
}
