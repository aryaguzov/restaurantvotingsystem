package com.restaurant.votingsystem.controller;

import com.restaurant.votingsystem.model.Dish;
import com.restaurant.votingsystem.model.Restaurant;
import com.restaurant.votingsystem.model.User;
import com.restaurant.votingsystem.service.DishService;
import com.restaurant.votingsystem.service.RestaurantService;
import com.restaurant.votingsystem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

import static com.restaurant.votingsystem.util.ValidationUtil.*;

@Slf4j
@RestController
@RequestMapping(path = "rest/admin", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestController {

    private UserService userService;
    private RestaurantService restaurantService;
    private DishService dishService;

    @Autowired
    public AdminRestController(UserService userService, RestaurantService restaurantService, DishService dishService) {
        this.userService = userService;
        this.restaurantService = restaurantService;
        this.dishService = dishService;
    }

    // curl localhost:8081/rest/admin/users
    @GetMapping("/users")
    public List<User> getAllUsers() {
        log.info("Getting all users");
        return userService.getAll();
    }

    // curl localhost:8081/rest/admin/users/{id}
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Integer id) {
        log.info("Getting the user with id={}", id);
        return userService.get(id);
    }

    // curl -X DELETE localhost:8081/rest/admin/users/{id}
    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Integer id) {
        log.info("Deleting the user with id={}", id);
        userService.delete(id);
    }

    // curl -X POST localhost:8081/rest/admin/users -H 'Content-type:application/json' -d '{"date":"{date}","name":"{name}","email":"{email}","password":"{password}","enabled":true,"lastVote":"{lastVote}","roles":["USER"]}'
    @PostMapping(path = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUser(@RequestBody User user) {
        log.info("Creating a user={}", user);
        checkNew(user);
        User created = userService.create(user);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("rest/admin/users/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    // curl -X PUT localhost:8081/rest/admin/users/{id} -H 'Content-type:application/json' -d '{"date":"{newDate}","name":"{newName}","email":"{newEmail}","password":"{newPassword}","enabled":true,"lastVote":"{newLastVote}","roles":["USER"]}'
    @PutMapping(path = "/users/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@RequestBody User updated, @PathVariable Integer id) {
        log.info("Updating the user={} with id={}", updated, id);
        assureIdConsistent(updated, id);
        userService.update(updated);
    }

    // curl localhost:8081/rest/admin/restaurants
    @GetMapping("/restaurants")
    public List<Restaurant> getAllRestaurants() {
        log.info("Getting all restaurants");
        return restaurantService.getAll();
    }

    // curl localhost:8081/rest/admin/restaurants/{id}
    @GetMapping("/restaurants/{id}")
    public Restaurant getRestaurant(@PathVariable Integer id) {
        log.info("Getting the restaurant with id={}", id);
        return restaurantService.get(id);
    }

    // curl -X DELETE localhost:8081/rest/admin/restaurants/{id}
    @DeleteMapping("/restaurants/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRestaurant(@PathVariable Integer id) {
        log.info("Deleting the restaurant with id={}", id);
        restaurantService.delete(id);
    }

    // curl -X POST localhost:8081/rest/admin/restaurants -H 'Content-type:application/json' -d '{"date":"{date}","name":"{name}","contacts":"{contacts}"}'
    @PostMapping(path = "/restaurants", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant) {
        log.info("Creating a restaurant {}", restaurant);
        checkNew(restaurant);
        Restaurant created = restaurantService.create(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("rest/admin/restaurants/{/id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    // curl -X PUT localhost:8081/rest/admin/restaurants/{id} -H 'Content-type:application/json' -d '{"date":"{date}","name":"{name}","contacts":"{contacts}"}'
    @PutMapping(path = "/restaurants/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRestaurant(@RequestBody Restaurant updated, @PathVariable Integer id) {
        log.info("Updating the restaurant={} with id={}", updated, id);
        assureIdConsistent(updated, id);
        restaurantService.update(updated);
    }

    // curl localhost:8081/rest/admin/restaurants/{id}/dishes
    @GetMapping("/restaurants/{id}/dishes")
    public Restaurant getRestaurantWithDishes(@PathVariable Integer id) {
        log.info("Getting the restaurant with id={} and all dishes", id);
        return restaurantService.getWithDishes(id);
    }

    // curl localhost:8081/rest/admin/dishes
    @GetMapping("/dishes")
    public List<Dish> getAllDishes() {
        log.info("Getting all dishes");
        return dishService.getAll();
    }

    // curl localhost:8081/rest/admin/dishes/{id}
    @GetMapping("/dishes/{id}")
    public Dish getDish(@PathVariable Integer id) {
        log.info("Getting the dish with id={}", id);
        return dishService.get(id);
    }

    // curl -X DELETE localhost:8081/rest/admin/dishes/{id}
    @DeleteMapping("/dishes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDish(@PathVariable Integer id) {
        log.info("Deleting the dish with id={}", id);
        dishService.delete(id);
    }

    // curl -X POST localhost:8081/rest/admin/dishes -H 'Content-type:application/json' -d '{"date":"{date}","name":"{name}","price":"{price}","restaurant":{"id":"{id}"}}'
    @PostMapping(path = "/dishes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createDish(@RequestBody Dish dish) {
        log.info("Creating a dish {}", dish);
        checkNew(dish);
        Dish created = dishService.create(dish);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("rest/admin/dish/{/id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    // curl -X PUT localhost:8081/rest/admin/dishes/{id} -H 'Content-type:application/json' -d '{"date":"{date}","name":"{name}","price":"{price}","restaurant":{"id":"{id}"}}'
    @PutMapping(path = "/dishes/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateDish(@RequestBody Dish updated, @PathVariable Integer id) {
        log.info("Updating the dish={} with id={}", updated, id);
        assureIdConsistent(updated, id);
        dishService.update(updated);
    }

    // curl "localhost:8081/rest/admin/dishes/filter?date=2021-03-30T00:00:00"
    @GetMapping("/dishes/filter")
    public List<Dish> getAllByDate(@RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime date) {
        log.info("Getting all dishes by date={}", date);
        return dishService.getAllByDate(date);
    }
}
