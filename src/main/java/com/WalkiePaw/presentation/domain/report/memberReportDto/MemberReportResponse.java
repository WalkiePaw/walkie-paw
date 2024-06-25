package com.WalkiePaw.presentation.domain.report.memberReportDto;

import com.WalkiePaw.domain.report.entity.MemberReport;
import com.WalkiePaw.domain.report.entity.MemberReportCategory;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MemberReportResponse {

    private String title;
    private String content;
    private MemberReportCategory reason;
    private Integer reportingMemberId;
    private String reportingMemberNickname;
    private Integer reportedMemberId;
    private String reportedMemberNickname;

    /**
     * Entity를 DTO로 변환하는 메서드
     */
    public static MemberReportResponse from(MemberReport memberReport) {
        return new MemberReportResponse(
                memberReport.getTitle(),
                memberReport.getContent(),
                memberReport.getReason(),
                memberReport.getReportingMember().getId(),
                memberReport.getReportingMember().getName(),
                memberReport.getReportedMember().getId(),
                memberReport.getReportedMember().getName());
    }
}
