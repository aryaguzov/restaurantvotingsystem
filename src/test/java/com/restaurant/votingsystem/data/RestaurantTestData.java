package com.restaurant.votingsystem.data;

import com.restaurant.votingsystem.model.Restaurant;

import java.util.List;

import static com.restaurant.votingsystem.model.AbstractEntity.START_SEQ;

public class RestaurantTestData {
    public static final TestMatcher<Restaurant> RESTAURANT_MATCHER = TestMatcher.usingIgnoringFieldsComparator("menus");
    public static final int RESTAURANT1_ID = START_SEQ + 4;
    public static final int RESTAURANT2_ID = START_SEQ + 5;
    public static final int RESTAURANT3_ID = START_SEQ + 6;
    public static final int RESTAURANT4_ID = START_SEQ + 7;
    public static final int RESTAURANT5_ID = START_SEQ + 8;
    public static final int NOT_FOUND = 1000000;

    public static final Restaurant RESTAURANT_1 = new Restaurant(RESTAURANT1_ID, "BreakingBad", "Los Angeles, USA");
    public static final Restaurant RESTAURANT_2 = new Restaurant(RESTAURANT2_ID, "LuckyPub", "Odessa, Ukraine");
    public static final Restaurant RESTAURANT_3 = new Restaurant(RESTAURANT3_ID, "Consuela", "Paris, France");
    public static final Restaurant RESTAURANT_4 = new Restaurant(RESTAURANT4_ID, "No Time To Die", "Amsterdam, Netherlands");
    public static final Restaurant RESTAURANT_5 = new Restaurant(RESTAURANT5_ID, "Drink Milk", "Kyiv, Ukraine");

    public static final List<Restaurant> RESTAURANTS = List.of(RESTAURANT_1, RESTAURANT_3, RESTAURANT_5, RESTAURANT_2, RESTAURANT_4);

    public static Restaurant getNew() {
        return new Restaurant(null, "Black Sea", "Odessa, Ukraine");
    }

    public static Restaurant getUpdated() {
        Restaurant updated = new Restaurant(RESTAURANT_1);
        updated.setName("New name");
        updated.setContacts("New contacts");
        return updated;
    }
}
