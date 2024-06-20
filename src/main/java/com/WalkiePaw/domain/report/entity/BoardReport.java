package com.WalkiePaw.domain.report.entity;

import com.WalkiePaw.domain.common.BaseEntity;
import com.WalkiePaw.domain.board.entity.Board;
import com.WalkiePaw.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardReport extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "board_report_id")
    private Integer id;
    @Column(name = "board_report_title")
    private String title;
    @Column(name = "board_report_content")
    private String content;
    @Enumerated(EnumType.STRING)
    private BoardReportCategory reason;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    public BoardReport(String title, String content, Member member, Board board, BoardReportCategory reason) {
        this.title = title;
        this.content = content;
        this.member = member;
        this.board = board;
        this.reason = reason;
    }
//    /**
//     * BoardReport 생성 메서드
//     */
//    public BoardReport createBoardReport (String title, String content, LocalDate reportedDate, Member member, Board board, BoardReportCategory reason) {
//        return new BoardReport(title, content, reportedDate, member, board, reason);
//    }
}
