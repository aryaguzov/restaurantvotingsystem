package com.restaurant.votingsystem.service;

import com.restaurant.votingsystem.model.Dish;
import com.restaurant.votingsystem.repository.DishRepository;
import com.restaurant.votingsystem.repository.MenuRepository;
import com.restaurant.votingsystem.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.restaurant.votingsystem.util.ValidationUtil.*;

@Service
@Transactional(readOnly = true)
public class DishService {

    private DishRepository dishRepository;
    private MenuRepository menuRepository;

    @Autowired
    public void setDishRepository(DishRepository dishRepository, MenuRepository menuRepository) {
        this.dishRepository = dishRepository;
        this.menuRepository = menuRepository;
    }

    @CacheEvict(value = "dishes", allEntries = true)
    @Transactional
    public Dish create(Dish dish, Integer menuId) {
        Objects.requireNonNull(dish, "Dish must not be null.");
        return save(dish, menuId);
    }

    @CacheEvict(value = "dishes", allEntries = true)
    @Transactional
    public void delete(Integer dishId, Integer menuId) {
        checkNotFoundWithId(dishRepository.delete(dishId, menuId), dishId);
    }

    public Dish get(Integer dishId, Integer menuId) {
        return dishRepository.findById(dishId)
                .filter(dish -> {
                    assert dish.getMenu().getId() != null;
                    return dish.getMenu().getId().equals(menuId);
                })
                .orElseThrow(() -> new NotFoundException("Not found the dish with id=" + dishId));
    }

    public List<Dish> getAllByDate(LocalDateTime date) {
        Objects.requireNonNull(date, "Date must not be null.");
        return dishRepository.getAllByDate(date);
    }

    @CacheEvict(value = "dishes", allEntries = true)
    @Transactional
    public void update(Dish dish, Integer menuId) {
        Objects.requireNonNull(dish, "Dish must not be null.");
        checkNotFoundWithId(save(dish, menuId), dish.id());
    }

    public List<Dish> getAll(Integer menuId) {
        return dishRepository.getAll(menuId);
    }

    @Transactional
    public Dish save(Dish dish, Integer menuId) {
        if (!dish.isNew() && get(dish.getId(), menuId) == null) {
            return null;
        }
        dish.setMenu(menuRepository.getOne(menuId));
        return dishRepository.save(dish);
    }
}
