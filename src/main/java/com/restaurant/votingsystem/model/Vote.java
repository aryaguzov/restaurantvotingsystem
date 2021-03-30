package com.restaurant.votingsystem.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"rest_id", "vote", "date"}, name = "unique_rest_id_vote_date_idx")})
@ToString(callSuper = true)
public class Vote extends AbstractEntity {
    @Column(name = "vote", nullable = false)
    @NotNull
    private int vote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rest_id")
    @NotNull
    private Restaurant restaurant;

    public Vote() {
    }

    public Vote(Integer id, int vote, LocalDateTime date, Restaurant restaurant) {
        super(id, date);
        this.vote = vote;
        this.restaurant = restaurant;
    }

    public Vote(Vote vote) {
        this(vote.getId(), vote.getVote(), vote.getDate(), vote.getRestaurant());
    }
}
