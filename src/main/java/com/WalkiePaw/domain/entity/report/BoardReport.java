package com.WalkiePaw.domain.entity.report;

import com.WalkiePaw.domain.entity.board.Board;
import com.WalkiePaw.domain.entity.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardReport {
    @Id
    @GeneratedValue
    @Column(name = "board_report_id")
    private Integer id;
    @Column(name = "board_report_title")
    private String title;
    @Column(name = "board_report_content")
    private String content;
    private LocalDate reportedDate;
    @Enumerated(EnumType.STRING)
    private BoardReportCategory reason;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    private BoardReport(String title, String content, LocalDate reportedDate, Member member, Board board, BoardReportCategory reason) {
        this.title = title;
        this.content = content;
        this.reportedDate = reportedDate;
        this.member = member;
        this.board = board;
        this.reason = reason;
    }
    /**
     * BoardReport 생성 메서드
     */
    public BoardReport createBoardReport (String title, String content, LocalDate reportedDate, Member member, Board board, BoardReportCategory reason) {
        return new BoardReport(title, content, reportedDate, member, board, reason);
    }
}
