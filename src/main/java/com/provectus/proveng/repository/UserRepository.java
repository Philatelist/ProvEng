package com.provectus.proveng.repository;

import com.provectus.proveng.domain.User;

import java.util.List;

public interface UserRepository {

    void create(User user);

    void changeStatus(User user);

    void delete(User user);

    List<User> getAll();

    List<User> getByRole(String roleName);

    List getByLevel(String level);

    List getByPassedStartLevel();

    User getByEmail(String email);

    User getById(long id);

    void update(User user);

    User saveOrUpdate(User user);

    Long getUserIdByToken(String token);

    List<String> getRoleNamesForUser(Long id);

}
