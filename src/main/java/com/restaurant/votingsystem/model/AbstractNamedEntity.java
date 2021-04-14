package com.restaurant.votingsystem.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@MappedSuperclass
@Getter
@Setter
@ToString
public abstract class AbstractNamedEntity extends AbstractEntity {

    @Column(name = "name", nullable = false)
    @NotBlank
    @NonNull
    @Size(min = 2, max = 100)
    protected String name;

    protected AbstractNamedEntity() {
    }

    protected AbstractNamedEntity(Integer id, LocalDate date) {
        super(id, date);
    }

    protected AbstractNamedEntity(Integer id, String name) {
        super(id);
        this.name = name;
    }

    protected AbstractNamedEntity(Integer id, String name, LocalDate date) {
        super(id, date);
        this.name = name;
    }
}
