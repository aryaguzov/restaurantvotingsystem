package com.restaurant.votingsystem;

import com.restaurant.votingsystem.model.User;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.Serial;

import static java.util.Objects.requireNonNull;

@Getter
@ToString
public class AuthorizedUser extends org.springframework.security.core.userdetails.User {

    @Serial
    private static final long serialVersionUID = 1L;

    private com.restaurant.votingsystem.model.User user;

    public AuthorizedUser(com.restaurant.votingsystem.model.User user) {
        super(user.getEmail(), user.getPassword(), true, true, true, true, user.getRoles());
        this.user = user;
    }

    public int getId() {
        return user.id();
    }

    public static int authUserId() {
        return get().getUser().id();
    }

    public void update(User updated) {
        user = updated;
    }

    public static AuthorizedUser safeGet() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Object principal = auth.getPrincipal();
        return (principal instanceof AuthorizedUser) ? (AuthorizedUser) principal : null;
    }

    public static AuthorizedUser get() {
        return requireNonNull(safeGet(), "No authorized user found");
    }
}
