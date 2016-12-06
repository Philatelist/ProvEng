package com.provectus.proveng.repository;

import com.provectus.proveng.domain.LoginSession;

public interface LoginSessionRepository {

    LoginSession createLoginSession(LoginSession session);

    LoginSession getByUserId(Long id);

    LoginSession getbyToken(String token);

    LoginSession getActiveSessionByToken(String token);

    void delete(LoginSession ls);

    void update(LoginSession ls);

    LoginSession getActiveSessionByUserId(long id);
}
