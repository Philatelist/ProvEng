package com.provectus.proveng.repository;

import com.provectus.proveng.domain.Role;

import java.util.List;

public interface RoleRepository {

    void create(Role role);

    void delete(Role role);

    List getAll();

    Role getByName(String name);

    Role getById(long id);

    void update(Role role);
}
