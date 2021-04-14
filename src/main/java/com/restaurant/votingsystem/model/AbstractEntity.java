package com.restaurant.votingsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Persistable;
import org.springframework.data.util.ProxyUtils;
import org.springframework.util.Assert;

import javax.persistence.*;

@MappedSuperclass
@Access(AccessType.FIELD)
@Getter
@Setter
@ToString
public abstract class AbstractEntity implements Persistable<Integer> {
    public static final int START_SEQ = 10000;

    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    protected Integer id;

    protected AbstractEntity() {
    }

    public AbstractEntity(Integer id) {
        this.id = id;
    }

    public Integer id() {
        Assert.notNull(id, "Entity must have id.");
        return id;
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !getClass().equals(ProxyUtils.getUserClass(obj))) {
            return false;
        }
        AbstractEntity that = (AbstractEntity) obj;
        return id != null && id.equals(that.id);
    }

    @Override
    @JsonIgnore
    public boolean isNew() {
        return id == null;
    }
}
