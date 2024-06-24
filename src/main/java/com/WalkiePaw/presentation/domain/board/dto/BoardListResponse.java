package com.WalkiePaw.presentation.domain.board.dto;

import com.WalkiePaw.domain.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BoardListResponse {
    private String title;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime lastModifiedDate;
    private String memberEmail;

    public static BoardListResponse from(final Board board) {
        return new BoardListResponse(board.getTitle(), board.getContent(), board.getCreatedDate(), board.getModifiedDate(), board.getMember().getEmail());
    }
}
