package com.WalkiePaw.presentation.domain.chatroom.dto;

import com.WalkiePaw.domain.board.entity.Board;
import com.WalkiePaw.domain.chatroom.entity.Chatroom;
import com.WalkiePaw.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatroomAddRequest {

    private Integer boardId;
    private Integer memberId;

    public static Chatroom toEntity(
            final Board board, final Member member
    ) {
        return new Chatroom(board, member);
    }
}
