package com.restaurant.votingsystem.controller;

import com.restaurant.votingsystem.AuthorizedUser;
import com.restaurant.votingsystem.model.Restaurant;
import com.restaurant.votingsystem.model.Vote;
import com.restaurant.votingsystem.service.RestaurantService;
import com.restaurant.votingsystem.service.VoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = UserVoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserVoteRestController {
    public static final String REST_URL = "/api/v1";

    private VoteService voteService;
    private RestaurantService restaurantService;

    @Autowired
    public UserVoteRestController(VoteService voteService, RestaurantService restaurantService) {
        this.voteService = voteService;
        this.restaurantService = restaurantService;
    }

    // curl localhost:8081/api/v1/restaurants -u user:password
    @GetMapping(path = "/restaurants")
    public List<Restaurant> getRestaurantsWithMenus() {
        log.info("Getting all restaurants with menus");
        return restaurantService.getRestaurantsWithMenus();
    }

    // curl 'localhost:8081/api/v1/votes/by?date={date}' -u user:password
    @GetMapping(path = "/votes/by")
    public List<Vote> getVotesByDay(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info(("Getting all votes by date={}"), date);
        return voteService.getAllByDate(date);
    }

    // curl -X POST localhost:8081/api/v1/restaurants/10005/votes -H 'Content-type:application/json' -d '{"users":[{"id":10000}],"restaurants:[{"id":10005}]}' -u user:password
    @PostMapping(value = "/restaurants/{restId}/votes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> create(@PathVariable Integer restId, @AuthenticationPrincipal AuthorizedUser authUser) {
        Vote vote = new Vote(null, LocalDate.now(), null, null);
        log.info("Creating a new vote={}", vote);
        Vote created = voteService.create(vote, authUser.getId(), restId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "restaurants/{restId}/votes/{voteId}")
                .buildAndExpand(restId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
