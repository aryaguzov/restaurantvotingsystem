package com.restaurant.votingsystem.controller;

import com.restaurant.votingsystem.model.Restaurant;
import com.restaurant.votingsystem.service.RestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<List> getAll() {
        List<Restaurant> all = service.getAll();
        log.info("getAll");
        if (!all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> get(@PathVariable Integer id) {
        log.info("get {}", id);
        return new ResponseEntity<>(service.get(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurant create(@RequestBody Restaurant restaurant) {
        log.info("create {}", restaurant);
        return service.create(restaurant);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Restaurant update(@PathVariable Integer id, @RequestBody Restaurant updated) {
        log.info("update {} with id={}", updated, id);
        Restaurant restaurant = service.get(id);
        if (updated.getName() != null) {
            restaurant.setName(updated.getName());
        }
        if (updated.getContacts() != null) {
            restaurant.setContacts(updated.getContacts());
        }
        if (updated.getRegistered() != null) {
            restaurant.setRegistered(updated.getRegistered());
        }
        if (updated.isEnabled()) {
            restaurant.setEnabled(true);
        }
        return service.create(restaurant);
    }
}
