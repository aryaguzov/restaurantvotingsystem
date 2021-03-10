package com.restaurant.votingsystem.repository;

import com.restaurant.votingsystem.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface DishRepository extends JpaRepository<Dish, Integer> {

    @Transactional
    @Query("DELETE FROM Dish d WHERE d.id=:id")
    @Modifying
    boolean delete(@Param("id") int id);

    List<Dish> getAllByDateOrderByDateAsc(LocalDate date);
}
