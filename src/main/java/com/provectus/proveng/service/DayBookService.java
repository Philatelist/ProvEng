package com.provectus.proveng.service;

import com.provectus.proveng.domain.DayBook;
import com.provectus.proveng.domain.User;

import java.util.List;

public interface DayBookService {

    DayBook create(DayBook dayBook);

    DayBook getById(long id);

    DayBook getByType(String type);

    DayBook update(DayBook dayBook);

    void delete(DayBook dayBook);

    DayBook refresh(DayBook dayBook);

    void deleteAllStartTestDaybooksForUser(User user);

    List<DayBook> getAllUsersDaybook(long id);

}