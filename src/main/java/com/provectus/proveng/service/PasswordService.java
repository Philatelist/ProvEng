package com.provectus.proveng.service;

import com.provectus.proveng.domain.Password;

public interface PasswordService {

    void create(Password password);

    Password getById(long id);

    Password getByPassword(String password);

    Password update(Password password);

    void delete(Password password);
}
