package com.restaurant.votingsystem.controller;

import com.restaurant.votingsystem.AuthorizedUser;
import com.restaurant.votingsystem.model.User;
import com.restaurant.votingsystem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.restaurant.votingsystem.util.ValidationUtil.*;


@Slf4j
@RestController
@RequestMapping(path = ProfileRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileRestController {
    public static final String REST_URL = "api/v1/profile";
    private UserService userService;

    @Autowired
    public ProfileRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public User get(@AuthenticationPrincipal AuthorizedUser authorizedUser) {
        log.info("Getting the user with id={}", authorizedUser.getId());
        return userService.get(authorizedUser.getId());
    }

    @DeleteMapping
    public void delete(@AuthenticationPrincipal AuthorizedUser authorizedUser) {
        log.info("Deleting the user with id={}", authorizedUser.getId());
        userService.delete(authorizedUser.getId());
    }

    @PutMapping
    public void update(@RequestBody User updated, @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        assureIdConsistent(updated, authorizedUser.getId());
        log.info("Updating the user={} with id={}", updated, authorizedUser.getId());
        updated.setId(authorizedUser.getId());
        userService.update(updated);
    }
}
