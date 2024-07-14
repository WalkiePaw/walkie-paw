package com.WalkiePaw.presentation.domain.board.dto;

import lombok.Data;

@Data
public class BoardLikeRequest {
    private final Integer boardId;
    private final Integer loginUserId;
}
