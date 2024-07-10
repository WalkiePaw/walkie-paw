package com.WalkiePaw.domain.review.service;

import com.WalkiePaw.domain.board.entity.BoardCategory;
import com.WalkiePaw.domain.chatroom.entity.Chatroom;
import com.WalkiePaw.domain.chatroom.repository.ChatroomRepository;
import com.WalkiePaw.domain.member.Repository.MemberRepository;
import com.WalkiePaw.domain.member.entity.Member;
import com.WalkiePaw.domain.review.entity.Review;
import com.WalkiePaw.domain.review.repository.ReviewRepository;
import com.WalkiePaw.presentation.domain.review.dto.ReviewDetailResponse;
import com.WalkiePaw.presentation.domain.review.dto.ReviewListResponse;
import com.WalkiePaw.presentation.domain.review.dto.ReviewSaveRequest;
import com.WalkiePaw.presentation.domain.review.dto.ReviewUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ChatroomRepository chatroomRepository;
    private final MemberRepository memberRepository;


    @Transactional
    public Integer addReview(final ReviewSaveRequest request) {
        Chatroom chatroom = chatroomRepository.findById(request.getChatroomId())
                .orElseThrow(() -> new IllegalStateException("잘못된 채팅방 번호입니다."));
        Member reviewer = memberRepository.findById(request.getReviewerId())
                .orElseThrow(() -> new IllegalStateException("잘못된 회원 번호입니다."));
        Review review = null;
        if (chatroom.getBoard().getId() == reviewer.getId()) {
            review = ReviewSaveRequest.toEntity(request, chatroom,
                memberRepository.findById(chatroom.getBoard().getId()).orElseThrow(),
                reviewer);
        } else {
            review = ReviewSaveRequest.toEntity(request, chatroom,
                memberRepository.findById(chatroom.getMember().getId()).orElseThrow(),
                reviewer);
        }
        return reviewRepository.save(review).getId();
    }

    public ReviewDetailResponse findById(final Integer reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalStateException("잘못된 리뷰 번호입니다."));
        return ReviewDetailResponse.from(review);
    }

    @Transactional
    public void updateReview(final Integer reviewId, final ReviewUpdateRequest request) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalStateException("잘못된 리뷰 번호입니다."));
        review.update(request.getContent(), request.getPoint());
    }

    public Slice<ReviewListResponse> findByReviewerId(Pageable pageable, final Integer reviewerId, final BoardCategory category) {
        List<Review> reviews = reviewRepository.findByReviewerIdAndCategory(pageable, reviewerId, category);
        return new SliceImpl<>(reviews.stream()
                .map(ReviewListResponse::from)
                .toList());
    }

    public Slice<ReviewListResponse> findByRevieweeId(Pageable pageable, final Integer revieweeId, final BoardCategory category) {
        List<Review> reviews = reviewRepository.findByRevieweeIdAndCategory(pageable, revieweeId, category);
        return new SliceImpl<>(reviews.stream()
                .map(ReviewListResponse::from)
                .toList());
    }
}
