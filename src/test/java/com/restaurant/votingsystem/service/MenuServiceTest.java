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
import static com.restaurant.votingsystem.data.MenuTestData.NOT_FOUND;
import static com.restaurant.votingsystem.data.RestaurantTestData.*;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MenuServiceTest extends AbstractServiceTest {

    @Autowired
    private MenuService menuService;

    @Test
    void create() {
        Menu newMenu = MenuTestData.getNew();
        Menu created = menuService.create(MenuTestData.getNew(), RESTAURANT3_ID);
        newMenu.setId(created.id());
        assertEquals(created, newMenu);
    }

    @Test
    void get() {
        Menu actual = menuService.get(MENU_1.getRestaurant().getId(), MENU1_ID);
        assertEquals(actual, MENU_1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> menuService.get(MENU_1.getRestaurant().getId(), NOT_FOUND));
    }

    @Test
    void getAll() {
        assertEquals(menuService.getAll(RESTAURANT1_ID), List.of(MENU_1));
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
        Menu updated = MenuTestData.getUpdated();
        menuService.update(updated, updated.getRestaurant().getId());
        assertEquals(menuService.get(MENU_1.getRestaurant().getId(), MENU1_ID), updated);
    }

    @Test
    void getAllByDate() {
        assertEquals(menuService.getAllByDate(LocalDate.of(2021, Month.MARCH, 24)), List.of(MENU_1));
    }
}