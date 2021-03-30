package com.restaurant.votingsystem.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "dishes", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "date"}, name = "unique_name_date_idx")})
@ToString(callSuper = true)
public class Dish extends AbstractNamedEntity {

    @Column(name = "price", nullable = false, columnDefinition = "int default 0")
    @NotNull
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rest_id")
    @NotNull
    private Restaurant restaurant;

    public Dish() {
    }

    public Dish(Integer id, String name, LocalDateTime date, Integer price, Restaurant restaurant) {
        super(id, name, date);
        this.price = price;
        this.restaurant = restaurant;
    }

    public Dish(Dish dish) {
        this(dish.getId(), dish.getName(), dish.getDate(), dish.getPrice(), dish.getRestaurant());
    }
}
