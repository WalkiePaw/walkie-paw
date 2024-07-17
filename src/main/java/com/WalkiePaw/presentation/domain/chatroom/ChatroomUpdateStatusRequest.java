package com.WalkiePaw.presentation.domain.chatroom;

import com.WalkiePaw.domain.board.entity.BoardStatus;
import com.WalkiePaw.domain.chatroom.entity.ChatroomStatus;
import lombok.Data;

@Data
public class ChatroomUpdateStatusRequest {
    private final Integer chatroomId;
    private final BoardStatus status;
}
