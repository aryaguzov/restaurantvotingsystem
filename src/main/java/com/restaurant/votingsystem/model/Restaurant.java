package com.restaurant.votingsystem.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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

    public Restaurant() {
    }

    public Restaurant(Integer id, String name, String contacts) {
        super(id, name);
        this.contacts = contacts;
    }
}
