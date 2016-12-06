package com.provectus.proveng.repository;

import com.provectus.proveng.domain.DayBook;

import java.util.List;

public interface DayBookRepository {

    DayBook create(DayBook dayBook);

    void delete(DayBook dayBook);

    List<DayBook> getAll();

    List<DayBook> getAllUsersDaybook(long user_id);

    DayBook getByType(String type);

    DayBook getById(long id);

    void update(DayBook dayBook);

    DayBook refresh(DayBook dayBook);

    void turnOffDayBook(long id);

}