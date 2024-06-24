package com.WalkiePaw.presentation.domain.review.dto;

import com.WalkiePaw.domain.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewListResponse {

    public static ReviewListResponse from(Review review) {
        return new ReviewListResponse();
    }
}
