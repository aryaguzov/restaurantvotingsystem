package com.restaurant.votingsystem.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Vote {
    private int id;
    private int vote;
    private Timestamp date;
    private Restaurant restaurant;
}
