package com.restaurant.votingsystem.repository;

import com.restaurant.votingsystem.model.Vote;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class VoteRepository implements CrudVoteRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public Vote save(Vote vote) {
        if (vote.isNew()) {
            entityManager.persist(vote);
            return vote;
        } else {
            return entityManager.merge(vote);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return entityManager.createNamedQuery(Vote.DELETE)
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    @Override
    public Vote get(int id) {
        return entityManager.find(Vote.class, id);
    }

    @Override
    public List<Vote> getAll() {
        return entityManager.createNamedQuery(Vote.GET_ALL_SORTED, Vote.class).getResultList();
    }
}
