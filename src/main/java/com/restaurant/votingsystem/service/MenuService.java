package com.restaurant.votingsystem.service;

import com.restaurant.votingsystem.model.Dish;
import com.restaurant.votingsystem.model.Menu;
import com.restaurant.votingsystem.repository.DishRepository;
import com.restaurant.votingsystem.repository.MenuRepository;
import com.restaurant.votingsystem.repository.RestaurantRepository;
import com.restaurant.votingsystem.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.restaurant.votingsystem.util.ValidationUtil.checkNotFound;
import static com.restaurant.votingsystem.util.ValidationUtil.checkNotFoundWithId;

@Service
@Transactional(readOnly = true)
public class MenuService {

    private MenuRepository menuRepository;
    private RestaurantRepository restaurantRepository;
    private DishRepository dishRepository;

    @Autowired
    public MenuService(MenuRepository repository, RestaurantRepository restaurantRepository, DishRepository dishRepository) {
        this.menuRepository = repository;
        this.restaurantRepository = restaurantRepository;
        this.dishRepository = dishRepository;
    }

    @CacheEvict(value = "menus", allEntries = true)
    @Transactional
    public Menu create(Menu menu, Integer restId) {
        Objects.requireNonNull(menu, "Menu must not be null.");
        Menu created = save(menu, restId);

        Set<Dish> menuWithDishes = created.getDishes();
        if (menuWithDishes != null) {
            menuWithDishes.forEach(dish -> {
                dish.setId(null);
                dish.setMenu(menu);
                dishRepository.save(dish);
            });
        }
        return created;
    }

    public Menu get(Integer restId, Integer menuId) {
        return menuRepository.findById(menuId)
                .filter(menu -> {
                    assert menu.getRestaurant().getId() != null;
                    return menu.getRestaurant().getId().equals(restId);
                })
                .orElseThrow(() -> new NotFoundException("Not found the menu with id=" + menuId));
    }

    @Cacheable("menus")
    public List<Menu> getAll(Integer restId) {
        return menuRepository.getAll(restId);
    }

    @CacheEvict(value = "menus", allEntries = true)
    @Transactional
    public void delete(Integer restId, Integer menuId) {
        if (!menuRepository.existsById(menuId)) {
            throw new NotFoundException("Not found the menu with id=" + menuId);
        }
        menuRepository.delete(restId, menuId);
    }

    @CachePut(value = "menus")
    @Transactional
    public void update(Menu menu, Integer restId) {
        Objects.requireNonNull(menu, "Menu must not be null.");
        Set<Dish> menuWithOldDishes = get(restId, menu.getId()).getDishes();
        if (menuWithOldDishes != null) {
            menuWithOldDishes.forEach(dish -> dishRepository.delete(dish.getId(), menu.getId()));
        }

        Set<Dish> menuWithNewDishes = menu.getDishes();
        if (menuWithNewDishes != null) {
            menuWithNewDishes.forEach(dish -> {
                dish.setId(null);
                dish.setMenu(menu);
                dishRepository.save(dish);
            });
        }
        checkNotFoundWithId(save(menu, restId), menu.id());
    }

    public List<Menu> getAllByDate(LocalDate date) {
        return checkNotFound(menuRepository.getAllByDate(date),
                String.format("date=%s", date.toString()));
    }

    private Menu save(Menu menu, Integer restId) {
        if (!menu.isNew() && get(restId, menu.getId()) == null) {
            return null;
        }
        menu.setRestaurant(restaurantRepository.getOne(restId));
        return menuRepository.save(menu);
    }

}
