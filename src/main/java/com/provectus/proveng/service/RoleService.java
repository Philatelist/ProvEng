package com.provectus.proveng.service;

import com.provectus.proveng.domain.Role;

public interface RoleService {

    void create(Role role);

    Role getById(long id);

    Role getByName(String name);

    Role updateStatus(Role role);

    Role update(Role role);

    void delete(Role role);
}
