package com.WalkiePaw.presentation.domain.chatroom.dto;

import com.WalkiePaw.domain.chatroom.entity.Chatroom;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatroomListResponse {
    private Integer id;
    private String location;
    private String nickname;
    private String latestMessage;
    private LocalTime latestTime;
    private int unreadCount;

    public static ChatroomListResponse from(final Chatroom chatroom) {
        return new ChatroomListResponse(chatroom.getId(), chatroom.getBoard().getLocation(),
                 chatroom.getMember().getNickname(), chatroom.getLatestMessage(), chatroom.getModifiedDate().toLocalTime(), chatroom.getUnreadCount());
    }
}
