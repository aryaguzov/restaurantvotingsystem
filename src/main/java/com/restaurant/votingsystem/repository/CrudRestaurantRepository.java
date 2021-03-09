package com.restaurant.votingsystem.repository;

import com.restaurant.votingsystem.model.Restaurant;

import java.util.List;

public interface CrudRestaurantRepository {
    Restaurant save(Restaurant restaurant);

    boolean delete(int id);

    Restaurant get(int id);

    Restaurant getByName(String name);

    List<Restaurant> getAll();
}
