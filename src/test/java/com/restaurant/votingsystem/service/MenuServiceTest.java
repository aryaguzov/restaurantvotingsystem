package com.restaurant.votingsystem.service;

import com.restaurant.votingsystem.data.MenuTestData;
import com.restaurant.votingsystem.model.Menu;
import com.restaurant.votingsystem.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static com.restaurant.votingsystem.data.MenuTestData.*;
import static com.restaurant.votingsystem.data.RestaurantTestData.RESTAURANT1_ID;
import static com.restaurant.votingsystem.data.RestaurantTestData.RESTAURANT3_ID;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MenuServiceTest extends AbstractServiceTest {

    @Autowired
    MenuService menuService;

    @Test
    public void create() {
        Menu newMenu = MenuTestData.getNew();
        Menu created = menuService.create(MenuTestData.getNew(), RESTAURANT3_ID);
        newMenu.setId(created.id());
        assertEquals(created, newMenu);
    }

    @Test
    public void get() {
        Menu actual = menuService.get(menu1.getRestaurant().getId(), MENU1_ID);
        MENU_MATCHER.assertMatch(actual, menu1);
    }

    @Test
    void getAll() {
        MENU_MATCHER.assertMatch(menuService.getAll(RESTAURANT1_ID), List.of(menu1));
    }

    @Test
    void delete() {
        menuService.delete(RESTAURANT1_ID, MENU1_ID);
        assertThrows(NotFoundException.class, () -> menuService.get(RESTAURANT1_ID, MENU1_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> menuService.delete(RESTAURANT1_ID, NOT_FOUND));
    }

    @Test
    void update() {
        Menu updated = getUpdated();
        menuService.update(updated, updated.getRestaurant().getId());
        MENU_MATCHER.assertMatch(menuService.get(menu1.getRestaurant().getId(), MENU1_ID), updated);
    }

    @Test
    void getAllByDate() {
        assertEquals(menuService.getAllByDate(LocalDate.of(2021, Month.MARCH, 24)), List.of(menu1));
    }
}