package com.restaurant.votingsystem.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "dishes")
@ToString(callSuper = true)
public class Dish extends AbstractNamedEntity {

    @Column(name = "price", nullable = false, columnDefinition = "int default 0")
    @NotNull
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "menu_id", nullable = false)
    @NotNull
    private Menu menu;

    public Dish() {
    }

    public Dish(Integer id, String name, Integer price, Menu menu) {
        super(id, name);
        this.price = price;
        this.menu = menu;
    }
}
