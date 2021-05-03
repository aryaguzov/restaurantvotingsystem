package com.restaurant.votingsystem.controller;

import com.restaurant.votingsystem.controller.json.JsonUtil;
import com.restaurant.votingsystem.model.Restaurant;
import com.restaurant.votingsystem.service.RestaurantService;
import com.restaurant.votingsystem.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.restaurant.votingsystem.TestUtil.userHttpBasic;
import static com.restaurant.votingsystem.data.RestaurantTestData.*;
import static com.restaurant.votingsystem.data.UserTestData.ADMIN;
import static com.restaurant.votingsystem.data.UserTestData.NOT_FOUND;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RestaurantRestControllerTest extends AbstractRestControllerTest {

    private static final String REST_URL = "/api/v1/admin/restaurants/";

    @Autowired
    private RestaurantService restaurantService;

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(5)));
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT1_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.name", is(RESTAURANT_1.getName())));
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + NOT_FOUND)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + RESTAURANT2_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> restaurantService.get(RESTAURANT2_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + NOT_FOUND)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void create() throws Exception {
        Restaurant newRestaurant = getNew();
        perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonUtil.writeValue(newRestaurant)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(newRestaurant.getName())))
                .andExpect(jsonPath("$.contacts", is(newRestaurant.getContacts())))
                .andDo(print());
    }

    @Test
    void update() throws Exception {
        Restaurant updated = getUpdated();
        updated.setId(null);
        perform(MockMvcRequestBuilders.put(REST_URL + RESTAURANT3_ID)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent())
                .andDo(print());

        assertEquals(restaurantService.get(RESTAURANT3_ID), updated);
    }
}