package com.restaurant.votingsystem.repository;

import com.restaurant.votingsystem.model.User;

import java.util.List;

public interface CrudUserRepository {
    User save(User user);

    boolean delete(int id);

    User get(int id);

    User getByEmail(String email);

    List<User> getAll();
}
