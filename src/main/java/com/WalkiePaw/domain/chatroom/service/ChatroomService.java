package com.WalkiePaw.domain.chatroom.service;

import com.WalkiePaw.domain.board.entity.Board;
import com.WalkiePaw.domain.board.entity.BoardStatus;
import com.WalkiePaw.domain.board.repository.BoardRepository;
import com.WalkiePaw.domain.chatroom.entity.Chatroom;
import com.WalkiePaw.domain.chatroom.entity.ChatroomStatus;
import com.WalkiePaw.domain.chatroom.repository.ChatroomRepository;
import com.WalkiePaw.global.exception.BadRequestException;
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

import static com.WalkiePaw.global.exception.ExceptionCode.INVALID_REQUEST;
import static com.WalkiePaw.global.exception.ExceptionCode.NOT_FOUND_CHATROOM_ID;

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

    public ChatroomRespnose findChatroomById(final Integer memberId, final Integer boardId) {
        Chatroom chatroom = chatroomRepository.findByMemberIdAndBoardId(memberId, boardId)
                .orElseGet(() ->
                        chatroomRepository.findByWriterIdAndBoardId(memberId, boardId)
                                .orElseThrow(() -> new BadRequestException(NOT_FOUND_CHATROOM_ID)));
        return ChatroomRespnose.toEntity(chatroom);
    }

    public Page<TransactionResponse> findTransaction(final Integer memberId, final Pageable pageable) {
        return chatroomRepository.findTransaction(memberId, pageable);
    }

    @Transactional
    public void updateChatroomStatus(
            final Integer chatroomId,
            final BoardStatus status
    ) {
        Chatroom chatroom = chatroomRepository.findChatroomAndBoardById(chatroomId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_CHATROOM_ID));
        chatroom.getBoard().updateStatus(status);
        chatroom.updateStatus(ChatroomStatus.valueOf(String.valueOf(status)));
    }
}
