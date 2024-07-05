package com.WalkiePaw.presentation.domain.chatroom.dto;

import com.WalkiePaw.domain.chatroom.entity.Chatroom;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatroomListResponse {
    private Integer id;
    private String location;
    private String nickname;
    private String latestMessage;
    private LocalTime latestTime;
    private int unreadCount;

    public ChatroomListResponse(Integer id, String location, String nickname, String latestMessage, LocalDateTime modifiedDate, int unreadCount) {
        this.id = id;
        this.location = location;
        this.nickname = nickname;
        this.latestMessage = latestMessage;
        this.latestTime = modifiedDate.toLocalTime();
        this.unreadCount = unreadCount;
    }

    public static ChatroomListResponse from(final Chatroom chatroom) {
        return new ChatroomListResponse(chatroom.getId(), chatroom.getBoard().getLocation(),
                 chatroom.getMember().getNickname(), chatroom.getLatestMessage(), chatroom.getModifiedDate(), chatroom.getUnreadCount());
    }
}
