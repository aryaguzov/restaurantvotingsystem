package com.restaurant.votingsystem.service;

import com.restaurant.votingsystem.model.Dish;
import com.restaurant.votingsystem.repository.DishRepository;
import com.restaurant.votingsystem.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.restaurant.votingsystem.util.ValidationUtil.*;

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
        checkNotFoundWithId(repository.delete(id), id);
    }

    public Dish get(int id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Not found the dish with id=" + id));
    }

    public List<Dish> getAllByDate(LocalDateTime date) {
        Objects.requireNonNull(date, "Date must not be null.");
        return repository.getAllByDate(date);
    }

    @CacheEvict(value = "dishes", allEntries = true)
    @Transactional
    public void update(Dish dish) {
        Objects.requireNonNull(dish, "Dish must not be null.");
        checkNotFoundWithId(repository.save(dish), dish.id());
    }

    public List<Dish> getAll() {
        return repository.findAll();
    }
}
