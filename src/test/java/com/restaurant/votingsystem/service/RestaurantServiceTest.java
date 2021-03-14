package com.restaurant.votingsystem.service;

import com.restaurant.votingsystem.model.Restaurant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static com.restaurant.votingsystem.RestaurantTestData.*;

class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    RestaurantService restaurantService;

    @Test
    void create() {
        Restaurant created = restaurantService.create(getNew());
        int newId = created.id();
        Restaurant newRestaurant = getNew();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(restaurantService.get(newId), newRestaurant);
    }

    @Test
    void delete() {
        restaurantService.delete(RESTAURANT1_ID);
        assertThrows(NoSuchElementException.class, () -> restaurantService.get(RESTAURANT1_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(EmptyResultDataAccessException.class, () -> restaurantService.delete(NOT_FOUND));
    }

    @Test
    void get() {
        Restaurant restaurant = restaurantService.get(RESTAURANT2_ID);
        RESTAURANT_MATCHER.assertMatch(restaurant, restaurant2);
    }

    @Test
    void getNotFound() {
        assertThrows(NoSuchElementException.class, () -> restaurantService.get(NOT_FOUND));
    }

    @Test
    void findByName() {
        Restaurant restaurant = restaurantService.findByName("Lucky Pub");
        RESTAURANT_MATCHER.assertMatch(restaurant, restaurant2);
    }

    @Test
    void update() {
        Restaurant updated = getUpdated();
        restaurantService.update(updated);
        RESTAURANT_MATCHER.assertMatch(restaurantService.get(RESTAURANT1_ID), getUpdated());
    }

    @Test
    void getAll() {
        List<Restaurant> all = restaurantService.getAll();
        RESTAURANT_MATCHER.assertMatch(all, restaurant1, restaurant2);
    }
}