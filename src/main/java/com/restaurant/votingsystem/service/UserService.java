package com.restaurant.votingsystem.service;

import com.restaurant.votingsystem.AuthorizedUser;
import com.restaurant.votingsystem.model.User;
import com.restaurant.votingsystem.repository.UserRepository;
import com.restaurant.votingsystem.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.restaurant.votingsystem.util.ValidationUtil.checkNotFound;
import static com.restaurant.votingsystem.util.ValidationUtil.checkNotFoundWithId;

@Service("userService")
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {

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
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Not found the user with id=" + id);
        }
        repository.delete(id);
    }

    public User get(Integer id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Not found the user with id=" + id));
    }

    public User getByName(String name) {
        Objects.requireNonNull(name, "Name must not be null.");
        return checkNotFound(repository.getByName(name), "name" + name);
    }

    @CachePut(value = "users")
    @Transactional
    public void update(User user) {
        Objects.requireNonNull(user, "User must not be null.");
        checkNotFoundWithId(repository.save(user), user.id());
    }

    @Cacheable(value = "users")
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public AuthorizedUser loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.getByName(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("Can't find a user with name %s", username));
        }
        return new AuthorizedUser(user);
    }
}
