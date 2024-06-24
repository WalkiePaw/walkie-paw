package com.WalkiePaw.domain.chatroom.service;

import com.WalkiePaw.domain.board.entity.Board;
import com.WalkiePaw.domain.board.repository.BoardRepository;
import com.WalkiePaw.domain.chatroom.entity.Chatroom;
import com.WalkiePaw.domain.chatroom.repository.ChatroomRepository;
import com.WalkiePaw.domain.member.Repository.MemberRepository;
import com.WalkiePaw.domain.member.entity.Member;
import com.WalkiePaw.presentation.domain.chatroom.dto.ChatroomAddRequest;
import com.WalkiePaw.presentation.domain.chatroom.dto.ChatroomListResponse;
import com.WalkiePaw.presentation.domain.chatroom.dto.ChatroomRespnose;
import com.WalkiePaw.presentation.domain.chatroom.dto.ChatroomUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ChatroomService {
    private final ChatroomRepository chatroomRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public List<ChatroomListResponse> findAll() {
        List<Chatroom> chatrooms = chatroomRepository.findAll();
        return chatrooms.stream()
                .map(ChatroomListResponse::from)
                .toList();
    }

    @Transactional
    public Integer saveChatroom(final ChatroomAddRequest request) {
        Board board = boardRepository.findById(request.getBoardId());
        Member member = memberRepository.findById(request.getMemberId());
        Chatroom chatroom = ChatroomAddRequest.toEntity(board, member);
        return chatroomRepository.save(chatroom);
    }

    public ChatroomRespnose findChatroomById(final Integer id) {
        Chatroom chatroom = chatroomRepository.findById(id);
        return ChatroomRespnose.toEntity(chatroom);
    }

    @Transactional
    public void updateChatroom(final Integer id, final ChatroomUpdateRequest request) {
        Chatroom chatroom = chatroomRepository.findById(id);
        chatroom.update();
    }
}
