package com.provectus.proveng.service.impl;

import com.provectus.proveng.domain.*;
import com.provectus.proveng.domain.dto.TestResultDto;
import com.provectus.proveng.enumaration.LevelType;
import com.provectus.proveng.enumaration.RoleName;
import com.provectus.proveng.enumaration.TestType;
import com.provectus.proveng.exception.TestServiceException;
import com.provectus.proveng.repository.TestAttemptRepository;
import com.provectus.proveng.repository.TestRepository;
import com.provectus.proveng.service.TestService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Service("testService")
@Transactional
public class TestServiceImpl implements TestService {

    private static final Logger logger = LogManager.getLogger(TestService.class);

    @Value("${level.advanced.score}")
    public double ADVANCED_SCORE;
    @Value("${level.upper_intermediate.score}")
    public double UPPER_INTERMEDIATE_SCORE;
    @Value("${level.intermediate.score}")
    public double INTERMEDIATE_SCORE;
    @Value("${level.pre_intermediate.score}")
    public double PRE_INTERMEDIATE_SCORE;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private TestAttemptRepository testAttemptRepository;

    @Override
    public Test create(Test test) {
        logger.debug(">>> create()");
        testRepository.create(test);
        // this.createVariants(test, 2);
        return test;
    }

    @Override
    public void createVariants(Test test, int numberOfVariants) {
        for (int i = 1; i <= numberOfVariants; i++) {
            Test newTest = new Test();
            newTest.setName(test.getName());
            newTest.setVersion(i);
            newTest.setDuration(test.getDuration());
            newTest.setWeight(test.getWeight());
            newTest.setPassMark(test.getPassMark());
            newTest.setAllowedAttempts(test.getAllowedAttempts());
            newTest.setMinLevel(test.getMinLevel());
            newTest.setParent(test.getParent());
            List<TestCard> newTestCards = new ArrayList<>();
            newTest.setTestCards(newTestCards);
            for (TestCard tc : test.getTestCards()) {
                TestCard newTestCard = new TestCard();
                newTestCard.setName(tc.getName());
                newTestCard.setQuestion(tc.getQuestion());

                Collection<TestAnswer> newTestAnswers = new HashSet<>();
                newTestCard.setTestAnswers(newTestAnswers);
                for (TestAnswer ta : tc.getTestAnswers()) {
                    // TODO add answers according to some algorithm
                    TestAnswer newTestAnswer = new TestAnswer();
                    newTestAnswer.setName(ta.getName());
                    newTestAnswer.setText(ta.getText());
                    newTestAnswer.setIsCorrect(ta.getIsCorrect());
                    newTestAnswers.add(newTestAnswer);

                }

                newTestCards.add(newTestCard);
            }
            newTest.setParent(test);
            testRepository.create(newTest);
        }

    }

    @Override
    public Test getTestVariant(Long parentTestId) {
        // TODO implement some algorithm to choose variant
        Test test = null;
        int version = 1;

        test = this.getTestVariant(parentTestId, version);

        return test;
    }

    private Test getTestVariant(Long parentTestId, int version) {

        return testRepository.getTestByParentIdAndVersion(parentTestId, version);
    }

    @Override
    public void checkIfExist(Long testId) throws TestServiceException {

        if (this.getById(testId) == null) {
            throw new TestServiceException("test with this id doesn't exist");
        }

    }

    public Test getById(Long id) throws TestServiceException {
        Test test = testRepository.getById(id);
        if (test == null) {
            throw new TestServiceException("test with this id doesn't exist");
        }
        return test;
    }

    @Override
    public Test getActiveById(Long id) throws TestServiceException {
        Test test = testRepository.getActiveById(id);
        if (test == null) {
            throw new TestServiceException("no active test with this id");
        }
        return test;
    }

    @Override
    public void turnOffById(Long id) {
        testRepository.deleteById(id);

    }

    @Override
    public Test update(Test test) {

        return testRepository.update(test);
    }

    @Override
    public TestAttempt createTestAttempt(Long testId, TestCardChoice[] choices)
            throws TestServiceException {

        Test test = testRepository.getActiveById(testId);

        if (test == null) {
            throw new TestServiceException("test with this id doesn't exist");
        }
        TestAttempt testAttempt = new TestAttempt();
        testAttempt.setTest(test);

        for (TestCardChoice choice : choices) {
            testAttempt.getTestCardChoices().add(choice);
        }

        testAttemptRepository.create(testAttempt);

        return testAttempt;

    }

    @Override
    public DayBook checkTest(TestAttempt testAttempt) {

        testAttempt = testAttemptRepository.refresh(testAttempt);

        int mark = 0;
        DayBook daybook = new DayBook();
        for (TestCardChoice choice : testAttempt.getTestCardChoices()) {
            if (choice.getTestAnswer().isCorrect()) {
                mark++;
            }
        }

        if (mark >= testAttempt.getTest().getPassMark()) {
            testAttempt.setIsPassed(true);
        } else {
            testAttempt.setIsPassed(false);
        }

        daybook.setMark(mark);
        daybook.setTestAttempt(testAttempt);
        return daybook;
    }

    @Override
    public Test getActiveByType(String type) throws TestServiceException {

        List<Test> testList = null;
        Test test = null;
        try {
            testList = testRepository.getAllActiveByType(type);
        } catch (NoResultException e) {
            throw (new TestServiceException("there is no any start test"));
        }
        if (testList != null) {
            // TODO: implement some algorithm
            test = testList.get(0);
        }
        return test;
    }

    @Override
    public String determineLevel(DayBook daybook) {
        // TODO implement some wise algorithm for Level
        int points = daybook.getMark();
        int totalPoints = daybook.getTestAttempt().getTest().getWeight();
        double score = (double) points / totalPoints;
        String level = null;
        if (score >= UPPER_INTERMEDIATE_SCORE) {
            level = LevelType.UPPER_INTERMEDIATE.getStringValue();
        } else if (score >= INTERMEDIATE_SCORE) {
            level = LevelType.INTERMEDIATE.getStringValue();
        } else if (score >= PRE_INTERMEDIATE_SCORE) {
            level = LevelType.PRE_INTERMEDIATE.getStringValue();
        } else
            level = LevelType.ELEMENTARY.getStringValue();

        return level;
    }

    @Override
    public List<Test> getTestsForUser(User user) {
        // TODO implements search for non-guest user by event
        List<Test> tests = new ArrayList<>();

        if (user.hasRole(RoleName.GUEST)) {
            tests.addAll(this.getStartTests());
        } else if (user.hasRole(RoleName.STUDENT)) {
            tests.addAll(testRepository.getAllActiveByType(TestType.REGULAR));
        } else if (user.hasRole(RoleName.ADMIN) || user.hasRole(RoleName.TEACHER)) {
            tests.addAll(testRepository.getAllActive());
        }
        return tests;
    }

    public List<Test> getStartTests() {
        List<Test> tests = new ArrayList<>();
        tests = testRepository.getStartTests();
        return tests;
    }

    @Override
    public TestResultDto makeTestResult(DayBook daybook) {
        TestResultDto result = new TestResultDto();
        result.setIsPassed(daybook.getTestAttempt().getIsPassed());
        result.setLevel(this.determineLevel(daybook));
        result.setMark(daybook.getMark());
        return result;
    }

}
