package com.restaurant.votingsystem.service;

import com.restaurant.votingsystem.UserTestData;
import com.restaurant.votingsystem.model.Role;
import com.restaurant.votingsystem.model.User;

import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.NoSuchElementException;

import static com.restaurant.votingsystem.UserTestData.*;

class UserServiceTest extends AbstractServiceTest {

    @Autowired
    UserService userService;

    @Test
    void create() {
        User created = userService.create(getNew());
        int newId = created.id();
        User newUser = getNew();
        newUser.setId(newId);
        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(userService.get(newId), newUser);
    }

    @Test
    void delete() {
        userService.delete(UserTestData.USER_ID);
        assertThrows(NoSuchElementException.class, () -> userService.get(UserTestData.USER_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(EmptyResultDataAccessException.class, () -> userService.delete(NOT_FOUND));
    }

    @Test
    void get() {
        User user = userService.get(USER_ID);
        USER_MATCHER.assertMatch(user, UserTestData.USER);
    }

    @Test
    void getNotFound() {
        assertThrows(NoSuchElementException.class, () -> userService.get(NOT_FOUND));
    }

    @Test
    void findByEmail() {
        User user = userService.findByEmail("user@gmail.com");
        USER_MATCHER.assertMatch(user, UserTestData.USER);
    }

    @Test
    void dublicateMailCreate() {
        assertThrows(DataAccessException.class, ()
                -> userService.create(new User(null, "Duplicate", "user@gmail.com", "newPassword", Role.USER)));
    }

    @Test
    void update() {
        User updated = getUpdated();
        userService.update(updated);
        USER_MATCHER.assertMatch(userService.get(USER_ID), getUpdated());
    }

    @Test
    void getAll() {
        List<User> all = userService.getAll();
        USER_MATCHER.assertMatch(all, USER, ADMIN);
    }
}