package com.restaurant.votingsystem.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.restaurant.votingsystem.controller.json.JsonDeserializers;
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

    @Column(name = "date", nullable = false, columnDefinition = "LocalDate default now()")
    private LocalDate date;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    @JsonProperty("userId")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Vote.class)
    @JsonIdentityReference(alwaysAsId = true)
    @JsonDeserialize(using = JsonDeserializers.UserJsonDeserializer.class)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rest_id", nullable = false)
    @NotNull
    @JsonProperty("restId")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Vote.class)
    @JsonIdentityReference(alwaysAsId = true)
    @JsonDeserialize(using = JsonDeserializers.RestaurantJsonDeserializer.class)
    private Restaurant restaurant;

    public Vote() {
    }

    public Vote(Integer id, LocalDate date, User user, Restaurant restaurant) {
        super(id);
        this.date = date;
        this.user = user;
        this.restaurant = restaurant;
    }

    public Vote(Vote vote) {
        this(vote.getId(), vote.getDate(), vote.getUser(), vote.getRestaurant());
    }
}
