package com.WalkiePaw.domain.report.entity;

import com.WalkiePaw.domain.common.BaseEntity;
import com.WalkiePaw.domain.member.entity.Member;
import com.WalkiePaw.presentation.domain.report.memberReportDto.MemberReportUpdateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberReport extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_report_id")
    private Integer id;
    @Column(name = "member_report_title")
    private String title;
    @Column(name = "member_report_content")
    private String content;
    @Enumerated(EnumType.STRING)
    private MemberReportCategory reason;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporting_member_id", referencedColumnName = "member_id")
    private Member reportingMember;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_member_id", referencedColumnName = "member_id")
    private Member reportedMember;
    @Enumerated(EnumType.STRING)
    private MemberReportStatus status;

    @Builder
    public MemberReport(String title, String content, MemberReportCategory reason, Member reportingMember, Member reportedMember) {
        this.title = title;
        this.content = content;
        this.reason = reason;
        this.reportingMember = reportingMember;
        this.reportedMember = reportedMember;
        this.status = MemberReportStatus.UNRESOLVED;
    }

    public void update(MemberReportUpdateRequest request, Member reportingMember, Member reportedMember) {
        this.content = request.getContent();
        this.reason = request.getReason();
        this.reportingMember = reportingMember;
        this.reportedMember = reportedMember;
    }

    public void ban() {
        this.status = MemberReportStatus.BANNED;
    }

    public void ignore() {
        this.status = MemberReportStatus.IGNORE;
    }

//    /**
//     * MemberReport 생성 메서드
//     */
//    public MemberReport createMemberReport(String title, String content, LocalDate reportedDate, MemberReportCategory reason, Member reportingMember, Member reportedMember) {
//        return new MemberReport(title, content, reportedDate, reason, reportingMember, reportedMember);
//    }
}
