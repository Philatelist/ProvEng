package com.provectus.proveng.repository.impl;

import com.provectus.proveng.domain.TestAttempt;
import com.provectus.proveng.repository.TestAttemptRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Transactional
public class TestAttemptRepositoryImpl implements TestAttemptRepository {

    private static final Logger logger = LogManager.getLogger(TestAttemptRepository.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(TestAttempt testAttempt) {

        em.persist(testAttempt);

    }

    @Override
    public TestAttempt getById(long id) {

        TestAttempt testAttempt = em.find(TestAttempt.class, id);
        em.refresh(testAttempt);
        return testAttempt;

    }

    @Override
    public TestAttempt refresh(TestAttempt testAttempt) {

        em.refresh(testAttempt);

        return testAttempt;

    }
}
