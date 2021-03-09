package com.restaurant.votingsystem.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@NamedQueries({
        @NamedQuery(name = Vote.DELETE, query = "DELETE FROM Vote v WHERE v.id=:id"),
        @NamedQuery(name = Vote.GET_ALL_SORTED, query = "SELECT v FROM Vote v ORDER BY v.date")
})

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"rest_id", "vote", "date"}, name = "unique_rest_id_vote_date_idx")})
public class Vote {

    public static final String DELETE = "Vote.delete";
    public static final String GET_ALL_SORTED = "Vote.getAllSorted";

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = User.START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    private Integer id;

    @Column(name = "vote", nullable = false)
    @NotNull
    private int vote;

    @Column(name = "date", nullable = false, columnDefinition = "Timestamp default now()")
    @NotNull
    private Timestamp date;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rest_id")
    @NotNull
    private Restaurant restaurant;

    public boolean isNew() {
        return this.id == null;
    }
}
