package com.provectus.proveng.service;

import com.provectus.proveng.domain.Relationship;

import java.util.List;

public interface RelationshipService {

    void create(Relationship relationship);

    Relationship getById(long id);

    List<Relationship> getAll();

    Relationship getByType(String type);

    Relationship update(Relationship relationship);

    void delete(Relationship relationship);
}
