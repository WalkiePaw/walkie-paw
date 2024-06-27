package com.WalkiePaw.presentation.domain.chat.dto;

import com.WalkiePaw.domain.chat.entity.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatMsgListResponse {

    public static ChatMsgListResponse from(ChatMessage chatMessage) {
        return new ChatMsgListResponse();
    }
}
