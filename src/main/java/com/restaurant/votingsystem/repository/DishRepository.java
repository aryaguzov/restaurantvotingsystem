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

    @Query("SELECT d FROM Dish d WHERE d.menu.date=:date")
    List<Dish> getAllByDate(LocalDate date);

    @Transactional
    @Modifying
    @Query("DELETE FROM Dish d WHERE d.id=:id AND d.menu.id=:menuId")
    int delete(@Param("id") Integer id, @Param("menuId") Integer menuId);

    @Query("SELECT d FROM Dish d WHERE d.menu.id=:menuId ORDER BY d.menu.date DESC")
    List<Dish> getAll(@Param("menuId") Integer menuId);
}
