package com.WalkiePaw.presentation.domain.board.dto;

import lombok.Data;

@Data
public class BoardLikeAddRequest {
    private final Integer boardId;
    private final Integer memberId;
}
