package com.restaurant.votingsystem.service;

import com.restaurant.votingsystem.model.Vote;
import com.restaurant.votingsystem.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static com.restaurant.votingsystem.data.RestaurantTestData.RESTAURANT1_ID;
import static com.restaurant.votingsystem.data.RestaurantTestData.RESTAURANT2_ID;
import static com.restaurant.votingsystem.data.UserTestData.USER_ID;
import static com.restaurant.votingsystem.data.VoteTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    private VoteService voteService;

    @Test
    void create() {
        Vote newVote = getNew();
        Vote created = voteService.create(newVote, USER_ID, RESTAURANT1_ID);
        newVote.setId(created.id());
        assertEquals(created, newVote);
    }

    @Test
    void delete() {
        voteService.delete(VOTE1_ID);
        assertThrows(NotFoundException.class, () -> voteService.get(VOTE1_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> voteService.delete(NOT_FOUND));
    }

    @Test
    void update() {
        Vote updated = voteService.getByUserAndDate(USER_ID, LocalDate.of(2021, Month.MARCH, 25));
        voteService.update(updated, updated.getUser().getId(), RESTAURANT2_ID);
        assertEquals(voteService.get(updated.getId()), updated);
    }

    @Test
    void get() {
        Vote actual = voteService.get(VOTE1_ID);
        assertEquals(actual, VOTE_1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> voteService.get(NOT_FOUND));
    }

    @Test
    void getAllByDate() {
        assertEquals(voteService.getAllByDate(LocalDate.of(2021, Month.MARCH, 25)), List.of(VOTE_1));
    }

    @Test
    void getByUserAndDate() {
        Vote actual = voteService.getByUserAndDate(USER_ID, LocalDate.of(2021, Month.MARCH, 25));
        assertEquals(actual, VOTE_1);
    }
}