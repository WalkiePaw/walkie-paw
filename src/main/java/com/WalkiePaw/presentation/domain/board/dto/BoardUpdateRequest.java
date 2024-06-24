package com.WalkiePaw.presentation.domain.board.dto;

import com.WalkiePaw.domain.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BoardUpdateRequest {

    /**
     * Board Entity 생성 메서드
     * @return Board Entity
     */
    public static Board toEntity() {
        return Board.builder().build();
    }
}
