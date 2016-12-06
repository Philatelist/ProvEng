package com.provectus.proveng.repository.impl;

import com.provectus.proveng.domain.LoginSession;
import com.provectus.proveng.exception.ErrorResponseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Transactional
public class LoginSessionRepositoryImpl implements com.provectus.proveng.repository.LoginSessionRepository {

    private static final Logger logger = LogManager.getLogger(LoginSessionRepositoryImpl.class);

    static {
        logger.info(">>> loaded LoginSessionRepository");
    }

    @PersistenceContext
    private EntityManager em;

    @Override
    public LoginSession createLoginSession(LoginSession session) {
        logger.debug(">> saving session to db: " + session.toString());
        em.persist(session);
        return session;
    }

    @Override
    public LoginSession getByUserId(Long id) {
        logger.debug(">> getting session from db for user id = " + id);
        LoginSession loginSession = null;
        try {
            loginSession = (LoginSession) em
                    .createQuery("from LoginSession where user.id = :user_id")
                    .setParameter("user_id", id).getSingleResult();
        } catch (NoResultException e) {
            logger.debug(">> exception = " + e.getMessage());
            throw new ErrorResponseException("sessionError", "session not exist",
                    HttpStatus.BAD_REQUEST);
        }
        logger.debug(">> LoginSession from db = " + loginSession);
        return loginSession;
    }

    @Override
    public LoginSession getbyToken(String token) {
        logger.debug(">> getting session from db for token = " + token);
        LoginSession loginSession = null;
        try {
            loginSession = (LoginSession) em.createQuery("from LoginSession where token = :token")
                    .setParameter("token", token).getSingleResult();
        } catch (NoResultException e) {
            logger.debug(">> exception = " + e.getMessage());

        }
        logger.debug(">> LoginSession from db = " + loginSession);
        return loginSession;
    }

    @Override
    public LoginSession getActiveSessionByToken(String token) {
        logger.debug(">> getting active session from db for token = " + token);
        LoginSession loginSession = null;
        try {
            loginSession = (LoginSession) em
                    .createQuery("from LoginSession where token = :token and sysStatus = 0")
                    .setParameter("token", token).getSingleResult();
        } catch (NoResultException e) {
            logger.debug(">> exception = " + e.getMessage());
            throw new ErrorResponseException("sessionError", "no active session for token",
                    HttpStatus.BAD_REQUEST);


        }
        logger.debug(">> LoginSession from db = " + loginSession);
        return loginSession;
    }

    @Override
    public void delete(LoginSession ls) {
        logger.debug(">> deleting session from db for token = " + ls.getToken());
        em.remove(ls);

    }

    @Override
    public void update(LoginSession ls) {
        logger.debug(">> deleting session from db for token = " + ls.getToken());
        em.merge(ls);

    }

    @Override
    public LoginSession getActiveSessionByUserId(long id) {
        logger.debug(">> getting active session from db for user id = " + id);
        LoginSession loginSession = null;
        try {
            loginSession = (LoginSession) em
                    .createQuery("from LoginSession where user.id = :user_id and sysStatus = 0")
                    .setParameter("user_id", id).getSingleResult();
        } catch (NoResultException e) {
            logger.debug(">> exception = " + e.getMessage());
            throw new ErrorResponseException("sessionError", "User has no active session",
                    HttpStatus.BAD_REQUEST);

        }
        logger.debug("LoginSession from db = " + loginSession);
        return loginSession;
    }

}
