package com.restaurant.votingsystem;

import com.restaurant.votingsystem.model.Role;
import com.restaurant.votingsystem.model.User;

import java.time.LocalDateTime;
import java.util.Collections;

import static com.restaurant.votingsystem.model.BaseEntity.START_SEQ;

public class UserTestData {
    public static final TestMatcher<User> USER_MATCHER = TestMatcher.usingIgnoringFieldsComparator("registered", "roles");
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int NOT_FOUND = 10;

    public static final User USER = new User(USER_ID, "User", "user@gmail.com", "password", Role.USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "password", Role.ADMIN);

    public static User getNew() {
        return new User("Name", "name@gmail.com", "newPassword", true, LocalDateTime.now(), Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        User updated = new User(USER);
        updated.setName("updatedName");
        updated.setEmail("updated@gmail.com");
        updated.setPassword("updatedPassword");
        updated.setEnabled(true);
        updated.setRoles(Collections.singleton(Role.ADMIN));
        return updated;
    }
}
