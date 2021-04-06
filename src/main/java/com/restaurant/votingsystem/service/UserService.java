package com.restaurant.votingsystem.service;

import com.restaurant.votingsystem.model.User;
import com.restaurant.votingsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.restaurant.votingsystem.util.ValidationUtil.*;

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
        return checkNotFoundWithId(repository.save(user), user.id());
    }

    @CacheEvict(value = "users", allEntries = true)
    @Transactional
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public User get(int id) {
        return repository.findById(id).orElseThrow();
    }

    public User findByEmail(String email) {
        Objects.requireNonNull(email, "Email must not be null.");
        return checkNotFound(repository.getByEmail(email), "email" + email);
    }

    @CacheEvict(value = "users", allEntries = true)
    @Transactional
    public void update(User user) {
        Objects.requireNonNull(user, "User must not be null.");
        checkNotFoundWithId(repository.save(user), user.id());
    }

    public List<User> getAll() {
        return repository.findAll();
    }
}
