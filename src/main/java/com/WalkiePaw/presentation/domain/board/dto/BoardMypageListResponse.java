package com.WalkiePaw.presentation.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardMypageListResponse {
    private Integer boardId;
    private String title;
    private String content;
    private LocalDateTime createdDate;
}
