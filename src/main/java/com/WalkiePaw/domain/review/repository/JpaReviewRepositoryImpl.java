package com.WalkiePaw.domain.review.repository;

import com.WalkiePaw.domain.review.entity.Review;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Profile("jpa")
public class JpaReviewRepositoryImpl implements ReviewRepository{

    @PersistenceContext
    private final EntityManager em;

    public Review save(final Review review) {
        em.persist(review);
        return review;
    }

//    @Override
    public List<Review> findByReviewerName(final String name) {
        return em.createQuery("select r from Review r where r.reviewer.name = :name", Review.class)
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public Optional<Review> findById(final Integer reviewId) {
        return Optional.ofNullable(em.find(Review.class, reviewId));
    }

    @Override
    public List<Review> findByRevieweeId(final Integer revieweeId) {
        return em.createQuery("select r from Review r where r.reviewee = :id", Review.class)
                .setParameter("id", revieweeId)
                .getResultList();
    }

    @Override
    public List<Review> findByReviewerId(final Integer reviewerId) {
        return List.of();
    }

}
