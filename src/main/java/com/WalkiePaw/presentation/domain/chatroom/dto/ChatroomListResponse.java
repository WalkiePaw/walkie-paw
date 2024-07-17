package com.WalkiePaw.presentation.domain.chatroom.dto;

import com.WalkiePaw.domain.board.entity.BoardCategory;
import com.WalkiePaw.domain.board.entity.BoardStatus;
import com.WalkiePaw.domain.chatroom.entity.Chatroom;
import com.WalkiePaw.domain.chatroom.entity.ChatroomStatus;
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
    private LocalDateTime latestTime;
    private int unreadCount;
    private String boardTitle;
    private String memberPhoto;
    private BoardStatus boardStatus;
    private boolean isTransactionCompleted;
    private boolean isBoardWriter;
    private BoardCategory category;
    private Integer memberId;

    public ChatroomListResponse(
        Integer id, String location, String nickname, String latestMessage, LocalDateTime modifiedDate,
        int unreadCount, String boardTitle, String memberPhoto, BoardStatus boardStatus, boolean isTransactionCompleted
        , boolean isBoardWriter, BoardCategory category, Integer memberId
    ) {
        this.id = id;
        this.location = location;
        this.nickname = nickname;
        this.latestMessage = latestMessage;
        this.latestTime = modifiedDate;
        this.unreadCount = unreadCount;
        this.boardTitle = boardTitle;
        this.memberPhoto = memberPhoto;
        this.boardStatus = boardStatus;
        this.isTransactionCompleted = isTransactionCompleted;
        this.isBoardWriter = isBoardWriter;
        this.category = category;
        this.memberId = memberId;
    }

    public ChatroomListResponse(
            Integer id, String location, String nickname, String latestMessage, LocalDateTime modifiedDate, int unreadCount, String boardTitle, String memberPhoto
    ) {
        this.id = id;
        this.location = location;
        this.nickname = nickname;
        this.latestMessage = latestMessage;
        this.latestTime = modifiedDate;
        this.unreadCount = unreadCount;
        this.boardTitle = boardTitle;
        this.memberPhoto = memberPhoto;
    }

    public static ChatroomListResponse from(final Chatroom chatroom) {
        return new ChatroomListResponse(chatroom.getId(), chatroom.getBoard().getLocation(),
                 chatroom.getMember().getNickname(), chatroom.getLatestMessage(), chatroom.getModifiedDate(), chatroom.getUnreadCount(), chatroom.getBoard()
            .getTitle(), chatroom.getMember().getPhoto());
    }
}
