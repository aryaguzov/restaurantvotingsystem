package com.restaurant.votingsystem.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "user_roles_unique_idx")})
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "role")
    private Set<Role> roles;

    public User() {
    }

    public User(Integer id) {
        super();
        this.id = id;
    }

    public User(Integer id, String name, String email, String password, Set<Role> roles) {
        super(id, name);
        this.email = email;
        this.password = password;
        setRoles(roles);
    }

    public User(User user) {
        this(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getRoles());
    }
}
