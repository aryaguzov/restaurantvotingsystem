package com.restaurant.votingsystem.data;

import com.restaurant.votingsystem.model.Menu;

import java.time.LocalDate;
import java.time.Month;
import java.util.Set;

import static com.restaurant.votingsystem.data.DishTestData.*;
import static com.restaurant.votingsystem.data.RestaurantTestData.*;
import static com.restaurant.votingsystem.model.AbstractEntity.START_SEQ;

public class MenuTestData {
    public static final int MENU1_ID = START_SEQ + 9;
    public static final int MENU2_ID = START_SEQ + 10;
    public static final int MENU3_ID = START_SEQ + 11;
    public static final int MENU4_ID = START_SEQ + 12;
    public static final int MENU5_ID = START_SEQ + 13;
    public static final int NOT_FOUND = 100000;

    public static final Menu MENU_1 = new Menu(MENU1_ID, LocalDate.of(2021, Month.MARCH, 24), RESTAURANT_1, Set.of(DISH_1, DISH_2, DISH_3));
    public static final Menu MENU_2 = new Menu(MENU2_ID, LocalDate.of(2021, Month.MARCH, 25), RESTAURANT_2, Set.of(DISH_4, DISH_5, DISH_6));
    public static final Menu MENU_3 = new Menu(MENU3_ID, LocalDate.of(2021, Month.MARCH, 26), RESTAURANT_3, Set.of(DISH_7, DISH_8, DISH_9));
    public static final Menu MENU_4 = new Menu(MENU4_ID, LocalDate.of(2021, Month.MARCH, 27), RESTAURANT_4, Set.of(DISH_10, DISH_11, DISH_12));
    public static final Menu MENU_5 = new Menu(MENU5_ID, LocalDate.of(2021, Month.MARCH, 28), RESTAURANT_5, Set.of(DISH_13, DISH_14, DISH_15));

    public static Menu getNew() {
        return new Menu(null, LocalDate.of(2021, Month.APRIL, 14), RESTAURANT_3, Set.of(DISH_5, DISH_10, DISH_15));
    }

    public static Menu getUpdated() {
        Menu updated = new Menu(MENU_1);
        updated.setDate(LocalDate.now());
        updated.setDishes(Set.of(DISH_11, DISH_12));
        return updated;
    }

}
