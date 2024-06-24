package com.WalkiePaw.domain.chat.service;

import com.WalkiePaw.domain.chat.chatV1.entity.ChatMessage;
import com.WalkiePaw.domain.chat.repository.ChatMsgRepository;
import com.WalkiePaw.presentation.domain.chat.dto.ChatMsgListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ChatService {

    private final ChatMsgRepository chatMsgRepository;

    public List<ChatMsgListResponse> findChatByChatroomId(final Integer chatroomId) {
        List<ChatMessage> chatMessagesList = chatMsgRepository.findByChatroomId(chatroomId);
        return chatMessagesList.stream()
                .map(ChatMsgListResponse::from)
                .toList();
    }
}
