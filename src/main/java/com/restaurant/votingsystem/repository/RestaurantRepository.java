package com.restaurant.votingsystem.repository;

import com.restaurant.votingsystem.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Transactional
    @Query("DELETE FROM Restaurant r WHERE r.id=:id")
    @Modifying
    boolean delete(@Param("id") int id);

    Restaurant findByName(String name);
}
