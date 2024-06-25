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

    public Integer save(final Review review) {
        em.persist(review);
        return review.getId();
    }

    public List<Review> findByName(final String name) {
        return em.createQuery("select r from Review r where r.name = :name", Review.class)
                .setParameter("name", name)
                .getResultList();
    }

    public Review findById(final Integer id) {
        return em.find(Review.class, id);
    }

    public List<Review> findByRevieweeId(final Integer revieweeId) {
        return em.createQuery("select r from Review r where r.reviewee = :id", Review.class)
                .setParameter("id", revieweeId)
                .getResultList();
    }

}
