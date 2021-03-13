package com.restaurant.votingsystem.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "unique_name_idx")})
public class Restaurant extends BaseEntity{
    @Column(name = "name", nullable = false)
    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @Column(name = "contacts", nullable = false)
    @NotBlank
    @Size(min = 2, max = 100)
    private String contacts;

    @Column(name = "registered", nullable = false, columnDefinition = "Timestamp default now()")
    @NotNull
    private Timestamp registered;

    @Column(name = "enabled", nullable = false, columnDefinition = "Default true")
    private boolean enabled;
}
