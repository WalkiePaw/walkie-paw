package com.WalkiePaw.presentation.domain.chat.dto;

import com.WalkiePaw.domain.chat.entity.ChatMessage;
import com.WalkiePaw.domain.chatroom.entity.Chatroom;
import com.WalkiePaw.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatAddRequest {
    private final Integer writerId;
    private final String content;

    public ChatMessage toEntity(final ChatAddRequest request, final Member member, final Chatroom chatroom) {
        return new ChatMessage(chatroom, member, request.content);
    }
}
