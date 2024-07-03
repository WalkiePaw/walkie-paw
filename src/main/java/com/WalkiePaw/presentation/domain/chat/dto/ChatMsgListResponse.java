package com.WalkiePaw.presentation.domain.chat.dto;

import com.WalkiePaw.domain.chat.entity.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ChatMsgListResponse {

    private final String nickname;
    private final String content;
    private final LocalDateTime createDate;

    public static ChatMsgListResponse from(ChatMessage chatMessage) {
        return new ChatMsgListResponse(chatMessage.getWriter().getNickname(), chatMessage.getContent(), chatMessage.getCreatedDate());
    }
}
