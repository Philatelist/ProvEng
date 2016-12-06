package com.provectus.proveng;

import com.provectus.proveng.domain.Relationship;
import com.provectus.proveng.service.RelationshipService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@SqlGroup(value = {
        @Sql("/database-scripts/schema.sql"),
        @Sql("/database-scripts/dataForTest.sql"),
})
public class RelationshipAndDBTests extends Assert {

    @Autowired
    RelationshipService relationshipService;

    @Test
    public void InjectRelationshipToDBTest() {
        Relationship relationship = new Relationship(1L, 2L, "Friend");
        relationshipService.create(relationship);
        Relationship newrelationship = relationshipService.getByType("Friend");
        assertNotNull(newrelationship);
    }

    @Test
    public void FindRelationshipByType() {
        Relationship relationship = relationshipService.getByType("Test");
        assertNotNull(relationship);
        assertEquals("Test", relationship.getType());
    }

    @Test
    public void FindAllRelationship() {
        List<Relationship> relationship = relationshipService.getAll();
        assertNotNull(relationship);
    }

    @Test
    public void FindRelationshipByIdDBTest() {
        Relationship relationship = relationshipService.getById(3);
        assertNotNull(relationship);
        assertEquals("Comate", relationship.getType());
    }

    @Test
    public void DeleteRelationshipFromDBTest() {
        Relationship relationship = relationshipService.getById(1);
        relationshipService.delete(relationship);
        Relationship relationshipResult = relationshipService.getById(1);
        assertNull(relationshipResult);
    }

    @Test
    public void UpdateRelationshipDBTest() {
        Relationship relationship = relationshipService.getById(2);
        relationship.setType("Comate");
        relationship.setSysStatus(-1);
        relationshipService.update(relationship);
        assertEquals("Comate", relationship.getType());
        assertEquals(-1, relationship.getSysStatus());
    }


}
