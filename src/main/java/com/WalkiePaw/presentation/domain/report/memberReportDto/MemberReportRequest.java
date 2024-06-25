package com.WalkiePaw.presentation.domain.report.memberReportDto;

import com.WalkiePaw.domain.member.entity.Member;
import com.WalkiePaw.domain.report.entity.MemberReport;
import com.WalkiePaw.domain.report.entity.MemberReportCategory;
import lombok.Getter;

@Getter
public class MemberReportRequest {
    private String title;
    private String content;
    private MemberReportCategory reason;
    private Integer reportingMemberId;
    private Integer reportedMemberId;

    /**
     * DTO를 Entity로 변환하는 메서드
     */
    public MemberReport toEntity(Member reportingMember, Member reportedMember) {
        return MemberReport.builder()
                .title(this.title)
                .content(this.content)
                .reason(this.reason)
                .reportingMember(reportingMember)
                .reportedMember(reportedMember)
                .build();

    }
}
