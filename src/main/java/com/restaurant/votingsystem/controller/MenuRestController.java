package com.restaurant.votingsystem.controller;

import com.restaurant.votingsystem.model.Menu;
import com.restaurant.votingsystem.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static com.restaurant.votingsystem.util.ValidationUtil.assureIdConsistent;
import static com.restaurant.votingsystem.util.ValidationUtil.checkNew;

@Slf4j
@RestController
@RequestMapping(path = MenuRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuRestController {
    public static final String REST_URL = "/api/v1/admin/restaurants";

    private MenuService menuService;

    @Autowired
    public MenuRestController(MenuService menuService) {
        this.menuService = menuService;
    }

    // curl localhost:8081/api/v1/admin/restaurants/{restId}/menus -u admin:password
    @GetMapping("/{restId}/menus")
    public List<Menu> getAll(@PathVariable Integer restId) {
        log.info("Getting all menus for the restaurant with id={}", restId);
        return menuService.getAll(restId);
    }

    // curl localhost:8081/api/v1/admin/restaurants/{restId}/menus/{menuId} -u admin:password
    @GetMapping("/{restId}/menus/{menuId}")
    public Menu get(@PathVariable Integer restId, @PathVariable Integer menuId) {
        log.info("Getting the menu with id={} for the restaurant with id={}", menuId, restId);
        return menuService.get(restId, menuId);
    }

    // curl -X DELETE localhost:8081/api/v1/admin/restaurants/{restId}/menus/{menuId} -u admin:password
    @DeleteMapping("/{restId}/menus/{menuId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer restId, @PathVariable Integer menuId) {
        log.info("Deleting the menu with id={} for the restaurant with id={}", menuId, restId);
        menuService.delete(restId, menuId);
    }

    // curl -X POST localhost:8081/api/v1/admin/restaurants/{restId}/menus -H 'Content-type:application/json' -d '{"date":"2021-03-03","dishes":[{"name": "Pizza","price": 100},{"name": "Bread","price": 10}]}' -u admin:password
    @PostMapping(value = "/{restId}/menus", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> create(@RequestBody Menu menu, @PathVariable Integer restId) {
        log.info("Creating menu={} for the restaurant with id={}", menu, restId);
        checkNew(menu);
        Menu created = menuService.create(menu, restId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{restId}/menus/{menuId}")
                .buildAndExpand(restId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    // curl -X PUT localhost:8081/api/v1/admin/restaurants/{restId}/menus/{menuId} -H 'Content-type:application/json' -d '{"date":"2021-04-04","dishes":[{"name": "NewPizza","price": 1100},{"name": "NewBread","price": 100}]}' -u admin:password
    @PutMapping(value = "/{restId}/menus/{menuId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Menu updated, @PathVariable Integer restId, @PathVariable Integer menuId) {
        log.info("Updating the menu={} with id={} for the restaurant with id={}", updated, menuId, restId);
        assureIdConsistent(updated, menuId);
        menuService.update(updated, restId);
    }

    // curl 'localhost:8081/api/v1/admin/restaurants/menus?date={date}' -u admin:password
    @GetMapping(value = "/menus")
    public List<Menu> getAllByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("Getting all menus filtering by date={}", date);
        return menuService.getAllByDate(date);
    }
}
