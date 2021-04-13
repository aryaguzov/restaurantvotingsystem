package com.restaurant.votingsystem.controller;

import com.restaurant.votingsystem.model.Dish;
import com.restaurant.votingsystem.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "rest/dishes", produces = MediaType.APPLICATION_JSON_VALUE)
public class DishRestController {
    private DishService service;

    @Autowired
    public DishRestController(DishService service) {
        this.service = service;
    }

    // curl localhost:8081/rest/dishes
    @GetMapping
    public List<Dish> getAll() {
        log.info("Getting all the dishes");
        return service.getAll();
    }

    // curl localhost:8081/rest/dishes/{id}
    @GetMapping("/{id}")
    public Dish get(@PathVariable Integer id) {
        log.info("Getting the dish with id={}", id);
        return service.get(id);
    }

    // curl localhost:8081/rest/dishes/filter?
    @GetMapping("/filter")
    @ResponseStatus(HttpStatus.OK)
    public List<Dish> getAllByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        log.info("Getting all dishes filtered by date");
        return service.getAllByDate(date);
    }
}
