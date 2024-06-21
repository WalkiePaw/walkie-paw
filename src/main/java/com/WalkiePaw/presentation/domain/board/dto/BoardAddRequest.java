package com.WalkiePaw.presentation.domain.board.dto;

import com.WalkiePaw.domain.board.entity.Board;
import lombok.Data;

@Data
public class BoardAddRequest {


    /**
     * BoardAddResponse -> Board 객체로 만드는 toEntity 메서드 필요
     */
    public static Board toEntity() {
        return Board.builder().build();
    }
}
