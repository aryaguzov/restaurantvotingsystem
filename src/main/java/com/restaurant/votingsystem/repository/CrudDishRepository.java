package com.restaurant.votingsystem.repository;

import com.restaurant.votingsystem.model.Dish;

import java.time.LocalDate;
import java.util.List;

public interface CrudDishRepository {
    Dish save(Dish dish);

    boolean delete(int id);

    Dish get(int id);

    Dish getByName(String name);

    List<Dish> getAllByDate(LocalDate date);

    List<Dish> getAll();
}
