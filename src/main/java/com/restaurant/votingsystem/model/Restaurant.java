package com.restaurant.votingsystem.model;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Restaurant {
    private int id;
    private String name;
    private String contacts;
    private Timestamp registered;
    private boolean enabled;
}
