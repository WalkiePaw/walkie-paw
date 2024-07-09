package com.WalkiePaw.domain.review.repository;

import com.WalkiePaw.domain.review.entity.Review;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Profile("spring-data-jpa")
public interface ReviewRepository extends JpaRepository<Review, Integer>, ReviewRepositoryOverride {
    List<Review> findByRevieweeId(Integer revieweeId);

    List<Review> findByReviewerId(Integer reviewerId);
}
