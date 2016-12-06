package com.provectus.proveng.service;

import com.provectus.proveng.TestAbstract;
import com.provectus.proveng.repository.GroupRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Transactional
public class TestGroupService extends TestAbstract {

    @MockBean
    private GroupRepository groupRepository;

    @Autowired
    private GroupService groupService;

    @Test
    public void testAddUserToGroup() {

        groupService.addUserToGroup(1L, 2L);
        verify(groupRepository, times(1)).addUserToGroup(1L, 2L);
    }

}
