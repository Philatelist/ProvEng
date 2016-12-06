package com.provectus.proveng.service;

import com.provectus.proveng.domain.Developer;

import java.util.List;

public interface DeveloperService {

    Developer create(Developer developer);

    Developer getById(long id);

    List<Developer> getByEmail(String email);

    Developer update(Developer developer);

    void delete(Developer developer);

    void turnOffDeveloper(String email);

    Developer refresh(Developer developer);

    List<String> getAllDevelopersEmail();

    boolean checkIfDeveloper(String email);

}