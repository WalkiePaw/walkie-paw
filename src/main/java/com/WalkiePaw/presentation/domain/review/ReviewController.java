package com.WalkiePaw.presentation.domain.review;

import com.WalkiePaw.domain.review.service.ReviewService;
import com.WalkiePaw.presentation.domain.review.dto.ReviewDetailResponse;
import com.WalkiePaw.presentation.domain.review.dto.ReviewListResponse;
import com.WalkiePaw.presentation.domain.review.dto.ReviewSaveRequest;
import com.WalkiePaw.presentation.domain.review.dto.ReviewUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    public static final String REVIEWS_URI = "/reviews/";
    private final ReviewService reviewService;

    @GetMapping("/{id}/reviews")
    public ResponseEntity<List<ReviewListResponse>> getReviewsByRevieweeId(@PathVariable("id") final Integer revieweeId) {
        List<ReviewListResponse> reviews = reviewService.findByRevieweeId(revieweeId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/{id}/my-reviews")
    public ResponseEntity<List<ReviewListResponse>> getReviewsByReviewerId(@PathVariable("id") final Integer reviewerId) {
        List<ReviewListResponse> reviews = reviewService.findByReviewerId(reviewerId);
        return ResponseEntity.ok(reviews);
    }

    @PostMapping
    public ResponseEntity<Void> saveReview(final @RequestBody ReviewSaveRequest request) {
        Integer id = reviewService.addReview(request);
        return ResponseEntity.created(URI.create(REVIEWS_URI + id)).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDetailResponse> getReview(final @PathVariable Integer id) {
        ReviewDetailResponse response = reviewService.findById(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateReview(final @PathVariable Integer id, final @RequestBody ReviewUpdateRequest request) {
        reviewService.updateReview(id, request);
        return ResponseEntity.noContent().build();
    }
}
