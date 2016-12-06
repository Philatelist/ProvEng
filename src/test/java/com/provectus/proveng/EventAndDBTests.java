package com.provectus.proveng;

import com.provectus.proveng.domain.Event;
import com.provectus.proveng.domain.User;
import com.provectus.proveng.service.EventService;
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
public class EventAndDBTests extends Assert {

    @Autowired
    EventService eventService;

    @Test
    public void InjectEventToDBTest() {
        User user0 = new User(1L, "Alla", "Efremova", "teacher@provectus-it.com");
        Event event = new Event(user0, "nameForTest", "Lesson");
        eventService.create(event);
        Event newevent = eventService.getByName("nameForTest");
        assertNotNull(newevent);
    }

    @Test
    public void FindEventByName() {
        Event event = eventService.getByName("Introductory lesson.");
        assertNotNull(event);
        assertEquals("Lesson", event.getType());
    }

    @Test
    public void FindEventByIdDBTest() {
        Event event = eventService.getById(4);
        assertNotNull(event);
        assertEquals("Monday Lesson", event.getName());
        assertEquals("Weekly", event.getRegular());
    }

    @Test
    public void DeleteEventFromDBTest() {
        Event event = eventService.getById(1);
        event.setSysStatus(-1);
        eventService.delete(event);
        Event eventResult = eventService.getById(1);
        assertEquals(-1, eventResult.getSysStatus());
    }

    @Test
    public void UpdateEventDBTest() {
        User user0 = new User(2L, "Alla", "Efremova", "teacher@provectus-it.com");
        Event event = eventService.getById(6);
        event.setName("newNameForTest");
        event.setSysStatus(-1);
        event.setLeader(user0);
        eventService.update(event);
        assertEquals("newNameForTest", event.getName());
        assertEquals(-1, event.getSysStatus());
        assertEquals(2L, event.getLeader().getId());
    }


}
