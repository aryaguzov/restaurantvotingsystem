package com.restaurant.votingsystem.service;

import com.restaurant.votingsystem.model.Vote;
import com.restaurant.votingsystem.repository.RestaurantRepository;
import com.restaurant.votingsystem.repository.UserRepository;
import com.restaurant.votingsystem.repository.VoteRepository;
import com.restaurant.votingsystem.util.exception.NotFoundException;
import com.restaurant.votingsystem.util.exception.TimeOverException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import static com.restaurant.votingsystem.util.ValidationUtil.checkNotFound;

@Service
@Transactional(readOnly = true)
public class VoteService {

    private VoteRepository voteRepository;
    private UserRepository userRepository;
    private RestaurantRepository restaurantRepository;

    @Autowired
    public VoteService(VoteRepository voteRepository, UserRepository userRepository, RestaurantRepository restaurantRepository) {
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @CacheEvict(value = "votes", allEntries = true)
    @Transactional
    public Vote create(Vote vote, Integer userId, Integer restId) {
        Objects.requireNonNull(vote, "Vote must not be null.");
        if (LocalTime.now().isAfter(LocalTime.of(23, 0))) {
            throw new TimeOverException("Sorry, it's too late to vote today.");
        }
        return save(vote, userId, restId);
    }

    @CacheEvict(value = "vote", allEntries = true)
    @Transactional
    public void delete(Integer id) {
        if (!voteRepository.existsById(id)) {
            throw new NotFoundException("Not found the vote with id=" + id);
        }
        voteRepository.delete(id);
    }

    @CachePut(value = "votes")
    @Transactional
    public Vote update(Vote vote, Integer userId, Integer restId) {
        Objects.requireNonNull(vote, "Vote must not be null.");
        Vote existingVote = getByUserAndDate(userId, vote.getDate());
        Objects.requireNonNull(existingVote, "Existing vote must not be null.");
        if (LocalTime.now().isAfter(LocalTime.of(11, 0))) {
            throw new TimeOverException("Sorry, too late to change your mind.");
        }
        return save(existingVote, userId, restId);
    }

    public Vote get(Integer id) {
        return voteRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found the vote with id=" + id));
    }

    public List<Vote> getAllByDate(LocalDate date) {
        return checkNotFound(voteRepository.getAllByDate(date),
                String.format("date=%s", date.toString()));
    }

    public Vote getByUserAndDate(Integer userId, LocalDate date) {
        return voteRepository.getByUserAndDate(userId, date);
    }

    @Transactional
    public Vote save(Vote vote, Integer userId, Integer restId) {
        if (!vote.isNew() && get(vote.id()) == null) {
            return null;
        }
        vote.setUser(userRepository.getOne(userId));
        vote.setRestaurant(restaurantRepository.getOne(restId));
        return voteRepository.save(vote);
    }
}
