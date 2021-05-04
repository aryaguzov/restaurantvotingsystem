package com.restaurant.votingsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@Entity
@EqualsAndHashCode
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "unique_name_idx")})
@ToString(callSuper = true)
public class Restaurant extends AbstractNamedEntity {

    @Column(name = "contacts", nullable = false)
    @NotBlank
    @Size(min = 2, max = 100)
    private String contacts;

    @JsonIgnoreProperties("restaurant")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("date DESC")
    private List<Menu> menus;

    public Restaurant() {
    }

    public Restaurant(Integer id) {
        super();
        this.id = id;
    }

    public Restaurant(Integer id, String name, String contacts) {
        super(id, name);
        this.contacts = contacts;
    }

    public Restaurant(Integer id, String name, String contacts, List<Menu> menus) {
        super(id, name);
        this.contacts = contacts;
        this.menus = menus;
    }

    public Restaurant(Restaurant restaurant) {
        this(restaurant.getId(), restaurant.getName(), restaurant.getContacts());
    }
}
