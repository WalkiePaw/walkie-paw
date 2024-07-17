package com.WalkiePaw.domain.chatroom.service;

import com.WalkiePaw.domain.board.entity.Board;
import com.WalkiePaw.domain.board.repository.BoardRepository;
import com.WalkiePaw.domain.chatroom.entity.Chatroom;
import com.WalkiePaw.domain.chatroom.repository.ChatroomRepository;
import com.WalkiePaw.domain.review.entity.Review;
import com.WalkiePaw.domain.review.repository.ReviewRepository;
import com.WalkiePaw.global.exception.BadRequestException;
import com.WalkiePaw.global.exception.ExceptionCode;
import com.WalkiePaw.presentation.domain.chatroom.dto.TransactionResponse;
import com.WalkiePaw.domain.member.Repository.MemberRepository;
import com.WalkiePaw.domain.member.entity.Member;
import com.WalkiePaw.presentation.domain.chatroom.dto.ChatroomAddRequest;
import com.WalkiePaw.presentation.domain.chatroom.dto.ChatroomListResponse;
import com.WalkiePaw.presentation.domain.chatroom.dto.ChatroomRespnose;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
        //        List<Chatroom> content = page.getContent();
//        for (Chatroom chatroom : content) {
//            TransactionResponse response = new TransactionResponse();
//            Integer bId = chatroom.getBoard().getId();
//            Integer mId = chatroom.getMember().getId();
//            if (bId.equals(memberId)) {
//                boolean hasNoReview  = reviewRepository.findByReviewerIdAndChatroomId(bId, chatroom.getId()).isEmpty();
//                response.setHasReview(!hasNoReview);
//                response.setMemberNickName(chatroom.getMember().getNickname());
//            } else {
//                boolean hasNoReview = reviewRepository.findByReviewerIdAndChatroomId(mId, chatroom.getId()).isEmpty();
//                response.setHasReview(!hasNoReview);
//                response.setMemberNickName(chatroom.getBoard().getMember().getNickname());
//            }
//            response.setChatroomId(chatroom.getId());
//            response.setTitle(chatroom.getBoard().getTitle());
//            response.setCategory(chatroom.getBoard().getCategory());
//            response.setCreatedDate(chatroom.getCompletedDate());
//        }
        return chatroomRepository.findTransaction(memberId, pageable);
    }
}
