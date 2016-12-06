package com.provectus.proveng.service.impl;

import com.provectus.proveng.domain.Password;
import com.provectus.proveng.repository.PasswordRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("passwordService")
public class PasswordServiceImpl implements com.provectus.proveng.service.PasswordService {

    private static Logger logger = LogManager.getLogger(PasswordServiceImpl.class);
    @Autowired
    private PasswordRepository passwordRepository;

    @Override
    public void create(Password password) {

        passwordRepository.create(password);
    }

    @Override
    public Password getById(long id) {

        Password password = passwordRepository.getById(id);
        return password;
    }

    @Override
    public Password getByPassword(String password) {

        Password pass = passwordRepository.getByPassword(password);
        return pass;
    }

    @Override
    public Password update(Password password) {
        passwordRepository.update(password);
        return password;
    }

    @Override
    public void delete(Password password) {

        passwordRepository.delete(password);
    }
}
