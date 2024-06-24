package com.WalkiePaw.domain.review.service;

import com.WalkiePaw.domain.chatroom.entity.Chatroom;
import com.WalkiePaw.domain.chatroom.repository.ChatroomRepository;
import com.WalkiePaw.domain.member.Repository.MemberRepository;
import com.WalkiePaw.domain.member.entity.Member;
import com.WalkiePaw.domain.review.entity.Review;
import com.WalkiePaw.domain.review.repository.ReviewRepository;
import com.WalkiePaw.presentation.domain.review.dto.ReviewListResponse;
import com.WalkiePaw.presentation.domain.review.dto.ReviewResponse;
import com.WalkiePaw.presentation.domain.review.dto.ReviewSaveRequest;
import com.WalkiePaw.presentation.domain.review.dto.ReviewUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public List<ReviewListResponse> findAll() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream()
                .map(ReviewListResponse::from)
                .toList();
    }

    @Transactional
    public Integer addReview(final ReviewSaveRequest request) {
        Chatroom chatroom = chatroomRepository.findById(request.getChatroomId());
        Member member = memberRepository.findById(request.getMemberId());
        Review review = ReviewSaveRequest.toEntity(request, chatroom, member);
        return reviewRepository.save(review);
    }

    public ReviewResponse findById(final Integer id) {
        Review review = reviewRepository.findById(id);
        return ReviewResponse.from(review);
    }

    @Transactional
    public void updateReview(final Integer id, final ReviewUpdateRequest request) {
        Review review = reviewRepository.findById(id);
        review.update();
    }
}
