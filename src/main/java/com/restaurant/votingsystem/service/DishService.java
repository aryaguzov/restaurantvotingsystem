package com.restaurant.votingsystem.service;

import com.restaurant.votingsystem.model.Dish;
import com.restaurant.votingsystem.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
public class DishService {

    private DishRepository repository;

    @Autowired
    public void setRepository(DishRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(value = "dishes", allEntries = true)
    @Transactional
    public Dish create(Dish dish) {
        Objects.requireNonNull(dish, "Dish must not be null.");
        return repository.save(dish);
    }

    @CacheEvict(value = "dishes", allEntries = true)
    @Transactional
    public void delete(int id) {
        repository.deleteById(id);
    }

    public Dish get(int id) {
        return repository.findById(id).orElseThrow();
    }

    public List<Dish> getAllByDate(LocalDate date) {
        Objects.requireNonNull(date, "Date must not be null.");
        return repository.getAllByDateOrderByDateAsc(date);
    }

    @CacheEvict(value = "dishes", allEntries = true)
    @Transactional
    public void update(Dish dish) {
        Objects.requireNonNull(dish, "Dish must not be null.");
        repository.save(dish);
    }

    public List<Dish> getAll() {
        return repository.findAll();
    }
}
