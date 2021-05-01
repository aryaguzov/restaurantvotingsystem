package com.restaurant.votingsystem.data;

import com.restaurant.votingsystem.model.Vote;

import java.time.LocalDate;
import java.time.Month;

import static com.restaurant.votingsystem.data.RestaurantTestData.*;
import static com.restaurant.votingsystem.data.UserTestData.*;
import static com.restaurant.votingsystem.model.AbstractEntity.START_SEQ;

public class VoteTestData {
    public static final int VOTE1_ID = START_SEQ + 29;
    public static final int VOTE2_ID = START_SEQ + 30;
    public static final int VOTE3_ID = START_SEQ + 31;
    public static final int VOTE4_ID = START_SEQ + 32;
    public static final int NOT_FOUND = 1000000;

    public static final Vote VOTE_1 = new Vote(VOTE1_ID, LocalDate.of(2021, Month.MARCH, 25), USER, RESTAURANT_1);
    public static final Vote VOTE_2 = new Vote(VOTE2_ID, LocalDate.of(2021, Month.MARCH, 26), ADMIN, RESTAURANT_2);
    public static final Vote VOTE_3 = new Vote(VOTE3_ID, LocalDate.of(2021, Month.MARCH, 26), USER1, RESTAURANT_3);
    public static final Vote VOTE_4 = new Vote(VOTE4_ID, LocalDate.of(2021, Month.MARCH, 26), USER2, RESTAURANT_4);

    public static Vote getNew() {
        return new Vote(null, LocalDate.now(), USER, RESTAURANT_1);
    }
}
