package com.restaurant.votingsystem.service;

import com.restaurant.votingsystem.model.Restaurant;
import com.restaurant.votingsystem.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
public class RestaurantService {

    private RestaurantRepository repository;

    @Autowired
    public void setRepository(RestaurantRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    public Restaurant create(Restaurant restaurant) {
        Objects.requireNonNull(restaurant, "Restaurant must not be null.");
        return repository.save(restaurant);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    public void delete(int id) {
        repository.deleteById(id);
    }

    public Restaurant get(int id) {
        return repository.findById(id).orElseThrow();
    }

    public Restaurant findByName(String name) {
        Assert.notNull(name, "Name must not be null.");
        return repository.findByName(name);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "Restaurant must not be null.");
        repository.save(restaurant);
    }

    public List<Restaurant> getAll() {
        return repository.findAll();
    }
}
