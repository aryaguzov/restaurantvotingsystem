package com.restaurant.votingsystem.repository;

import com.restaurant.votingsystem.model.Dish;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class DishRepository implements CrudDishRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public Dish save(Dish dish) {
        if (dish.isNew()) {
            entityManager.persist(dish);
            return dish;
        } else {
            return entityManager.merge(dish);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return entityManager.createNamedQuery(Dish.DELETE)
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    @Override
    public Dish get(int id) {
        return entityManager.find(Dish.class, id);
    }

    @Override
    public Dish getByName(String name) {
        List<Dish> dishes = entityManager.createNamedQuery(Dish.GET_BY_NAME, Dish.class)
                .setParameter("name", name)
                .getResultList();
        return DataAccessUtils.singleResult(dishes);
    }

    @Override
    public List<Dish> getAllByDate(LocalDate date) {
        return entityManager.createNamedQuery(Dish.GET_BY_DATE, Dish.class)
                .setParameter("date", date)
                .getResultList();
    }

    @Override
    public List<Dish> getAll() {
        return entityManager.createNamedQuery(Dish.GET_ALL_SORTED, Dish.class)
                .getResultList();
    }
}
