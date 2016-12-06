package com.provectus.proveng.repository.impl;

import com.provectus.proveng.domain.Test;
import com.provectus.proveng.enumaration.LevelType;
import com.provectus.proveng.enumaration.SysStatus;
import com.provectus.proveng.repository.TestRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class TestRepositoryImpl implements TestRepository {

    private static final Logger logger = LogManager.getLogger(TestRepository.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Test test) {
        logger.debug(">>> create");
        em.persist(test);
    }

    @Override
    public Test getTestByParentIdAndVersion(Long parentTestId, int version) {
        return (Test) em.createQuery("from Test where parent.id = :parentId AND version = :version")
                .setParameter("parentId", parentTestId).setParameter("version", version)
                .getSingleResult();
    }

    @Override
    public Test getById(Long testId) {

        return em.find(Test.class, testId);
    }

    @Override
    public Test getActiveById(Long id) {
        Test test = null;
        try {
            test = (Test) em.createQuery("from Test where id = :id and sysStatus = :status")
                    .setParameter("id", id).setParameter("status", SysStatus.ON.getNumber())
                    .getSingleResult();
        } catch (NoResultException e) {

        }
        return test;
    }

    @Override
    public void deleteById(Long id) {

        Test test = em.find(Test.class, id);
        test.setSysStatus(SysStatus.OFF.getNumber());

    }

    @Override
    public Test update(Test test) {
        em.merge(test);
        return test;
    }

    @Override
    public List<Test> getAllActiveByType(String type) {
        @SuppressWarnings("unchecked")
        List<Test> tests = (List<Test>) em.createQuery("from Test where type = :type and sysStatus = :status")
                .setParameter("type", type)
                .setParameter("status", SysStatus.ON.getNumber()).getResultList();
        return tests;
    }

    @Override
    public List<Test> getStartTests() {
        @SuppressWarnings("unchecked")
        List<Test> tests = (List<Test>) em.createQuery("from Test where minLevel = :minLevel and sysStatus = :status")
                .setParameter("minLevel", LevelType.UNKNOWN.getNumberValue())
                .setParameter("status", SysStatus.ON.getNumber()).getResultList();
        return tests;
    }

    @Override
    public List<Test> getAllActive() {
        @SuppressWarnings("unchecked")
        List<Test> tests = (List<Test>) em.createQuery("from Test where sysStatus = :status")
                .setParameter("status", SysStatus.ON.getNumber()).getResultList();
        return tests;
    }

}
