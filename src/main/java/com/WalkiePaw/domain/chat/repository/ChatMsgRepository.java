package com.WalkiePaw.domain.chat.repository;

import com.WalkiePaw.domain.chat.chatV1.entity.ChatMessage;

import java.util.List;

public interface ChatMsgRepository {

    List<ChatMessage> findByChatroomId(Integer chatroomId);
}
