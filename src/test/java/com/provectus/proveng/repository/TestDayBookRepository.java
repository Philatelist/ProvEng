package com.provectus.proveng.repository;

import com.provectus.proveng.TestAbstract;
import com.provectus.proveng.domain.DayBook;
import com.provectus.proveng.domain.User;
import com.provectus.proveng.utils.TestEntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Transactional
public class TestDayBookRepository extends TestAbstract {

    @Autowired
    private DayBookRepository dayBookRepository;

    @Before
    public void setup() {
        //TODO delete cache
    }

    @After
    public void cleanAfter() {

    }

    @Test
    public void testCreate() {

        User user = TestEntityUtils.createTestUser(1L);
        DayBook dayBook = new DayBook(47, "Workshop", new Date(), null, user, null);
        dayBookRepository.create(dayBook);
        DayBook newDayBook = dayBookRepository.getByType("Workshop");
        assertNotNull(newDayBook);
        assertEquals("excpected user_id = 1", 1L, newDayBook.getUser().getId());

    }
}
