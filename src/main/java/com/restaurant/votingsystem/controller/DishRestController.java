package com.restaurant.votingsystem.controller;

import com.restaurant.votingsystem.model.Dish;
import com.restaurant.votingsystem.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "rest/dishes", produces = MediaType.APPLICATION_JSON_VALUE)
public class DishRestController {

    DishService service;

    @Autowired
    public DishRestController(DishService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List> getAll() {
        List<Dish> all = service.getAll();
        log.info("getAll");
        if (!all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Dish> get(@PathVariable Integer id) {
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
    public Dish create(@RequestBody Dish dish) {
        log.info("create {}", dish);
        return service.create(dish);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Dish update(@PathVariable Integer id, @RequestBody Dish updated) {
        log.info("update {} with id={}", updated, id);
        Dish dish = service.get(id);
        if (updated.getName() != null) {
            dish.setName(updated.getName());
        }
        if (updated.getDate() != null) {
            dish.setDate(updated.getDate());
        }
        if (updated.getPrice() != null) {
            dish.setPrice(updated.getPrice());
        }
        if (updated.getRestaurant() != null) {
            dish.setPrice(updated.getPrice());
        }
        return service.create(dish);
    }

    @GetMapping("/filter")
    @ResponseStatus(HttpStatus.OK)
    public List<Dish> getAllByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("get all by date");
        return service.getAllByDate(date);
    }
}
