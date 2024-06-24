package com.WalkiePaw.presentation.domain.review;

import com.WalkiePaw.domain.review.service.ReviewService;
import com.WalkiePaw.presentation.domain.review.dto.ReviewListResponse;
import com.WalkiePaw.presentation.domain.review.dto.ReviewResponse;
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


    //api/v1/{domain}-> get : 목록
    //api/v1/{domain} -> post : 등록
    //api/v1/{domain}/{id} -> get : 조회
    //api/v1/{domain}/{id} -> patch  : 수정
    //api/v1/{domain}/{id} -> delete : 삭제

    @GetMapping
    public ResponseEntity<List<ReviewListResponse>> getReviews() {
        List<ReviewListResponse> reviews = reviewService.findAll();
        return ResponseEntity.ok(reviews);
    }

    @PostMapping
    public ResponseEntity<Void> saveReview(ReviewSaveRequest request) {
        Integer id = reviewService.addReview(request);
        return ResponseEntity.created(URI.create(REVIEWS_URI + id)).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponse> getReview(@PathVariable Integer id) {
        ReviewResponse response = reviewService.findById(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateReview(@PathVariable Integer id, ReviewUpdateRequest request) {
        reviewService.updateReview(id, request);
        return ResponseEntity.noContent().build();
    }
}
