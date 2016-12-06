package com.provectus.proveng.repository;

import com.provectus.proveng.domain.Developer;

import java.util.List;

public interface DeveloperRepository {

    Developer create(Developer developer);

    Developer getById(long id);

    List getByEmail(String email);

    void update(Developer developer);

    void delete(Developer developer);

    void turnOffDeveloper(String email);

    Developer refresh(Developer developer);

    List<Developer> getAllDevelopers();

    List<Developer> checkIfDeveloper(String email);

}