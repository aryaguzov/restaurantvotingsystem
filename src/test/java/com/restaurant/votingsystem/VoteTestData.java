package com.restaurant.votingsystem;

import com.restaurant.votingsystem.model.Vote;

import java.time.LocalDate;
import java.time.Month;

import static com.restaurant.votingsystem.model.BaseEntity.*;
import static com.restaurant.votingsystem.RestaurantTestData.*;

public class VoteTestData {
    public static final TestMatcher<Vote> VOTE_MATCHER = TestMatcher.usingIgnoringFieldsComparator();
    public static final int VOTE1_ID = START_SEQ + 6;
    public static final int VOTE2_ID = START_SEQ + 7;
    public static final int NOT_FOUND = 1000000;

    public static final Vote vote1 = new Vote(VOTE1_ID, 10, LocalDate.of(2021, Month.MARCH, 15), restaurant1);
    public static final Vote vote2 = new Vote(VOTE2_ID, 15, LocalDate.of(2021, Month.MARCH, 16), restaurant2);

    public static Vote getNew() {
        return new Vote(10, LocalDate.now(), restaurant1);
    }

    public static Vote getUpdated() {
        Vote updated = new Vote(vote1);
        updated.setVote(100);
        updated.setDate(LocalDate.now());
        updated.setRestaurant(restaurant2);
        return updated;
    }

}
