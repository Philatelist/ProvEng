package com.provectus.proveng.service.impl;

import com.provectus.proveng.domain.Permission;
import com.provectus.proveng.repository.PermissionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("permissionService")
public class PermissionServiceImpl implements com.provectus.proveng.service.PermissionService {

    private static Logger logger = LogManager.getLogger(PermissionServiceImpl.class);
    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public void create(Permission permission) {

        permissionRepository.create(permission);
    }

    @Override
    public Permission getById(long id) {

        Permission permission = permissionRepository.getById(id);
        return permission;
    }

    @Override
    public Permission getByName(String name) {

        Permission permission = permissionRepository.getByName(name);
        return permission;
    }

    @Override
    public Permission getByNameAndFlagAndRole(String name, String flag, int role_id) {

        Permission permission = permissionRepository.getByNameAndFlagAndRole(name, flag, role_id);
        return permission;
    }

    @Override
    public Permission update(Permission permission) {
        permissionRepository.update(permission);
        return permission;
    }

    @Override
    public void delete(Permission permission) {

        permissionRepository.delete(permission);
    }
}
