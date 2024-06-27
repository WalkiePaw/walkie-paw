package com.WalkiePaw.presentation.domain.review.dto;

import com.WalkiePaw.domain.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewListResponse {
    private final Integer id;
    private final String content;
    private final int point;
    private final String memberName;


    public static ReviewListResponse from(Review review) {
        return new ReviewListResponse(review.getId(), review.getContent(), review.getPoint(), review.getReviewer().getNickname());
    }

}
