package com.restaurant.votingsystem.controller;

import com.restaurant.votingsystem.model.Restaurant;
import com.restaurant.votingsystem.service.RestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.restaurant.votingsystem.util.ValidationUtil.assureIdConsistent;
import static com.restaurant.votingsystem.util.ValidationUtil.checkNew;

@Slf4j
@RestController
@RequestMapping(path = RestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantRestController {
    public static final String REST_URL = "/api/v1/admin/restaurants";
    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantRestController(RestaurantService service) {
        this.restaurantService = service;
    }

    // curl localhost:8081/api/v1/admin/restaurants
    @GetMapping
    public List<Restaurant> getAll() {
        log.info("Getting all restaurants");
        return restaurantService.getAll();
    }

    // curl localhost:8081/api/v1/admin/restaurants/{id}
    @GetMapping("/{id}")
    public Restaurant get(@PathVariable Integer id) {
        log.info("Getting the restaurant with id={}", id);
        return restaurantService.get(id);
    }

    // curl -X DELETE localhost:8081/api/v1/admin/restaurants/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        log.info("Deleting the restaurant with id={}", id);
        restaurantService.delete(id);
    }

    // curl -X POST localhost:8081/api/v1/admin/restaurants -H 'Content-type:application/json' -d '{"date":"{date}","name":"{name}","contacts":"{contacts}"}'
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> create(@RequestBody Restaurant restaurant) {
        log.info("Creating a restaurant {}", restaurant);
        checkNew(restaurant);
        Restaurant created = restaurantService.create(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    // curl -X PUT localhost:8081/api/v1/admin/restaurants/{id} -H 'Content-type:application/json' -d '{"date":"{date}","name":"{name}","contacts":"{contacts}"}'
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Restaurant updated, @PathVariable Integer id) {
        log.info("Updating the restaurant={} with id={}", updated, id);
        assureIdConsistent(updated, id);
        restaurantService.update(updated);
    }
}
