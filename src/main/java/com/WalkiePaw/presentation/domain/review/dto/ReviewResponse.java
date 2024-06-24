package com.WalkiePaw.presentation.domain.review.dto;

import com.WalkiePaw.domain.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewResponse {
    public static ReviewResponse from(final Review review) {
        return new ReviewResponse();
    }
}
