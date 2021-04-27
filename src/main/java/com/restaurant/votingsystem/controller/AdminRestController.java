package com.restaurant.votingsystem.controller;

import com.restaurant.votingsystem.model.User;
import com.restaurant.votingsystem.service.UserService;
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
@RequestMapping(path = AdminRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestController {
    public static final String REST_URL = "api/v1/admin/users";
    private UserService userService;

    @Autowired
    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    // curl localhost:8081/api/v1/admin/users -u admin:password
    @GetMapping
    public List<User> getAll() {
        log.info("Getting all users");
        return userService.getAll();
    }

    // curl localhost:8081/api/v1/admin/users/{id} -u admin:password
    @GetMapping("/{id}")
    public User get(@PathVariable Integer id) {
        log.info("Getting the user with id={}", id);
        return userService.get(id);
    }

    // curl -X DELETE localhost:8081/api/v1/admin/users/{id} -u admin:password
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        log.info("Deleting the user with id={}", id);
        userService.delete(id);
    }

    // curl 'localhost:8081/api/v1/admin/users/by?name={name}' -u admin:password
    @GetMapping("/by")
    public User getByName(@RequestParam("name") String name) {
        log.info("Getting the user by name={}", name);
        return userService.getByName(name);
    }

    // curl -X POST localhost:8081/api/v1/admin/users -H 'Content-type:application/json' -d '{"name":"{name}","email":"{email}","password":"{password}","roles":["USER"]}' -u admin:password
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> create(@RequestBody User user) {
        log.info("Creating a user={}", user);
        checkNew(user);
        User created = userService.create(user);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    // curl -X PUT localhost:8081/api/v1/admin/users/{id} -H 'Content-type:application/json' -d '{"name":"{newName}","email":"{newEmail}","password":"{newPassword}","roles":["USER"]}' -u admin:password
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody User updated, @PathVariable Integer id) {
        log.info("Updating the user={} with id={}", updated, id);
        assureIdConsistent(updated, id);
        userService.update(updated);
    }
}
