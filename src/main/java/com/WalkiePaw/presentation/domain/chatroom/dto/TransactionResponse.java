package com.WalkiePaw.presentation.domain.chatroom.dto;

import com.WalkiePaw.domain.board.entity.BoardCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {
    private Integer chatroomId;
    private String title;
    private String memberNickName;
    private LocalDateTime createdDate;
    private boolean hasReview;  // 추가
    private BoardCategory category;
}
