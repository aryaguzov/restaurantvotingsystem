package com.restaurant.votingsystem.service;

import com.restaurant.votingsystem.model.Dish;
import com.restaurant.votingsystem.model.Menu;
import com.restaurant.votingsystem.repository.DishRepository;
import com.restaurant.votingsystem.repository.MenuRepository;
import com.restaurant.votingsystem.repository.RestaurantRepository;
import com.restaurant.votingsystem.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;

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
                dishRepository.save(dish);
            });
        }
        return created;
    }

    public Menu get(Integer menuId, Integer restId) {
        return menuRepository.findById(menuId)
                .filter(menu -> {
                    assert menu.getRestaurant().getId() != null;
                    return menu.getRestaurant().getId().equals(restId);
                })
                .orElseThrow(() -> new NotFoundException("Not found the menu with id=" + menuId));
    }

    public List<Menu> getAll(Integer restId) {
        return menuRepository.getAll(restId);
    }

    @CacheEvict(value = "menus", allEntries = true)
    @Transactional
    public void delete(Integer menuId, Integer restId) {
        checkNotFoundWithId(menuRepository.delete(menuId, restId), menuId);
    }

    @CacheEvict(value = "menus", allEntries = true)
    @Transactional
    public void update(Menu menu, Integer restId) {
        Objects.requireNonNull(menu, "Menu must not be null.");
        Set<Dish> menuWithOldDishes = get(menu.getId(), restId).getDishes();
        if (menuWithOldDishes != null) {
            menuWithOldDishes.forEach(dish -> dishRepository.delete(dish.getId(), menu.getId()));
        }

        Set<Dish> menuWithNewDishes = menu.getDishes();
        if (menuWithNewDishes != null) {
            menuWithNewDishes.forEach(dish -> {
                dish.setId(null);
                dishRepository.save(dish);
            });
        }
        checkNotFoundWithId(save(menu, restId), menu.id());
    }

    public Menu getWithDishes(Integer id) {
        return checkNotFoundWithId(menuRepository.getWithDishes(id), id);
    }

    @Transactional
    public Menu save(Menu menu, Integer restId) {
        if (!menu.isNew() && get(menu.getId(), restId) == null) {
            return null;
        }
        menu.setRestaurant(restaurantRepository.getOne(restId));
        return menuRepository.save(menu);
    }

}
