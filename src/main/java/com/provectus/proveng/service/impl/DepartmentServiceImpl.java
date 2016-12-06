package com.provectus.proveng.service.impl;

import com.provectus.proveng.domain.Department;
import com.provectus.proveng.repository.DepartmentRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements com.provectus.proveng.service.DepartmentService {

    private static Logger logger = LogManager.getLogger(DepartmentServiceImpl.class);

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public Department create(Department department) {
        logger.debug(">> create department");
        return departmentRepository.create(department);
    }

    @Override
    public Department getById(long id) {
        Department department = departmentRepository.getById(id);
        return department;
    }

    @Override
    public Department getByName(String name) {
        Department department = departmentRepository.getByName(name);
        return department;
    }

    @Override
    public List<Department> getAll() {
        List department = departmentRepository.getAll();
        return department;
    }

    @Override
    public Department update(Department department) {
        departmentRepository.update(department);
        return department;
    }

    @Override
    public void delete(Department department) {
        departmentRepository.delete(department);
    }
}