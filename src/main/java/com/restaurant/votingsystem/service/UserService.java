package com.restaurant.votingsystem.service;

import com.restaurant.votingsystem.model.User;
import com.restaurant.votingsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
public class UserService {

    private UserRepository repository;

    @Autowired
    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(value = "users", allEntries = true)
    @Transactional
    public User create(User user) {
        Objects.requireNonNull(user, "User must not be null.");
        return repository.save(user);
    }

    @CacheEvict(value = "users", allEntries = true)
    @Transactional
    public void delete(int id) {
        repository.deleteById(id);
    }

    public User get(int id) {
        return repository.findById(id).orElseThrow();
    }

    public User findByEmail(String email) {
        Assert.notNull(email, "Email must not be null.");
        return repository.findByEmail(email);
    }

    @CacheEvict(value = "users", allEntries = true)
    @Transactional
    public void update(User user) {
        Assert.notNull(user, "User must not be null.");
        repository.save(user);
    }

    public List<User> getAll() {
        return repository.findAll();
    }
}
