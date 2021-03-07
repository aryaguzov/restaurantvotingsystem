package com.restaurant.votingsystem.model;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Dish {
    private int id;
    private String name;
    private Timestamp date;
    private int price;
    private Restaurant restaurant;
}
