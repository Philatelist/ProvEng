package com.provectus.proveng.service;

import com.provectus.proveng.domain.LoginSession;
import com.provectus.proveng.domain.User;

public interface LoginSessionService {

    String generateToken(String str);

    LoginSession createLoginSession(User user);

    LoginSession getActiveLoginSessionByUser(User user);

    void deleteSessionByToken(String token);

    void turnOffSessionForToken(String token);

    LoginSession getLoginSessionByToken(String token);
}
