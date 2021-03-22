package com.restaurant.votingsystem.service;

import com.restaurant.votingsystem.model.Dish;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static com.restaurant.votingsystem.DishTestData.*;

class DishServiceTest extends AbstractServiceTest {

    @Autowired
    DishService dishService;

    @Test
    void create() {
        Dish created = dishService.create(getNew());
        int newId = created.id();
        Dish newDish = getNew();
        newDish.setId(newId);
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(dishService.get(newId), newDish);
    }

    @Test
    void delete() {
        dishService.delete(DISH1_ID);
        assertThrows(NoSuchElementException.class, () -> dishService.get(DISH1_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(EmptyResultDataAccessException.class, () -> dishService.delete(NOT_FOUND));
    }

    @Test
    void get() {
        Dish dish = dishService.get(DISH2_ID);
        DISH_MATCHER.assertMatch(dish, dish2);
    }

    @Test
    void getAllByDate() {
        DISH_MATCHER.assertMatch(dishService.getAllByDate(LocalDate.of(2021, Month.JANUARY, 16)), dish2);
    }

    @Test
    void update() {
        Dish updated = getUpdated();
        dishService.update(updated);
        DISH_MATCHER.assertMatch(dishService.get(DISH2_ID), getUpdated());
    }

    @Test
    void getAll() {
        List<Dish> all = dishService.getAll();
        DISH_MATCHER.assertMatch(all, dish1, dish2);
    }
}