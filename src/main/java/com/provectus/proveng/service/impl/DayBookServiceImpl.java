package com.provectus.proveng.service.impl;

import com.provectus.proveng.domain.DayBook;
import com.provectus.proveng.domain.User;
import com.provectus.proveng.enumaration.DaybookType;
import com.provectus.proveng.repository.DayBookRepository;
import com.provectus.proveng.service.DayBookService;
import com.provectus.proveng.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("dayBookService")
@Transactional
public class DayBookServiceImpl implements DayBookService {

    private static Logger logger = LogManager.getLogger(DayBookServiceImpl.class);

    static {
        logger.info(">>> DayBookService loaded");
    }

    @Autowired
    private DayBookRepository dayBookRepository;
    @Autowired
    private UserService userService;

    @Override
    public DayBook create(DayBook dayBook) {

        dayBookRepository.create(dayBook);

        return dayBook;
    }

    @Override
    public DayBook getById(long id) {

        DayBook dayBook = dayBookRepository.getById(id);
        return dayBook;
    }

    @Override
    public DayBook getByType(String type) {

        DayBook dayBook = dayBookRepository.getByType(type);
        return dayBook;
    }

    @Override
    public DayBook update(DayBook dayBook) {
        dayBookRepository.update(dayBook);
        return dayBook;
    }

    @Override
    public void delete(DayBook dayBook) {

        dayBookRepository.delete(dayBook);
    }

    @Override
    public DayBook refresh(DayBook dayBook) {

        return dayBookRepository.refresh(dayBook);
    }

    @Override
    public void deleteAllStartTestDaybooksForUser(User user) {
        user = userService.getById(user.getId());
        for (DayBook mark : user.getDayBooks()) {
            if (DaybookType.START_TEST.getDaybookType().equals(mark.getType())) {
                dayBookRepository.turnOffDayBook(mark.getId());
            }
        }
    }

    @Override
    public List<DayBook> getAllUsersDaybook(long user_id) {
        return dayBookRepository.getAllUsersDaybook(user_id);
    }
}
