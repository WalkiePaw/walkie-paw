package com.WalkiePaw.domain.review.repository;

import com.WalkiePaw.domain.board.entity.BoardCategory;
import com.WalkiePaw.domain.review.entity.Review;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Profile("spring-data-jpa")
public interface ReviewRepository extends JpaRepository<Review, Integer>, ReviewRepositoryOverride {
    List<Review> findByRevieweeIdAndCategory(Pageable pageable, Integer revieweeId, BoardCategory category);

    List<Review> findByReviewerIdAndCategory(Pageable pageable, Integer reviewerId, BoardCategory category);
}
