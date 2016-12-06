package com.provectus.proveng.service;

import com.provectus.proveng.domain.*;
import com.provectus.proveng.domain.dto.TestResultDto;
import com.provectus.proveng.exception.TestServiceException;

import java.util.List;

public interface TestService {

    Test create(Test test);

    void createVariants(Test test, int numberOfVariants);

    Test getTestVariant(Long parentTestId);

    void checkIfExist(Long testId) throws TestServiceException;

    Test getById(Long id) throws TestServiceException;

    Test getActiveById(Long id) throws TestServiceException;

    void turnOffById(Long id);

    Test update(Test test);

    TestAttempt createTestAttempt(Long testId, TestCardChoice[] choices) throws TestServiceException;

    DayBook checkTest(TestAttempt testAttempt);

    Test getActiveByType(String type) throws TestServiceException;

    String determineLevel(DayBook daybook);

    List<Test> getTestsForUser(User user);

    TestResultDto makeTestResult(DayBook daybook);

}
