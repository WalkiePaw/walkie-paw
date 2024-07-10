package com.WalkiePaw.presentation.domain.chatroom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {
    private Integer id;
    private String title;
    private String memberNickName;
    private LocalDateTime createdDate;
    private boolean hasReview;
}