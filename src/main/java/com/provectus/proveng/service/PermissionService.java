package com.provectus.proveng.service;

import com.provectus.proveng.domain.Permission;

public interface PermissionService {

    void create(Permission permission);

    Permission getById(long id);

    Permission getByName(String name);

    Permission getByNameAndFlagAndRole(String name, String flag, int role_id);

    Permission update(Permission permission);

    void delete(Permission permission);
}
