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
public class MemberReport {
    @Id
    @GeneratedValue
    @Column(name = "member_report_id")
    private Integer id;
    @Column(name = "member_report_title")
    private String title;
    @Column(name = "member_report_content")
    private String content;
    private LocalDate reportedDate;
    private String reason;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @Column(name = "reporting_member_id")
    private Member reportingMember;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @Column(name = "reported_member_id")
    private Member reportedMember;

}
