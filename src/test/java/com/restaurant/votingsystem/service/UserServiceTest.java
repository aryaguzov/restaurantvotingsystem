package com.restaurant.votingsystem.service;

import com.restaurant.votingsystem.data.UserTestData;
import com.restaurant.votingsystem.model.Role;
import com.restaurant.votingsystem.model.User;
import com.restaurant.votingsystem.util.exception.NotFoundException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import java.util.Collections;
import java.util.List;

import static com.restaurant.votingsystem.data.UserTestData.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class UserServiceTest extends AbstractServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void create() {
        User newUser = getNew();
        User created = userService.create(getNew());
        newUser.setId(created.id());
        assertEquals(created, newUser);
    }

    @Test
    public void delete() {
        userService.delete(USER_ID);
        assertThrows(NotFoundException.class, () -> userService.get(USER_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> userService.delete(NOT_FOUND));
    }

    @Test
    public void get() {
        User user = userService.get(USER_ID);
        assertEquals(user, UserTestData.USER);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> userService.get(NOT_FOUND));
    }

    @Test
    public void getByEmail() {
        User actual = userService.getByEmail("user@gmail.com");
        assertEquals(actual, USER);
    }

    @Test
    public void dublicateMailCreate() {
        assertThrows(DataAccessException.class, ()
                -> userService.create(new User(null, "Duplicate", "user@gmail.com", "newPassword", Collections.singleton(Role.USER))));
    }

    @Test
    public void getByName() {
        User actual = userService.getByName("user");
        assertEquals(actual, USER);
    }

    @Test
    public void update() {
        User updated = getUpdated();
        userService.update(updated);
        assertEquals(userService.get(USER_ID), getUpdated());
    }

    @Test
    public void getAll() {
        List<User> all = userService.getAll();
        assertEquals(all, List.of(USER, ADMIN, USER1, USER2));
    }
}