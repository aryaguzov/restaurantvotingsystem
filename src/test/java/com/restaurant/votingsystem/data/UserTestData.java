package com.restaurant.votingsystem.data;

import com.restaurant.votingsystem.model.Role;
import com.restaurant.votingsystem.model.User;

import java.util.Collections;

import static com.restaurant.votingsystem.model.AbstractEntity.START_SEQ;

public class UserTestData {
    public static final TestMatcher<User> USER_MATCHER = TestMatcher.usingIgnoringFieldsComparator("roles", "password");
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int USER1_ID = START_SEQ + 2;
    public static final int USER2_ID = START_SEQ + 3;
    public static final int NOT_FOUND = 10;

    public static final User USER = new User(USER_ID, "user", "user@gmail.com", "password", Collections.singleton(Role.USER));
    public static final User ADMIN = new User(ADMIN_ID, "admin", "admin@gmail.com", "password", Collections.singleton(Role.ADMIN));
    public static final User USER1 = new User(USER1_ID, "user1", "user@yahoo.com", "password", Collections.singleton(Role.USER));
    public static final User USER2 = new User(USER2_ID, "user2", "user@facebook.com", "password", Collections.singleton(Role.USER));

    public static User getNew() {
        return new User(null, "Name", "name@gmail.com", "newPassword", Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        User updated = new User(USER);
        updated.setName("updatedName");
        updated.setEmail("updated@gmail.com");
        updated.setPassword("updatedPassword");
        updated.setRoles(Collections.singleton(Role.ADMIN));
        return updated;
    }
}
