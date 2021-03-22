package com.restaurant.votingsystem.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

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

    @Column(name = "registered", nullable = false, columnDefinition = "LocalDateTime default now()")
    private LocalDateTime registered;

    @Column(name = "enabled", nullable = false, columnDefinition = "Default true")
    private boolean enabled;

    public Restaurant(Integer id, @NotBlank @Size(min = 2, max = 100) String name, @NotBlank @Size(min = 2, max = 100) String contacts, LocalDateTime registered, boolean enabled) {
        super(id);
        this.name = name;
        this.contacts = contacts;
        this.registered = registered;
        this.enabled = enabled;
    }

    public Restaurant(Restaurant restaurant) {
        this(restaurant.getId(), restaurant.getName(), restaurant.getContacts(), restaurant.getRegistered(), restaurant.isEnabled());
    }
}
