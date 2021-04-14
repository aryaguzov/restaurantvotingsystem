package com.restaurant.votingsystem.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"rest_id", "date"}, name = "votes_unique_user_date_idx")})
@ToString(callSuper = true)
public class Vote extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rest_id", nullable = false)
    @NotNull
    private Restaurant restaurant;

    public Vote() {
    }

    public Vote(Integer id, LocalDate date, User user, Restaurant restaurant) {
        super(id, date);
        this.user = user;
        this.restaurant = restaurant;
    }
}
