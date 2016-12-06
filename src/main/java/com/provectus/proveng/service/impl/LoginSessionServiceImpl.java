package com.provectus.proveng.service.impl;

import com.provectus.proveng.domain.LoginSession;
import com.provectus.proveng.domain.User;
import com.provectus.proveng.exception.ErrorResponseException;
import com.provectus.proveng.repository.LoginSessionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class LoginSessionServiceImpl implements com.provectus.proveng.service.LoginSessionService {
    private static Logger logger = LogManager.getLogger(LoginSessionServiceImpl.class);
    @Autowired
    private LoginSessionRepository loginSessionRepository;

    @Override
    public String generateToken(String str) {
        String token = System.currentTimeMillis() + str;
        logger.debug(">> generated token =" + token);
        return token;
    }

    @Override
    public LoginSession createLoginSession(User user) {

        logger.debug(">>> create session for user, userId = " + user.getId());
        String token = generateToken(user.getEmail());

        LoginSession ls = new LoginSession(user, token);
        logger.debug(">> session created = " + ls);

        LoginSession savedLoginSession =
                loginSessionRepository.createLoginSession(ls);
        logger.debug(">> session saved to db = " + savedLoginSession);
        return savedLoginSession;
    }

    @Override
    public LoginSession getActiveLoginSessionByUser(User user) {
        // TODO Auto-generated method stub
        logger.debug(">> getLoginSession(user)");
        return loginSessionRepository.getActiveSessionByUserId(user.getId());
    }

    @Override
    public void deleteSessionByToken(String token) {
        logger.debug(">>> deleteSesionByToken : token = " + token);
        LoginSession ls = loginSessionRepository.getbyToken(token);
        logger.debug(">> session = " + ls);
        if (ls != null) {
            loginSessionRepository.delete(ls);
        }
    }

    @Override
    public void turnOffSessionForToken(String token) {
        logger.debug(">>> turnOffSessionForToken, token = " + token);
        LoginSession ls = loginSessionRepository.getActiveSessionByToken(token);
        logger.debug(">> session = " + ls);
        if (ls == null) {
            throw new ErrorResponseException("logoutError", "no active session for token",
                    HttpStatus.BAD_REQUEST);
        }
        if (ls != null) {
            ls.setSysStatus(-1);
            loginSessionRepository.update(ls);
        }
    }

    @Override
    public LoginSession getLoginSessionByToken(String token) {
        logger.debug(">>> getSessonByToken for token = " + token);
        LoginSession ls = loginSessionRepository.getActiveSessionByToken(token);
        logger.debug(">> loginSession = " + ls);
        return ls;
    }
}
