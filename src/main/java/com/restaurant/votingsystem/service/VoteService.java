package com.restaurant.votingsystem.service;

import com.restaurant.votingsystem.model.Vote;
import com.restaurant.votingsystem.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
public class VoteService {

    private VoteRepository repository;

    @Autowired
    public void setRepository(VoteRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(value = "votes", allEntries = true)
    @Transactional
    public Vote create(Vote vote) {
        Objects.requireNonNull(vote, "Vote must not be null.");
        return repository.save(vote);
    }

    @CacheEvict(value = "vote", allEntries = true)
    @Transactional
    public void delete(int id) {
        repository.deleteById(id);
    }

    public Vote get(int id) {
        return repository.findById(id).orElseThrow();
    }

    @CacheEvict(value = "votes", allEntries = true)
    @Transactional
    public void update(Vote vote) {
        Assert.notNull(vote, "Vote must not be null.");
        repository.save(vote);
    }

    public List<Vote> getAll() {
        return repository.findAll();
    }
}
