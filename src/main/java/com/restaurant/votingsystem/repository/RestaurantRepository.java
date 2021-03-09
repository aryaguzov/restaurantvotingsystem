package com.restaurant.votingsystem.repository;

import com.restaurant.votingsystem.model.Restaurant;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class RestaurantRepository implements CrudRestaurantRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public Restaurant save(Restaurant restaurant) {
        if (restaurant.isNew()) {
            entityManager.persist(restaurant);
            return restaurant;
        } else {
            return entityManager.merge(restaurant);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return entityManager.createNamedQuery(Restaurant.DELETE)
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    @Override
    public Restaurant get(int id) {
        return entityManager.find(Restaurant.class, id);
    }

    @Override
    public Restaurant getByName(String name) {
        List<Restaurant> restaurants = entityManager.createNamedQuery(Restaurant.GET_BY_NAME, Restaurant.class)
                .setParameter("name", name)
                .getResultList();
        return DataAccessUtils.singleResult(restaurants);
    }

    @Override
    public List<Restaurant> getAll() {
        return entityManager.createNamedQuery(Restaurant.GET_ALL_SORTED).getResultList();
    }
}
