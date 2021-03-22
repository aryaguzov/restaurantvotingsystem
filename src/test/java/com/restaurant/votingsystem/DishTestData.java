package com.restaurant.votingsystem;

import com.restaurant.votingsystem.model.Dish;

import java.time.LocalDate;
import java.time.Month;

import static com.restaurant.votingsystem.model.BaseEntity.*;
import static com.restaurant.votingsystem.RestaurantTestData.*;

public class DishTestData {
    public static final TestMatcher<Dish> DISH_MATCHER = TestMatcher.usingIgnoringFieldsComparator();
    public static final int DISH1_ID = START_SEQ + 4;
    public static final int DISH2_ID = START_SEQ + 5;
    public static final int NOT_FOUND = 1000000;

    public static Dish dish1 = new Dish(DISH1_ID, "Pizza", LocalDate.of(2021, Month.FEBRUARY, 15), 5, restaurant1);
    public static Dish dish2 = new Dish(DISH2_ID, "Pasta", LocalDate.of(2021, Month.JANUARY, 16), 3, restaurant2);

    public static Dish getNew() {
        return new Dish("Soup", LocalDate.now(), 10, restaurant1);
    }

    public static Dish getUpdated() {
        Dish updated = new Dish(dish2);
        updated.setName("New name");
        updated.setDate(LocalDate.of(2021, Month.MARCH, 10));
        updated.setPrice(10);
        updated.setRestaurant(restaurant1);
        return updated;
    }
}
