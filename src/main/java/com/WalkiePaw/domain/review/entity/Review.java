package com.WalkiePaw.domain.review.entity;

import com.WalkiePaw.domain.common.BaseEntity;
import com.WalkiePaw.domain.chatroom.entity.Chatroom;
import com.WalkiePaw.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id")
    private Chatroom chatroom;
    @Column(name = "review_content")
    private String content;
    private int point;
    private boolean isDeleted;

    @Builder
    public Review(int point, String content, Chatroom chatroom, Member member) {
        this.point = point;
        this.content = content;
        this.chatroom = chatroom;
        this.member = member;
    }

    /**
     * TODO - update 메서드
     */
    public void update() {
    }

//    public Review createReview(int point, String content, Chatroom chatroom, Member member) {
//        return new Review(point, content, chatroom, member);
//    }
}
