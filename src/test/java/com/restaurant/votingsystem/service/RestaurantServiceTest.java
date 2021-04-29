package com.restaurant.votingsystem.service;

import com.restaurant.votingsystem.model.Restaurant;
import com.restaurant.votingsystem.util.exception.NotFoundException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import java.util.List;

import static com.restaurant.votingsystem.data.RestaurantTestData.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    RestaurantService restaurantService;

    @Test
    public void create() {
        Restaurant created = restaurantService.create(getNew());
        int newId = created.id();
        Restaurant newRestaurant = getNew();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(restaurantService.get(newId), newRestaurant);
    }

    @Test
    public void delete() {
        restaurantService.delete(RESTAURANT1_ID);
        assertThrows(NotFoundException.class, () -> restaurantService.get(RESTAURANT1_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> restaurantService.delete(NOT_FOUND));
    }

    @Test
    public void get() {
        Restaurant restaurant = restaurantService.get(RESTAURANT2_ID);
        RESTAURANT_MATCHER.assertMatch(restaurant, RESTAURANT_2);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> restaurantService.get(NOT_FOUND));
    }

    @Test
    public void duplicateNameCreate() {
        assertThrows(DataAccessException.class, () ->
                restaurantService.create(new Restaurant(null, "LuckyPub", "Moscow, Russia")));
    }

    @Test
    public void update() {
        Restaurant updated = getUpdated();
        restaurantService.update(updated);
        RESTAURANT_MATCHER.assertMatch(restaurantService.get(RESTAURANT1_ID), getUpdated());
    }

    @Test
    public void getAll() {
        RESTAURANT_MATCHER.assertMatch(restaurantService.getAll(), RESTAURANTS);
    }

    @Test
    public void getWithMenus() {
        Restaurant actual = restaurantService.getWithMenus(RESTAURANT1_ID);
        assertEquals(actual, RESTAURANT_1);
    }

    @Test
    public void getRestaurantsWithMenus() {
        List<Restaurant> actual = restaurantService.getRestaurantsWithMenus();
        assertEquals(actual, RESTAURANTS);
    }
}