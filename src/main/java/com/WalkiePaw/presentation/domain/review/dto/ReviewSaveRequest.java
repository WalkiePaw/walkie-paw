package com.WalkiePaw.presentation.domain.review.dto;

import com.WalkiePaw.domain.chatroom.entity.Chatroom;
import com.WalkiePaw.domain.member.entity.Member;
import com.WalkiePaw.domain.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewSaveRequest {
    private final int point;
    private final String content;
    private final Integer chatroomId;
    private final Integer revieweeId;
    private final Integer reviewerId;

    public static Review toEntity(final ReviewSaveRequest request, Chatroom chatroom, Member reviewee, Member reviewer) {
        return new Review(request.point, request.content, chatroom, reviewee, reviewer);
    }
}
