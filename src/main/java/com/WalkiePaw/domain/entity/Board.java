package com.WalkiePaw.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {
    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    private String title;
    private String content;
    private int price;
    private LocalDate meetingTime;
    private LocalDate createdDate;
    private int viewCount;
    private int likeCount;
    @Enumerated(EnumType.STRING)
    private BoardStatus status;


    private Board(String title, String content, int price, LocalDate meetingTime) {
        this.title = title;
        this.content = content;
        this.price = price;
        this.meetingTime = meetingTime;
        createdDate = LocalDate.now();
        status = BoardStatus.RECRUITING;
    }

    /**
     * Board 생성 메서드
     * @param member
     * @param title
     * @param content
     * @param price
     * @param meetingTime
     * @return
     */
    public static Board createBoard(Member member, String title, String content, int price, LocalDate meetingTime) {
        Board board = new Board(title, content, price, meetingTime);
        board.member = member;
        return board;
    }

    /**
     * TODO : update 메서드 만들기
     */
}
