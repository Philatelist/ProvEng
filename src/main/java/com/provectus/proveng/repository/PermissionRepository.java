package com.provectus.proveng.repository;

import com.provectus.proveng.domain.Permission;

import java.util.List;

public interface PermissionRepository {

    void create(Permission permission);

    void delete(Permission permission);

    List getAll();

    Permission getByName(String name);

    Permission getByNameAndFlagAndRole(String name, String flag, int roleId);

    Permission getById(long id);

    void update(Permission permission);
}
