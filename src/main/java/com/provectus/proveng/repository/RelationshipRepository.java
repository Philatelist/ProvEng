package com.provectus.proveng.repository;

import com.provectus.proveng.domain.Relationship;

import java.util.List;

public interface RelationshipRepository {

    void create(Relationship relationship);

    void delete(Relationship relationship);

    List getAll();

    Relationship getByType(String type);

    Relationship getById(long id);

    void update(Relationship relationship);
}
