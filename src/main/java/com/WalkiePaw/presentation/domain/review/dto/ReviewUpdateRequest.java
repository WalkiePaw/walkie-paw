package com.WalkiePaw.presentation.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewUpdateRequest {
    private final String content;
    private final int point;

}
