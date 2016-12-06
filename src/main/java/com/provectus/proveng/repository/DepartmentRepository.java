package com.provectus.proveng.repository;

import com.provectus.proveng.domain.Department;

import java.util.List;

public interface DepartmentRepository {

    Department create(Department department);

    void delete(Department department);

    List getAll();

    Department getByName(String name);

    Department getById(long id);

    void update(Department department);
}
