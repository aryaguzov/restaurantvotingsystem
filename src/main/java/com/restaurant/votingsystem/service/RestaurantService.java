package com.restaurant.votingsystem.service;

import com.restaurant.votingsystem.model.Restaurant;
import com.restaurant.votingsystem.repository.RestaurantRepository;
import com.restaurant.votingsystem.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.restaurant.votingsystem.util.ValidationUtil.checkNotFoundWithId;

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
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Not found the restaurant with id=" + id);
        }
        repository.delete(id);
    }

    public Restaurant get(Integer id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Not found the restaurant with id=" + id));
    }

    @CachePut(value = "restaurants")
    @Transactional
    public void update(Restaurant restaurant) {
        Objects.requireNonNull(restaurant, "Restaurant must not be null.");
        checkNotFoundWithId(repository.save(restaurant), restaurant.id());
    }

    @Cacheable(value = "restaurants")
    public List<Restaurant> getAll() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }
}
