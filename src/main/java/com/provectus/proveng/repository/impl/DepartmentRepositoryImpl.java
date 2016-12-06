package com.provectus.proveng.repository.impl;

import com.provectus.proveng.controller.UserMockController;
import com.provectus.proveng.domain.Department;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Repository
@Transactional
public class DepartmentRepositoryImpl implements com.provectus.proveng.repository.DepartmentRepository {
    private static Logger logger = LogManager.getLogger(UserMockController.class);

    static {
        logger.info(">>> Loaded DepartmentRepository.class");
    }

    // An EntityManager will be automatically injected from entityManagerFactory
    // setup on DatabaseConfig class.
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Department create(Department department) {
        // TODO Auto-generated method stub
        logger.debug(">> saving department = : " + department);
        entityManager.persist(department);
        return department;
    }

    //    ЗАМЕНА СТАТУТСА!!! Но не удалять. Удалить связи.
    @Override
    public void delete(Department department) {
        Serializable id = new Long(department.getId());
        Object persistentInstance = entityManager.find(Department.class, id);
        if (persistentInstance != null) {
            entityManager.remove(persistentInstance);
        }
    }

    /**
     * Return all the department stored in the database.
     */
    @Override
    @SuppressWarnings("unchecked")
    public List getAll() {
        return entityManager.createQuery("from Department").getResultList();
    }

    /**
     * Return the department having the passed name.
     */
    @Override
    public Department getByName(String name) {
        return (Department) entityManager.createQuery(
                "from Department where name = :name AND sys_status = 0")
                .setParameter("name", name)
                .getSingleResult();
    }

    /**
     * Return the department having the passed id.
     */
    @Override
    public Department getById(long id) {
        return entityManager.find(Department.class, id);
    }

    /**
     * Update the passed department in the database.
     */
    @Override
    public void update(Department department) {
        entityManager.merge(department);
    }

}
