package com.restaurant.votingsystem.controller;

import com.restaurant.votingsystem.AuthorizedUser;
import com.restaurant.votingsystem.model.Restaurant;
import com.restaurant.votingsystem.model.Vote;
import com.restaurant.votingsystem.service.RestaurantService;
import com.restaurant.votingsystem.service.VoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

import static com.restaurant.votingsystem.util.ValidationUtil.assureIdConsistent;

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
    public List<Vote> getVotesByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info(("Getting all votes by date={}"), date);
        return voteService.getAllByDate(date);
    }

    // curl -X POST localhost:8081/api/v1/restaurants/10005/votes -H 'Content-type:application/json' -u user:password
    @PostMapping(value = "/restaurants/{restId}/votes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> create(@Valid @PathVariable Integer restId, @AuthenticationPrincipal AuthorizedUser authUser) {
        Vote vote = new Vote(null, LocalDate.now(), null, null);
        log.info("Creating a new vote={}", vote);
        Vote created = voteService.create(vote, authUser.getId(), restId);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // curl -X PUT localhost:8081/api/v1/restaurants/{newRestaurant}/votes/{voteId} -H 'Content-type:application/json' -d '{"date":"2021-05-04","userId":10000}' -u user:password
    @PutMapping(value = "restaurants/{restId}/votes/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@Valid @RequestBody Vote updated, @PathVariable Integer restId, @PathVariable Integer id) {
        log.info("Updating the vote={} with id={}", updated, id);
        assureIdConsistent(updated, id);
        voteService.update(updated, updated.getUser().getId(), restId);
    }
}
