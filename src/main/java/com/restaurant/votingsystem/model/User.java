package com.restaurant.votingsystem.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.Set;

@Entity()
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email", name = "unique_email_idx")})
@Setter
@Getter
@ToString(callSuper = true, exclude = {"password"})
public class User extends AbstractNamedEntity {

    @Column(name = "email", nullable = false)
    @NotBlank
    @Email
    @Size(max = 100)
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank
    @Size(min = 3, max = 100)
    private String password;

    @Column(name = "enabled", nullable = false, columnDefinition = "Default true")
    private boolean enabled;

    @Column(name = "last_vote")
    @NotNull
    private LocalDate lastVote;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "user_roles_unique_idx")})
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "role")
    private Set<Role> roles;

    public User() {
    }

    public User(Integer id, String name, String email, String password, LocalDateTime date, Role role) {
        super(id, name, date);
        this.email = email;
        this.password = password;
        this.enabled = true;
        this.roles = EnumSet.of(role);
    }

    public User(User user) {
        this(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.isEnabled(), user.getDate(), user.getLastVote(), user.getRoles());
    }

    public User(Integer id, String name, String email, String password, boolean enabled, LocalDateTime date, LocalDate lastVote, Set<Role> roles) {
        super(id, name, date);
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.lastVote = lastVote;
        setRoles(roles);
    }
}
