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
    private LocalDateTime meetingTime;
    private int viewCount;
    private int likeCount;
    private String location;
    @Enumerated(EnumType.STRING)
    private BoardStatus status;
    private BoardCategory category;
    private boolean isDeleted;

    @Builder
    public Board(Member member, String title, String content, int price, LocalDateTime meetingTime, LocalDateTime startTime, LocalDateTime endTime, PriceType priceType, String location, BoardCategory category) {
        this.priceType = priceType;
        this.member = member;
        this.title = title;
        this.content = content;
        this.price = price;
        this.meetingTime = meetingTime;
        this.startTime = startTime;
        this.endTime = endTime;
        this.category = category;
        status = BoardStatus.RECRUITING;
    }

//    /**
//     * Board 생성 메서드
//     */
//    public static Board createBoard(Member member, String title, String content, int price, LocalDate meetingTime) {
//        Board board = new Board(title, content, price, meetingTime);
//        board.member = member;
//        return board;
//    }

    public void updateMember(Member member) {
        this.member = member;
    }

    public void updateTitle(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void updateBoard(final BoardUpdateRequest request) {
        this.content = request.getContent();
        this.title = request.getTitle();
        this.price = request.getPrice();
        this.meetingTime = request.getMeetingTime();
        this.startTime = request.getStartTime();
        this.endTime = request.getEndTime();
        this.priceType = request.getPriceType();
    }

    public void updateStatus(final BoardStatus status) {
        this.status = status;
    }

    /**
     * TODO : update 메서드 만들기
     *  - validation 추가
     */
}
