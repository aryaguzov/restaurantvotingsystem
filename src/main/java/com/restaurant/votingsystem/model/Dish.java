package com.restaurant.votingsystem.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dishes", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "date"}, name = "unique_name_date_idx")})
public class Dish extends BaseEntity {
    @Column(name = "name", nullable = false)
    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @Column(name = "date", nullable = false, columnDefinition = "LocalDateTime default now()")
    @NotNull
    private LocalDate date;

    @Column(name = "price", nullable = false)
    @NotNull
    private int price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rest_id")
    @NotNull
    private Restaurant restaurant;

    public Dish(Integer id, String name, LocalDate date, int price, Restaurant restaurant) {
        super(id);
        this.name = name;
        this.date = date;
        this.price = price;
        this.restaurant = restaurant;
    }

    public Dish(Dish dish) {
        this(dish.getId(), dish.getName(), dish.getDate(), dish.getPrice(), dish.getRestaurant());
    }
}
