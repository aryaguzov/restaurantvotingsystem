package com.restaurant.votingsystem.service;

import com.restaurant.votingsystem.model.Dish;
import com.restaurant.votingsystem.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.restaurant.votingsystem.data.DishTestData.*;
import static com.restaurant.votingsystem.data.MenuTestData.MENU1_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DishServiceTest extends AbstractServiceTest {

    @Autowired
    private DishService dishService;

    @Test
    void create() {
        Dish newDish = getNew();
        Dish created = dishService.create(getNew(), MENU1_ID);
        newDish.setId(created.id());
        assertEquals(created, newDish);
    }

    @Test
    void delete() {
        dishService.delete(DISH1_ID, MENU1_ID);
        assertThrows(NotFoundException.class, () -> dishService.get(DISH1_ID, MENU1_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> dishService.delete(NOT_FOUND, MENU1_ID));
    }

    @Test
    void get() {
        Dish actual = dishService.get(DISH1_ID, MENU1_ID);
        assertEquals(actual, DISH_1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> dishService.get(NOT_FOUND, MENU1_ID));
    }

    @Test
    void update() {
        Dish updated = getUpdated();
        dishService.update(updated, MENU1_ID);
        assertEquals(dishService.get(DISH1_ID, MENU1_ID), updated);
    }

    @Test
    void getAll() {
        List<Dish> actual = dishService.getAll(MENU1_ID);
        assertEquals(actual, List.of(DISH_1, DISH_2, DISH_3));
    }
}