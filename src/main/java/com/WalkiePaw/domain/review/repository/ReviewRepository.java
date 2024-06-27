package com.WalkiePaw.domain.review.repository;

import com.WalkiePaw.domain.review.entity.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository {

    Review save(Review review);

//    List<Review> findByName(String name);

    Optional<Review> findById(Integer reviewId);

    List<Review> findByRevieweeId(Integer revieweeId);

    List<Review> findByReviewerId(Integer reviewerId);
}
