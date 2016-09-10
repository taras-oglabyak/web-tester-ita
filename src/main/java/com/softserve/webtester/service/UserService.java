package com.softserve.webtester.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.webtester.mapper.UserMapper;
import com.softserve.webtester.model.User;

/**
 * UserService class implements CRUD operation on {@link User} instance in the database.<br>
 * The service uses Spring DataSourceTransactionManager for managing transaction with the database and log4j for
 * logging.
 * 
 * @author Taras Oglabyak
 */
@Service
@Transactional(propagation=Propagation.NOT_SUPPORTED, isolation=Isolation.READ_COMMITTED)
public class UserService {

    private static final Logger LOGGER = Logger.getLogger(UserService.class);

    @Autowired
    private UserMapper userMapper;

    /**
     * Loads {@link User} instance from the database.
     * 
     * @param userId identifier of User instance
     * @return {@link User} instance
     * @throws DataAccessException
     */
    public User load(String userId) {
        try {
            return userMapper.load(userId);
        } catch (DataAccessException e) {
            LOGGER.error("User not found, userId: " + userId, e);
            throw e;
        }
    }

    /**
     * Updates {@link User} instance in the database.
     * 
     * @param user user instance should be updated
     * @return number of rows affected by the statement
     * @throws DuplicateKeyException if the user with the username exists in the database.
     * @throws DataAccessException
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public int update(User user) {
        try {
            return userMapper.update(user);
        } catch (DataAccessException e) {
            LOGGER.error("Unable to update the user, username: " + user.getUsername(), e);
            throw e;
        }
    }

    /**
     * Checks the unique of user's username.
     * 
     * @param id id of {@link User} should be excluded
     * @param username username of {@link User} should be checked
     * @return true, if username is unique
     * @throws DataAccessException
     */
    public boolean isUsernameFree(int id, String username) {
        try {
            return userMapper.isUserNameFree(id, username);
        } catch (DataAccessException e) {
            LOGGER.error("Unable to check username, user id: " + id, e);
            throw e;
        }
    }
}