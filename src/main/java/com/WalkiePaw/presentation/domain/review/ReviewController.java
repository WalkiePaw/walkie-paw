package com.WalkiePaw.presentation.domain.review;

import com.WalkiePaw.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<ReviewListResponse>> reviewList() {
        List<ReviewListResponse> reviews = reviewService.listReview();
        return ResponseEntity.ok(reviews);
    }

//    @PostMapping
//    public ResponseEntity<Void> add() {
//    }
}
