package com.provectus.proveng.service;

import com.provectus.proveng.domain.LoginSession;
import com.provectus.proveng.domain.User;
import com.provectus.proveng.exception.UserServiceException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service("userService")
public interface UserService {

    User create(User user);

    User getUserByEmail(String email);

    User registerUser(User user);

    void logoutUserByToken(String token);

    User getUserFromSessionByToken(String token);

    LoginSession getActiveSessionByUser(User user);

    User getById(long id);

    List<User> getAll();

    User getByEmail(String email);

    List<User> getByRole(String roleName);

    User updateStatus(User user);

    void update(User user);

    void delete(User user);

    void checkIfExist(Long id) throws UserServiceException;

    void checkIfExist(List<User> users) throws UserServiceException;

    void checkIfExist(Set<Long> keySet) throws UserServiceException;

    Long getUserIdByToken(String token);

    List<String> getRoleNamesForUser(Long id);

    List<User> getByLevel(String level);

    List<User> getByLevelOutsideGroup(String level);

    List<User> getByPassedStartTest();

    boolean isValidEmailDomain(String email);

}
