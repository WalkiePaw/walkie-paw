package com.WalkiePaw.domain.review.service;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ChatroomRepository chatroomRepository;
    private final MemberRepository memberRepository;

    public List<ReviewListResponse> findByRevieweeId(final Integer revieweeId) {
        List<Review> reviews = reviewRepository.findByRevieweeId(revieweeId);
        return reviews.stream()
                .map(ReviewListResponse::from)
                .toList();
    }

    @Transactional
    public Integer addReview(final ReviewSaveRequest request) {
        Chatroom chatroom = chatroomRepository.findById(request.getChatroomId())
                .orElseThrow(() -> new IllegalStateException("잘못된 채팅방 번호입니다."));
        Member reviewee = memberRepository.findById(request.getRevieweeId());
        Member reviewer = memberRepository.findById(request.getReviewerId());
        Review review = ReviewSaveRequest.toEntity(request, chatroom, reviewee, reviewer);
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
}
