package com.WalkiePaw.domain.review.repository;

import com.WalkiePaw.domain.review.entity.Review;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@Profile("spring-data-jpa")
public interface SpringDataReviewRepository extends JpaRepository<Review, Integer>, ReviewRepository{
}
