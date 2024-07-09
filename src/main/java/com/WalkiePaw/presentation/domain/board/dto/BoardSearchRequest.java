package com.WalkiePaw.presentation.domain.board.dto;

import com.WalkiePaw.domain.board.entity.BoardCategory;
import lombok.Data;

@Data
public class BoardSearchRequest {
    private final String title;
    private final String content;
    private final BoardCategory category;
}
