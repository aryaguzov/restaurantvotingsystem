package com.restaurant.votingsystem.controller;

import com.restaurant.votingsystem.model.Restaurant;
import com.restaurant.votingsystem.service.RestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "rest/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantRestController {
    private final RestaurantService service;

    @Autowired
    public RestaurantRestController(RestaurantService service) {
        this.service = service;
    }

    // curl localhost:8081/rest/restaurants
    @GetMapping()
    public List<Restaurant> getAll() {
        log.info("Getting all restaurants");
        return service.getAll();
    }

    // curl localhost:8081/rest/restaurants/{id}
    @GetMapping("/{id}")
    public Restaurant get(@PathVariable Integer id) {
        log.info("Getting the restaurant with id={}", id);
        return service.get(id);
    }

    // curl "localhost:8081/rest/dishes/filter?date=2021-03-30T00:00:00"
    @GetMapping("{id}/dishes")
    public Restaurant getWithDishes(@PathVariable Integer id) {
        log.info("Getting the restaurant with id={} and all the dishes", id);
        return service.getWithDishes(id);
    }
}
