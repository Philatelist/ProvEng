package com.provectus.proveng.repository;

import com.provectus.proveng.domain.Password;

import java.util.List;

public interface PasswordRepository {

    void create(Password password);

    void delete(Password password);

    List getAll();

    Password getById(long id);

    Password getByPassword(String password);

    void update(Password password);
}
