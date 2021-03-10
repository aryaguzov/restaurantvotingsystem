package com.restaurant.votingsystem.repository;

import com.restaurant.votingsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer> {

    @Transactional
    @Query("DELETE FROM User u WHERE u.id=:id")
    @Modifying
    boolean delete(@Param("id") int id);

    User getByEmail(String email);
}
