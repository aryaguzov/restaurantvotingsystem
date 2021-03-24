package com.restaurant.votingsystem.controller;

import com.restaurant.votingsystem.model.Restaurant;
import com.restaurant.votingsystem.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/restaurants", produces = "application/json")
public class RestaurantRestController {

    RestaurantService service;

    @Autowired
    public RestaurantRestController(RestaurantService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List> getAll() {
        List<Restaurant> all = service.getAll();
        if (!all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Restaurant> getById(@PathVariable("id") Integer id) {
        Restaurant restaurant = service.get(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Integer id) {
        service.delete(id);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurant create(@RequestBody Restaurant restaurant) {
        return service.create(restaurant);
    }

    @PatchMapping(path = "{id}", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Restaurant edit(@PathVariable("id") Integer id, @RequestBody Restaurant patch) {
        Restaurant restaurant = service.get(id);
        if (patch.getName() != null) {
            restaurant.setName(patch.getName());
        }
        if (patch.getContacts() != null) {
            restaurant.setContacts(patch.getContacts());
        }
        if (patch.getRegistered() != null) {
            restaurant.setRegistered(patch.getRegistered());
        }
        return service.create(restaurant);
    }
}
