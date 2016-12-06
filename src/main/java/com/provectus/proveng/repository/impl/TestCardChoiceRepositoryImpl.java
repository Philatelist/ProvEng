package com.provectus.proveng.repository.impl;

import com.provectus.proveng.domain.TestCardChoice;
import com.provectus.proveng.repository.TestCardChoiceRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Transactional
public class TestCardChoiceRepositoryImpl implements TestCardChoiceRepository {


    @PersistenceContext
    private EntityManager em;

    @Override
    public TestCardChoice getById(Long id) {
        // TODO Auto-generated method stub
        return em.find(TestCardChoice.class, id);
    }

}
