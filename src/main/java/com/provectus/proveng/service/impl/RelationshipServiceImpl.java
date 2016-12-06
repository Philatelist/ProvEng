package com.provectus.proveng.service.impl;

import com.provectus.proveng.domain.Relationship;
import com.provectus.proveng.repository.RelationshipRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("relationshipService")
public class RelationshipServiceImpl implements com.provectus.proveng.service.RelationshipService {

    private static Logger logger = LogManager.getLogger(RelationshipServiceImpl.class);
    @Autowired
    private RelationshipRepository relationshipRepository;

    @Override
    public void create(Relationship relationship) {

        relationshipRepository.create(relationship);
    }

    @Override
    public Relationship getById(long id) {

        Relationship relationship = relationshipRepository.getById(id);
        return relationship;
    }

    @Override
    public List<Relationship> getAll() {

        List relationship = relationshipRepository.getAll();
        return relationship;
    }

    @Override
    public Relationship getByType(String type) {

        Relationship relationship = relationshipRepository.getByType(type);
        return relationship;
    }

    @Override
    public Relationship update(Relationship relationship) {
        relationshipRepository.update(relationship);
        return relationship;
    }

    @Override
    public void delete(Relationship relationship) {

        relationshipRepository.delete(relationship);
    }
}
