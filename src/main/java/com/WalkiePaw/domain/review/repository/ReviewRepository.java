package com.WalkiePaw.domain.review.repository;

import com.WalkiePaw.domain.review.entity.Review;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(Review review) {
        em.persist(review);
    }

    public List<Review> findByName(String name) {
        return em.createQuery("select r from Review r where r.name = :name", Review.class)
                .setParameter("name", name)
                .getResultList();
    }

    public List<Review> findAll() {
        return em.createQuery("select r from Review r", Review.class)
                .getResultList();
    }
}
