package com.WalkiePaw.presentation.domain.chatroom.dto;

import com.WalkiePaw.domain.chatroom.entity.Chatroom;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatroomRespnose {
    private final Integer chatroomId;
    public static ChatroomRespnose toEntity(final Chatroom chatroom) {
        return new ChatroomRespnose(chatroom.getId());
    }
}
