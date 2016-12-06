package com.provectus.proveng.service;

import com.provectus.proveng.TestAbstract;
import com.provectus.proveng.domain.DayBook;
import com.provectus.proveng.domain.User;
import com.provectus.proveng.repository.DayBookRepository;
import com.provectus.proveng.utils.TestEntityUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@Transactional
public class TestDayBookService extends TestAbstract {

    private User user;
    private DayBook dayBook;

    @MockBean
    private DayBookRepository dayBookRepository;

    @Autowired
    private DayBookService dayBookService;

    @Before
    public void setup() {

        user = TestEntityUtils.createTestUser(1L);
        dayBook = TestEntityUtils.createTestDayBook(1L);

    }

    @Test
    public void testCreate() {

        when(dayBookRepository.create(any(DayBook.class))).thenReturn(this.dayBook);
        dayBookService.create(this.dayBook);
        verify(dayBookRepository, times(1)).create(any(DayBook.class));
    }
}
