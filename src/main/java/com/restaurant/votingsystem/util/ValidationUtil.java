package com.restaurant.votingsystem.util;

import com.restaurant.votingsystem.model.AbstractEntity;

public class ValidationUtil {

    public static void assureIdConsistent(AbstractEntity entity, int id) {
        if (entity.isNew()) {
            entity.setId(id);
        } else if (entity.id() != id) {
            throw new IllegalArgumentException(entity + " must be with id=" + id);
        }
    }
}
