package com.restaurant.votingsystem.repository;

import com.restaurant.votingsystem.model.Vote;
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
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Vote v WHERE v.id=:id")
    int delete(@Param("id") Integer id);

    @Query("SELECT v FROM Vote v WHERE v.date=:date ORDER BY v.date DESC")
    List<Vote> getAllByDate(@Param("date") LocalDate date);

    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId AND v.date=:date")
    Vote getByUserAndDate(@Param("userId") Integer userId, @Param("date") LocalDate date);
}
