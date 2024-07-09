package com.WalkiePaw.domain.chatroom.service;

import com.WalkiePaw.domain.board.entity.Board;
import com.WalkiePaw.domain.board.repository.BoardRepository;
import com.WalkiePaw.domain.chatroom.entity.Chatroom;
import com.WalkiePaw.domain.chatroom.repository.ChatroomRepository;
import com.WalkiePaw.presentation.domain.chatroom.dto.TransactionResponse;
import com.WalkiePaw.domain.member.Repository.MemberRepository;
import com.WalkiePaw.domain.member.entity.Member;
import com.WalkiePaw.presentation.domain.chatroom.dto.ChatroomAddRequest;
import com.WalkiePaw.presentation.domain.chatroom.dto.ChatroomListResponse;
import com.WalkiePaw.presentation.domain.chatroom.dto.ChatroomRespnose;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ChatroomService {
    private final ChatroomRepository chatroomRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public Slice<ChatroomListResponse> findAllByMemberId(final Integer memberId, Pageable pageable) {
        return chatroomRepository.findByMemberId(memberId, pageable);
    }

    @Transactional
    public Integer saveChatroom(final ChatroomAddRequest request) {
        Board board = boardRepository.findById(request.getBoardId())
                .orElseThrow(() -> new IllegalStateException("잘못된 게시글 번호입니다."));
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new IllegalStateException("잘못된 회원 번호입니다."));
        Chatroom chatroom = ChatroomAddRequest.toEntity(board, member);
        return chatroomRepository.save(chatroom).getId();
    }

    public ChatroomRespnose findChatroomById(final Integer chatroomId) {
        Chatroom chatroom = chatroomRepository.findById(chatroomId)
                .orElseThrow(() -> new IllegalStateException("잘못된 채팅방 번호입니다."));
        return ChatroomRespnose.toEntity(chatroom);
    }

    public Page<TransactionResponse> findTransaction(final Integer memberId, final Pageable pageable) {
        return chatroomRepository.findTransaction(memberId, pageable);
    }
}
