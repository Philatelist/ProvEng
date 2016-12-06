package com.provectus.proveng.service.impl;

import com.provectus.proveng.domain.Role;
import com.provectus.proveng.repository.RoleRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("roleService")
public class RoleServiceImpl implements com.provectus.proveng.service.RoleService {

    private static Logger logger = LogManager.getLogger(RoleServiceImpl.class);
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void create(Role role) {

        roleRepository.create(role);
    }

    @Override
    public Role getById(long id) {

        Role role = roleRepository.getById(id);
        return role;
    }

    @Override
    public Role getByName(String name) {

        Role role = roleRepository.getByName(name);
        return role;
    }

    @Override
    public Role updateStatus(Role role) {
        roleRepository.update(role);
        return role;
    }

    @Override
    public Role update(Role role) {
        roleRepository.update(role);
        return role;
    }

    @Override
    public void delete(Role role) {

        roleRepository.delete(role);
    }
}
