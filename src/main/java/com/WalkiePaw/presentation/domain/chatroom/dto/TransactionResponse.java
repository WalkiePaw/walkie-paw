package com.WalkiePaw.presentation.domain.chatroom.dto;

import com.WalkiePaw.domain.board.entity.BoardCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TransactionResponse {
    private Integer chatroomId;
    private String title;
    private String memberNickName;
    private LocalDateTime createdDate;
    private boolean hasReview;  // 추가
    private BoardCategory category;

    public TransactionResponse(
            final Integer chatroomId, final String title, final String memberNickName,
            final LocalDateTime createdDate, final boolean hasReview, final BoardCategory category) {
        this.chatroomId = chatroomId;
        this.title = title;
        this.memberNickName = memberNickName;
        this.createdDate = createdDate;
        this.hasReview = hasReview;
        this.category = category;
    }
}
