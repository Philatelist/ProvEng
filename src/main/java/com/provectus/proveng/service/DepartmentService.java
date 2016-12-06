package com.provectus.proveng.service;

import com.provectus.proveng.domain.Department;

import java.util.List;

public interface DepartmentService {

    Department create(Department department);

    Department getById(long id);

    Department getByName(String name);

    List<Department> getAll();

    Department update(Department department);

    void delete(Department department);
}
