package com.WalkiePaw.presentation.domain.board.dto;

import lombok.Data;

@Data
public class BoardSearchRequest {
    private final String title;
    private final String content;
}
