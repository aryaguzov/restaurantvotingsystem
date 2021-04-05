package com.restaurant.votingsystem.controller;

import com.restaurant.votingsystem.model.Restaurant;
import com.restaurant.votingsystem.service.RestaurantService;
import com.restaurant.votingsystem.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "rest/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantRestController {

    RestaurantService service;

    @Autowired
    public RestaurantRestController(RestaurantService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Restaurant>> getAll() {
        log.info("Getting all the restaurants");
        List<Restaurant> all = service.getAll();
        if (all.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> get(@PathVariable Integer id) {
        log.info("Getting the restaurant with id={}", id);
        Restaurant restaurant = service.get(id);
        if (restaurant == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        log.info("Deleting the restaurant with id={}", id);
        Restaurant restaurant = service.get(id);
        if (restaurant == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> create(@RequestBody Restaurant restaurant) {
        log.info("Creating a restaurant {}", restaurant);
        Restaurant created = service.create(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("rest/restaurants" + "{/id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> update(@PathVariable Integer id, @RequestBody Restaurant updated) {
        log.info("Updating the restaurant={} with id={}", updated, id);
        ValidationUtil.assureIdConsistent(updated, id);
        service.create(updated);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @GetMapping("{id}/dishes")
    public ResponseEntity<Restaurant> getWithDishes(@PathVariable Integer id) {
        log.info("Getting the restaurant with id={} and all the dishes", id);
        Restaurant restaurant = service.getWithDishes(id);
        if (restaurant == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
}
