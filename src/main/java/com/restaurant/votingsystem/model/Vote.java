package com.restaurant.votingsystem.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"rest_id", "vote", "date"}, name = "unique_rest_id_vote_date_idx")})
public class Vote extends BaseEntity {
    @Column(name = "vote", nullable = false)
    @NotNull
    private int vote;

    @Column(name = "date", nullable = false, columnDefinition = "Timestamp default now()")
    @NotNull
    private LocalDate date;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rest_id")
    @NotNull
    private Restaurant restaurant;

    public Vote(Integer id, int vote, LocalDate date, Restaurant restaurant) {
        super(id);
        this.vote = vote;
        this.date = date;
        this.restaurant = restaurant;
    }

    public Vote(Vote vote) {
        this(vote.getId(), vote.getVote(), vote.getDate(), vote.getRestaurant());
    }
}
