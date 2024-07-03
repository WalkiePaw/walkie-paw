package com.WalkiePaw.domain.chatroom.service;

import com.WalkiePaw.domain.board.entity.Board;
import com.WalkiePaw.domain.board.repository.BoardRepository;
import com.WalkiePaw.domain.board.repository.BoardRepositoryOverride;
import com.WalkiePaw.domain.chatroom.entity.Chatroom;
import com.WalkiePaw.domain.chatroom.repository.ChatroomRepository;
import com.WalkiePaw.domain.member.Repository.MemberRepository;
import com.WalkiePaw.domain.member.entity.Member;
import com.WalkiePaw.presentation.domain.chatroom.dto.ChatroomAddRequest;
import com.WalkiePaw.presentation.domain.chatroom.dto.ChatroomListResponse;
import com.WalkiePaw.presentation.domain.chatroom.dto.ChatroomRespnose;
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

    public List<ChatroomListResponse> findAllByMemberId(final Integer memberId) {
        List<Chatroom> chatrooms = chatroomRepository.findAllByMemberId(memberId);
        return chatrooms.stream()
                .map(ChatroomListResponse::from)
                .toList();
    }

    @Transactional
    public Integer saveChatroom(final ChatroomAddRequest request) {
        Board board = boardRepository.findById(request.getBoardId())
                .orElseThrow(() -> new IllegalStateException("잘못된 게시글 번호입니다."));
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new IllegalStateException("잘못된 회원 번호입니다."));
        Chatroom chatroom = ChatroomAddRequest.toEntity(board, member, request);
        return chatroomRepository.save(chatroom).getId();
    }

    public ChatroomRespnose findChatroomById(final Integer chatroomId) {
        Chatroom chatroom = chatroomRepository.findById(chatroomId)
                .orElseThrow(() -> new IllegalStateException("잘못된 채팅방 번호입니다."));
        return ChatroomRespnose.toEntity(chatroom);
    }
}
