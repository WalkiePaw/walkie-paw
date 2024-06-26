package com.WalkiePaw.presentation.domain.chatroom.dto;

import com.WalkiePaw.domain.board.entity.Board;
import com.WalkiePaw.domain.chatroom.entity.Chatroom;
import com.WalkiePaw.domain.chatroom.entity.ChatroomCategory;
import com.WalkiePaw.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatroomAddRequest {

    private final Integer boardId;
    private final Integer memberId;
    private final ChatroomCategory category;

    public static Chatroom toEntity(
            final Board board, final Member member, final ChatroomAddRequest request
            ) {
        return new Chatroom(board, member, request.category);
    }
}
