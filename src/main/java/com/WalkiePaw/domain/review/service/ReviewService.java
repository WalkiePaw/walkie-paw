package com.WalkiePaw.domain.review.service;

import com.WalkiePaw.domain.review.entity.Review;
import com.WalkiePaw.domain.review.repository.ReviewRepository;
import com.WalkiePaw.presentation.domain.review.ReviewListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public List<ReviewListResponse> listReview() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream()
                .map(ReviewListResponse::from)
                .toList();
    }
}
