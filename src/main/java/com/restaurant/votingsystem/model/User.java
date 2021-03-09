package com.restaurant.votingsystem.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Set;


@NamedQueries({
        @NamedQuery(name = User.DELETE, query = "DELETE FROM User u WHERE u.id=:id"),
        @NamedQuery(name = User.GET_BY_EMAIL, query = "SELECT u from User u WHERE u.email=:email"),
        @NamedQuery(name = User.GET_ALL_SORTED, query = "SELECT u from User u ORDER BY u.id")
})

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity()
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email", name = "unique_email_idx")})
public class User {
    public static final int START_SEQ = 10000;

    public static final String DELETE = "User.delete";
    public static final String GET_BY_EMAIL = "User.getByEmail";
    public static final String GET_ALL_SORTED = "User.getAllSorted";

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    private Integer id;

    @Column(name = "name", nullable = false)
    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

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

    @Column(name = "registered", nullable = false, columnDefinition = "Timestamp default now()")
    @NotNull
    private Timestamp registered;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "user_roles_unique_idx")})
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "role")
    private Set<Role> roles;

    public boolean isNew() {
        return this.id == null;
    }
}
