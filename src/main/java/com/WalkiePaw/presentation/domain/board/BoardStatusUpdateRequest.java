package com.WalkiePaw.presentation.domain.board;

import com.WalkiePaw.domain.board.entity.BoardStatus;
import lombok.Data;

@Data
public class BoardStatusUpdateRequest {
    private final Integer boardId;
    private final BoardStatus status;
}
