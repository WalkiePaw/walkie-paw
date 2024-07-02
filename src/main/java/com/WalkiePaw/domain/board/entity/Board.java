package com.WalkiePaw.domain.board.entity;

import com.WalkiePaw.domain.common.BaseEntity;
import com.WalkiePaw.domain.member.entity.Member;
import com.WalkiePaw.presentation.domain.board.dto.BoardUpdateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {
    public static final String DELETED_MSG = "삭제된 게시글입니다."; // msg 처리 필요

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    private String title;
    private String content;
    private int price;
    @Enumerated(EnumType.STRING)
    private PriceType priceType;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int viewCount;
    private int likeCount;
    private String location;
    private String detailedLocation;
    @Enumerated(EnumType.STRING)
    private BoardStatus status;
    @Enumerated(EnumType.STRING)
    private BoardCategory category;

    @Builder
    public Board(
            Member member, String title, String content, int price, LocalDateTime meetingTime,
            LocalDateTime startTime, LocalDateTime endTime, PriceType priceType,
            String location, String detailedLocation, BoardCategory category) {
        this.priceType = priceType;
        this.member = member;
        this.title = title;
        this.content = content;
        this.price = price;
        this.startTime = startTime;
        this.endTime = endTime;
        this.category = category;
        this.detailedLocation = detailedLocation;
        this.location = location;
        status = BoardStatus.RECRUITING;
    }

    public void updateMember(Member member) {
        this.member = member;
    }

    public void updateTitle(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void updateBoard(final String title, final String content, final int price,
                       final LocalDateTime startTime,
                       final LocalDateTime endTime, final PriceType priceType) {

        this.title = title;
        this.content = content;
        this.price = price;
        this.startTime = startTime;
        this.endTime = endTime;
        this.priceType = priceType;
    }

    public void delete() {
        this.content = DELETED_MSG;
        this.title = DELETED_MSG;
        this.status = BoardStatus.DELETED;
    }

    public void updateStatus(final BoardStatus status) {
        this.status = status;
    }

    /**
     * TODO : update 메서드 만들기
     *  - validation 추가
     */
}
