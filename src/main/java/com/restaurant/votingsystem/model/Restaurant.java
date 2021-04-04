package com.restaurant.votingsystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "unique_name_idx")})
@ToString(callSuper = true)
public class Restaurant extends AbstractNamedEntity {

    @Column(name = "contacts", nullable = false)
    @NotBlank
    @Size(min = 2, max = 100)
    private String contacts;

    @Column(name = "enabled", nullable = false, columnDefinition = "Default true")
    private boolean enabled;

    @JsonIgnoreProperties("restaurant")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("date DESC")
    private List<Dish> dishes;

    @JsonIgnoreProperties("restaurant")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("date ASC")
    private List<Vote> votes;

    public Restaurant() {
    }

    public Restaurant(Integer id, @NotBlank @Size(min = 2, max = 100) String name, @NotBlank @Size(min = 2, max = 100) String contacts, LocalDateTime date, boolean enabled) {
        super(id, name, date);
        this.contacts = contacts;
        this.enabled = enabled;
    }

    public Restaurant(Restaurant restaurant) {
        this(restaurant.getId(), restaurant.getName(), restaurant.getContacts(), restaurant.getDate(), restaurant.isEnabled());
    }
}
