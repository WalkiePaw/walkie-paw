package com.WalkiePaw.domain.entity.review;

import com.WalkiePaw.domain.entity.BaseEntity;
import com.WalkiePaw.domain.entity.board.Board;
import com.WalkiePaw.domain.entity.chatroom.Chatroom;
import com.WalkiePaw.domain.entity.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
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

}
