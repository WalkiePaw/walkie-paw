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
    @Enumerated(EnumType.STRING)
    private MemberReportCategory reason;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporting_member_id", referencedColumnName = "member_id")
    private Member reportingMember;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_member_id", referencedColumnName = "member_id")
    private Member reportedMember;

    private MemberReport(String title, String content, LocalDate reportedDate, MemberReportCategory reason, Member reportingMember, Member reportedMember) {
        this.title = title;
        this.content = content;
        this.reportedDate = reportedDate;
        this.reason = reason;
        this.reportingMember = reportingMember;
        this.reportedMember = reportedMember;
    }

    /**
     * MemberReport 생성 메서드
     */
    public MemberReport createMemberReport(String title, String content, LocalDate reportedDate, MemberReportCategory reason, Member reportingMember, Member reportedMember) {
        return new MemberReport(title, content, reportedDate, reason, reportingMember, reportedMember);
    }
}
