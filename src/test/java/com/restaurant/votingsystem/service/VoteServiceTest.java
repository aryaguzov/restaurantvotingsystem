package com.restaurant.votingsystem.service;

import com.restaurant.votingsystem.model.Vote;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static com.restaurant.votingsystem.VoteTestData.*;

class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    VoteService voteService;

    @Test
    void create() {
        Vote created = voteService.create(getNew());
        int newId = created.id();
        Vote newVote = getNew();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(voteService.get(newId), newVote);
    }

    @Test
    void delete() {
        voteService.delete(VOTE1_ID);
        assertThrows(NoSuchElementException.class, () -> voteService.get(VOTE1_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(EmptyResultDataAccessException.class, () -> voteService.delete(NOT_FOUND));
    }

    @Test
    void get() {
        Vote vote = voteService.get(VOTE1_ID);
        VOTE_MATCHER.assertMatch(vote, vote1);
    }


    @Test
    void update() {
        Vote updated = getUpdated();
        voteService.update(updated);
        VOTE_MATCHER.assertMatch(voteService.get(VOTE1_ID), getUpdated());
    }

    @Test
    void getAll() {
        List<Vote> all = voteService.getAll();
        VOTE_MATCHER.assertMatch(all, vote1, vote2);
    }
}