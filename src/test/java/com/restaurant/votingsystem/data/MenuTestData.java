package com.restaurant.votingsystem.data;

import com.restaurant.votingsystem.model.Menu;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Set;

import static com.restaurant.votingsystem.data.DishTestData.*;
import static com.restaurant.votingsystem.data.RestaurantTestData.*;
import static com.restaurant.votingsystem.model.AbstractEntity.START_SEQ;

public class MenuTestData {
    public static final TestMatcher<Menu> MENU_MATCHER = TestMatcher.usingIgnoringFieldsComparator("restaurant", "dishes");

    public static final int MENU1_ID = START_SEQ + 9;
    public static final int MENU2_ID = START_SEQ + 10;
    public static final int MENU3_ID = START_SEQ + 11;
    public static final int MENU4_ID = START_SEQ + 12;
    public static final int MENU5_ID = START_SEQ + 13;
    public static final int NOT_FOUND = 100000;

    public static final Menu menu1 = new Menu(MENU1_ID, LocalDate.of(2021, Month.MARCH, 24), restaurant1, Set.of(dish1, dish2, dish3));
    public static final Menu menu2 = new Menu(MENU2_ID, LocalDate.of(2021, Month.MARCH, 25), restaurant2, Set.of(dish4, dish5, dish6));
    public static final Menu menu3 = new Menu(MENU3_ID, LocalDate.of(2021, Month.MARCH, 26), restaurant3, Set.of(dish7, dish8, dish9));
    public static final Menu menu4 = new Menu(MENU4_ID, LocalDate.of(2021, Month.MARCH, 27), restaurant4, Set.of(dish10, dish11, dish12));
    public static final Menu menu5 = new Menu(MENU5_ID, LocalDate.of(2021, Month.MARCH, 28), restaurant5, Set.of(dish13, dish14, dish15));

    public static final List<Menu> menus = List.of(menu1, menu2, menu3, menu4, menu5);

    public static Menu getNew() {
        return new Menu(null, LocalDate.of(2021, Month.APRIL, 14), restaurant3, Set.of(dish5, dish10, dish15));
    }

    public static Menu getUpdated() {
        Menu updated = new Menu(menu1);
        updated.setDate(LocalDate.now());
        updated.setDishes(Set.of(dish11, dish12));
        return updated;
    }

}
