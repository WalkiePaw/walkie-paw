package com.WalkiePaw.presentation.domain.chatroom.dto;

import com.WalkiePaw.domain.chatroom.entity.Chatroom;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatroomListResponse {
    public static ChatroomListResponse from(final Chatroom chatroom) {
        return new ChatroomListResponse();
    }
}
