package com.provectus.proveng.service.impl;

import com.provectus.proveng.domain.*;
import com.provectus.proveng.enumaration.RoleName;
import com.provectus.proveng.enumaration.SysStatus;
import com.provectus.proveng.exception.UserServiceException;
import com.provectus.proveng.repository.UserRepository;
import com.provectus.proveng.service.DayBookService;
import com.provectus.proveng.service.LoginSessionService;
import com.provectus.proveng.service.RoleService;
import com.provectus.proveng.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service("userService")
public class UserServiceImpl implements UserService {

    private static Logger logger = LogManager.getLogger(UserServiceImpl.class);
    @Value("${email.domains}")
    private String[] EMAIL_DOMAINS;
    @Value("${email.domain.enable_check}")
    private boolean EMAIL_DOMAIN_ENABLE_CHECK;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LoginSessionService loginSessionService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private DayBookService dayBookService;

    public User create(User user) {

        userRepository.create(user);
        return user;
    }

    public User getUserByEmail(String email) {
        logger.debug(">> UserService.getByEmail()");
        User user = null;
        try {
            user = userRepository.getByEmail(email);
        } catch (Exception e) {
            logger.debug(">> exception e = " + e.getMessage());
        }
        return user;
    }

    public User registerUser(User user) {

        User savedUser = create(user);
        Role role = roleService.getByName(RoleName.GUEST);
        savedUser.getRoles().add(role);
        this.update(savedUser);
        return savedUser;
    }

    public void logoutUserByToken(String token) {

        logger.debug(">> logout");
        // loginSessionService.deleteSessionByToken(token);
        loginSessionService.turnOffSessionForToken(token);
    }

    public User getUserFromSessionByToken(String token) {

        LoginSession ls = loginSessionService.getLoginSessionByToken(token);
        return ls.getUser();
    }

    public LoginSession getActiveSessionByUser(User user) {
        logger.debug(">>> getActiveSessionByUser for  user = " + user);
        LoginSession ls = null;
        try {
            ls = loginSessionService.getActiveLoginSessionByUser(user);
            logger.debug(">> Session = " + ls);
        } catch (Exception e) {
            logger.debug(">> error = " + e.getMessage());
        }
        return ls;
    }

    public User getById(long id) {
        return userRepository.getById(id);
    }

    public List<User> getAll() {

        return userRepository.getAll();
    }

    public User getByEmail(String email) {

        return userRepository.getByEmail(email);
    }

    @Override
    public List<User> getByRole(String roleName) {
        List<User> users = userRepository.getByRole(roleName);
        for (User u : users) {
            u.setDayBooks(dayBookService.getAllUsersDaybook(u.getId()));
        }
        return users;
    }

    public User updateStatus(User user) {
        userRepository.update(user);
        return user;
    }

    public void update(User user) {

        userRepository.update(user);
    }

    public void delete(User user) {

        userRepository.delete(user);
    }

    @Override
    public void checkIfExist(Long id) throws UserServiceException {
        User user = this.getById(id);
        if (user == null)
            throw new UserServiceException("user with this id doesn't exist");

    }

    @Override
    public void checkIfExist(List<User> users) throws UserServiceException {
        List<Long> unrealMemberIds = new ArrayList<>();
        for (User u : users) {
            if (this.getById(u.getId()) == null) {
                unrealMemberIds.add(u.getId());
            }
        }
        if (unrealMemberIds.size() > 0) {
            throw new UserServiceException("no users with id : " + unrealMemberIds);

        }

    }

    @Override
    public void checkIfExist(Set<Long> userIds) throws UserServiceException {
        List<Long> unrealMemberIds = new ArrayList<>();
        for (Long uId : userIds) {
            if (this.getById(uId) == null) {
                unrealMemberIds.add(uId);
            }
        }
        if (unrealMemberIds.size() > 0) {
            throw new UserServiceException("no users with id : " + unrealMemberIds);

        }

    }

    @Override
    public Long getUserIdByToken(String token) {
        return userRepository.getUserIdByToken(token);

    }

    @Override
    public List<String> getRoleNamesForUser(Long id) {

        return userRepository.getRoleNamesForUser(id);

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> getByLevel(String level) {
        return userRepository.getByLevel(level);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> getByLevelOutsideGroup(String level) {

        List<User> users = userRepository.getByLevel(level);
        List<User> result = new ArrayList<>();
        for (User u : users) {
            if (u.getGroups() != null && !isHasPrimaryGroup(u) && !result.contains(u)) {
                result.add(u);
            }
            if (u.getGroups() == null && !result.contains(u)) {
                result.add(u);
            }
        }
        for (User u : result) {
            deleteDisabledDaybooks(u);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> getByPassedStartTest() {
        List<User> users = userRepository.getByPassedStartLevel();
        for (User u : users) {
            deleteDisabledDaybooks(u);
        }
        return users;
    }

    private boolean isHasPrimaryGroup(User user) {
        for (Group group : user.getGroups()) {
            if (group.getPrimaryGroupFlag()) {
                return true;
            }
        }
        return false;
    }

    private void deleteDisabledDaybooks(User u) {
        List<DayBook> dayBooks = (List<DayBook>) u.getDayBooks();
        for (int i = dayBooks.size() - 1; i >= 0; --i) {
            if (dayBooks.get(i).getSysStatus() == SysStatus.OFF.getNumber()) {
                dayBooks.remove(i);
            }
        }
    }

    @Override
    public boolean isValidEmailDomain(String email) {
        if (!EMAIL_DOMAIN_ENABLE_CHECK)
            return true;
        for (String domain : EMAIL_DOMAINS) {
            if (email.endsWith(domain))
                return true;
        }
        return false;
    }
}
