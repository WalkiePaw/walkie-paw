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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewee_member_id", referencedColumnName = "member_id")
    private Member reviewee;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_member_id", referencedColumnName = "member_id")
    private Member reviewer;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id")
    private Chatroom chatroom;
    @Column(name = "review_content")
    private String content;
    private int point;
    private boolean isDeleted;

    @Builder
    public Review(int point, String content, Chatroom chatroom, Member reviewee, Member reviewer) {
        this.point = point;
        this.content = content;
        this.chatroom = chatroom;
        this.reviewee = reviewee;
        this.reviewer = reviewer;
    }

    /**
     * TODO - update 메서드
     */
    public void update(final String content, final int point) {
        this.content = content;
        this.point = point;
    }

//    public Review createReview(int point, String content, Chatroom chatroom, Member member) {
//        return new Review(point, content, chatroom, member);
//    }
}
