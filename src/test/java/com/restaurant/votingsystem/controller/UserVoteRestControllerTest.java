package com.restaurant.votingsystem.controller;

import com.restaurant.votingsystem.controller.json.JsonUtil;
import com.restaurant.votingsystem.data.VoteTestData;
import com.restaurant.votingsystem.model.Vote;
import com.restaurant.votingsystem.service.VoteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.Month;

import static com.restaurant.votingsystem.TestUtil.userHttpBasic;
import static com.restaurant.votingsystem.data.RestaurantTestData.RESTAURANT1_ID;
import static com.restaurant.votingsystem.data.RestaurantTestData.RESTAURANT3_ID;
import static com.restaurant.votingsystem.data.UserTestData.USER;
import static com.restaurant.votingsystem.data.UserTestData.USER_ID;
import static com.restaurant.votingsystem.data.VoteTestData.VOTE1_ID;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserVoteRestControllerTest extends AbstractRestControllerTest {

    private static final String REST_URL = "/api/v1/";

    @Autowired
    private VoteService voteService;

    @Test
    void getRestaurantsWithMenus() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "restaurants/")
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(5)));
    }

    @Test
    void getVotesByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "votes/by")
                .param("date", "2021-03-26")
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andDo(print());
    }

    @Test
    void create() throws Exception {
        Vote vote = VoteTestData.getNew();
        perform(MockMvcRequestBuilders.post(REST_URL + "restaurants/" + RESTAURANT1_ID + "/votes")
                .with(userHttpBasic(USER))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonUtil.writeValue(vote)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId", is(USER.getId())))
                .andDo(print());
    }

    @Test
    void update() throws Exception {
        Vote updated = voteService.getByUserAndDate(USER_ID, LocalDate.of(2021, Month.MARCH, 25));
        perform(MockMvcRequestBuilders.put(REST_URL + "restaurants/" + RESTAURANT3_ID + "/votes/" + VOTE1_ID)
                .with(userHttpBasic(USER))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk())
                .andDo(print());
        Assertions.assertEquals(voteService.get(VOTE1_ID), updated);
    }
}