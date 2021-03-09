package com.restaurant.votingsystem.repository;

import com.restaurant.votingsystem.model.Vote;

import java.util.List;

public interface CrudVoteRepository {
    Vote save(Vote vote);

    boolean delete(int id);

    Vote get(int id);

    List<Vote> getAll();
}
