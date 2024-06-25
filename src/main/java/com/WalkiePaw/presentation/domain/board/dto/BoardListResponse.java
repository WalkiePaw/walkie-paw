package com.WalkiePaw.presentation.domain.board.dto;

import com.WalkiePaw.domain.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BoardListResponse {
    private final Integer id;
    private final String title;
    private final String content;
    private final String location;
    private final String memberNickName;

    public static BoardListResponse from(final Board board) {
        return new BoardListResponse(board.getId(), board.getTitle(), board.getContent(), board.getLocation(), board.getMember().getNickname());
    }
}
