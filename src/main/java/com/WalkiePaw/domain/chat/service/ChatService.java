package com.WalkiePaw.domain.chat.service;

import com.WalkiePaw.domain.chat.entity.ChatMessage;
import com.WalkiePaw.domain.chat.repository.ChatMsgRepository;
import com.WalkiePaw.domain.chatroom.entity.Chatroom;
import com.WalkiePaw.domain.chatroom.repository.ChatroomRepository;
import com.WalkiePaw.domain.member.Repository.MemberRepository;
import com.WalkiePaw.domain.member.entity.Member;
import com.WalkiePaw.presentation.domain.chat.dto.ChatAddRequest;
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
    private final ChatroomRepository chatroomRepository;
    private final MemberRepository memberRepository;

    public List<ChatMsgListResponse> findChatsByChatroomId(final Integer chatroomId) {
        List<ChatMessage> chatMessagesList = chatMsgRepository.findByChatroomId(chatroomId);
        return chatMessagesList.stream()
                .map(ChatMsgListResponse::from)
                .toList();
    }

    @Transactional
    public Integer saveChatMsg(final ChatAddRequest request) {
        Chatroom chatroom = chatroomRepository.findById(request.getChatroomId())
                .orElseThrow(() -> new IllegalStateException("잘못된 채팅방 번호입니다."));
        Member member = memberRepository.findById(request.getWriterId())
                .orElseThrow();
        chatroom.updateLatestMessage(request.getContent());
        ChatMessage chatMsg = request.toEntity(request, member, chatroom);
        return chatMsgRepository.save(chatMsg).getId();
    }

    public void bulkUpdateIsRead(final Integer chatroomId) {
        chatMsgRepository.bulkIsRead(chatroomId);
    }
}
