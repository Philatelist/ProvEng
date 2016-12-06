package com.provectus.proveng;

import com.provectus.proveng.domain.DayBook;
import com.provectus.proveng.service.DayBookService;
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
public class DayBookAndDBTests extends Assert {

    @Autowired
    DayBookService dayBookService;

    @Test
    public void InjectDayBookToDBTest() {
//        DayBook dayBook = new DayBook(1, 6, 77, "Workshop", new Date());
//        dayBookService.create(dayBook);
//        DayBook newdayBook = dayBookService.getByType("Workshop");
//        assertNotNull(newdayBook);
    }

    @Test
    public void FindDayBookByType() {
        DayBook dayBook = dayBookService.getByType("Lesson");
        assertNotNull(dayBook);
        assertEquals("Lesson", dayBook.getType());
    }

    @Test
    public void FindDayBookByIdDBTest() {
        DayBook dayBook = dayBookService.getById(3);
        assertNotNull(dayBook);
        assertEquals(55, dayBook.getMark());
        assertEquals("Presentation", dayBook.getType());
    }

    @Test
    public void DeleteDayBookFromDBTest() {
        DayBook dayBook = dayBookService.getById(1);
        dayBookService.delete(dayBook);
        DayBook dayBookResult = dayBookService.getById(1);
        assertNull(dayBookResult);
    }

    @Test
    public void UpdateDayBookDBTest() {
        DayBook dayBook = dayBookService.getById(2);
        dayBook.setType("Lesson");
        dayBook.setSysStatus(-1);
        dayBook.setMark(76);
        dayBookService.update(dayBook);
        assertEquals("Lesson", dayBook.getType());
        assertEquals(-1, dayBook.getSysStatus());
        assertEquals(76, dayBook.getMark());
    }


}
