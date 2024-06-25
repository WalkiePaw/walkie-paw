package com.WalkiePaw.presentation.domain.board.dto;

import com.WalkiePaw.domain.board.entity.Board;
import com.WalkiePaw.domain.board.entity.BoardStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoardGetResponse {

    private final Integer id;
    private final BoardStatus status;
    /**
     * TODO - Member 정보 뭐 넘길지
     */
    private final String location;
    private final String title;
    private final String content;

    /**
     * BoardGetResponse 생성 메서드
     * @param board Entity
     * @return BoardGetResponse
     */
    public static BoardGetResponse from(final Board board) {
        return new BoardGetResponse(board.getId(), board.getStatus(), board.getLocation(), board.getTitle(), board.getContent());
    }

}
