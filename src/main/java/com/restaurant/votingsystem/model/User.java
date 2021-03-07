package com.restaurant.votingsystem.model;

import lombok.*;

import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private boolean enabled;
    private Timestamp registered;
    private Set<Role> roles;
}
