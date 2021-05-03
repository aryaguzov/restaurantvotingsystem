package com.restaurant.votingsystem.controller;

import com.restaurant.votingsystem.controller.json.JsonUtil;
import com.restaurant.votingsystem.model.User;
import com.restaurant.votingsystem.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.restaurant.votingsystem.TestUtil.userHttpBasic;
import static com.restaurant.votingsystem.data.UserTestData.*;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProfileRestControllerTest extends AbstractRestControllerTest {
    private static final String REST_URL = "/api/v1/profile/";

    @Autowired
    private UserService userService;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.name", is(USER.getName())))
                .andExpect(jsonPath("$.email", is(USER.getEmail())))
                .andDo(print());
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk());
        assertEquals(userService.getAll(), List.of(USER, ADMIN, USER2));
    }

    @Test
    void update() throws Exception {
        User user = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL)
                .with(userHttpBasic(USER))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonUtil.writeValue(user)))
                .andExpect(status().isOk())
                .andDo(print());
        assertEquals(userService.get(USER_ID), getUpdated());
    }
}