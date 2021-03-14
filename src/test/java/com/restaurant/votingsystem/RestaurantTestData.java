package com.restaurant.votingsystem;

import com.restaurant.votingsystem.model.Restaurant;

import java.time.LocalDateTime;
import java.time.Month;

import static com.restaurant.votingsystem.model.BaseEntity.*;

public class RestaurantTestData {
    public static final TestMatcher<Restaurant> RESTAURANT_MATCHER = TestMatcher.usingIgnoringFieldsComparator("registered", "enabled");
    public static final int RESTAURANT1_ID = START_SEQ + 2;
    public static final int RESTAURANT2_ID = START_SEQ + 3;
    public static final int NOT_FOUND = 1000000;

    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT1_ID, "Breaking Bad", "Los Angeles, USA",
            LocalDateTime.of(2015, Month.FEBRUARY, 20, 0, 0, 0),
            true);
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT2_ID, "Lucky Pub", "Melitopol, Ukraine",
            LocalDateTime.of(2021, Month.FEBRUARY, 20, 0, 0, 0),
            true);

    public static Restaurant getNew() {
        return new Restaurant("Black Sea", "Odessa, Ukraine", LocalDateTime.now(), true);
    }

    public static Restaurant getUpdated() {
        Restaurant updated = new Restaurant(restaurant1);
        updated.setName("New name");
        updated.setContacts("New contacts");
        return updated;
    }
}
