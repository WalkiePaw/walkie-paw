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
    private String reason;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;
}
