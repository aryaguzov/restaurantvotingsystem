package com.restaurant.votingsystem.data;

import com.restaurant.votingsystem.model.Dish;

import static com.restaurant.votingsystem.model.AbstractEntity.START_SEQ;

public class DishTestData {
    public static final int DISH1_ID = START_SEQ + 14;
    public static final int DISH2_ID = START_SEQ + 15;
    public static final int DISH3_ID = START_SEQ + 16;
    public static final int DISH4_ID = START_SEQ + 17;
    public static final int DISH5_ID = START_SEQ + 18;
    public static final int DISH6_ID = START_SEQ + 19;
    public static final int DISH7_ID = START_SEQ + 20;
    public static final int DISH8_ID = START_SEQ + 21;
    public static final int DISH9_ID = START_SEQ + 22;
    public static final int DISH10_ID = START_SEQ + 23;
    public static final int DISH11_ID = START_SEQ + 24;
    public static final int DISH12_ID = START_SEQ + 25;
    public static final int DISH13_ID = START_SEQ + 26;
    public static final int DISH14_ID = START_SEQ + 27;
    public static final int DISH15_ID = START_SEQ + 28;
    public static final int NOT_FOUND = 1000000;

    public static final Dish DISH_1 = new Dish(DISH1_ID, "Pizza", 5);
    public static final Dish DISH_2 = new Dish(DISH2_ID, "Steak", 10);
    public static final Dish DISH_3 = new Dish(DISH3_ID, "Beer", 2);
    public static final Dish DISH_4 = new Dish(DISH4_ID, "Soup", 6);
    public static final Dish DISH_5 = new Dish(DISH5_ID, "Pizza", 6);
    public static final Dish DISH_6 = new Dish(DISH6_ID, "Aperol", 3);
    public static final Dish DISH_7 = new Dish(DISH7_ID, "Soup", 6);
    public static final Dish DISH_8 = new Dish(DISH8_ID, "Pizza", 6);
    public static final Dish DISH_9 = new Dish(DISH9_ID, "Beer", 3);
    public static final Dish DISH_10 = new Dish(DISH10_ID, "Soup", 6);
    public static final Dish DISH_11 = new Dish(DISH11_ID, "Pizza", 6);
    public static final Dish DISH_12 = new Dish(DISH12_ID, "Beer", 3);
    public static final Dish DISH_13 = new Dish(DISH13_ID, "Soup", 6);
    public static final Dish DISH_14 = new Dish(DISH14_ID, "Pizza", 6);
    public static final Dish DISH_15 = new Dish(DISH15_ID, "Pasta", 3);

    public static Dish getNew() {
        return new Dish(null, "Soup", 10);
    }

    public static Dish getUpdated() {
        Dish updated = new Dish(DISH_1);
        updated.setName("New name");
        updated.setPrice(10);
        return updated;
    }
}
