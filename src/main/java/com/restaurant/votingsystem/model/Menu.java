package com.restaurant.votingsystem.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "menus", uniqueConstraints = {@UniqueConstraint(columnNames = {"rest_id", "date"}, name = "rest_date_idx")})
@EqualsAndHashCode
@ToString(callSuper = true)
public class Menu extends AbstractEntity {

    @Column(name = "date", nullable = false, columnDefinition = "LocalDate default now()")
    private LocalDate date;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "rest_id", nullable = false)
    private Restaurant restaurant;

    @NotNull
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "menu")
    private Set<Dish> dishes;

    public Menu() {
    }

    public Menu(Integer id, LocalDate date, Restaurant restaurant, Set<Dish> dishes) {
        super(id);
        this.date = date;
        this.restaurant = restaurant;
        this.dishes = dishes;
    }

    public Menu(Menu menu) {
        this(menu.getId(), menu.getDate(), menu.getRestaurant(), menu.getDishes());
    }
}
