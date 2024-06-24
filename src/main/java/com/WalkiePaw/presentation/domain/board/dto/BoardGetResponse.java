package com.WalkiePaw.presentation.domain.board.dto;

import com.WalkiePaw.domain.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoardGetResponse {

    /**
     * BoardGetResponse 생성 메서드
     * @param board Entity
     * @return BoardGetResponse
     */
    public static BoardGetResponse from(final Board board) {
        return new BoardGetResponse();
    }

}
