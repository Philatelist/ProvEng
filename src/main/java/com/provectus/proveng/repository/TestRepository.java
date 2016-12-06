package com.provectus.proveng.repository;

import com.provectus.proveng.domain.Test;

import java.util.List;

public interface TestRepository {

    void create(Test test);

    Test getTestByParentIdAndVersion(Long parentTestId, int version);

    Test getById(Long testId);

    Test getActiveById(Long id);

    void deleteById(Long id);

    Test update(Test test);

    List<Test> getAllActiveByType(String type);

    List<Test> getStartTests();

    List<Test> getAllActive();

}
