package com.WalkiePaw.presentation.domain.review.dto;

import com.WalkiePaw.domain.chatroom.entity.Chatroom;
import com.WalkiePaw.domain.member.entity.Member;
import com.WalkiePaw.domain.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewSaveRequest {
    private int point;
    private String content;
    private final Integer chatroomId;
    private final Integer memberId;

    public static Review toEntity(final ReviewSaveRequest request, Chatroom chatroom, Member member) {
        return new Review(request.point, request.content, chatroom, member);
    }
}
