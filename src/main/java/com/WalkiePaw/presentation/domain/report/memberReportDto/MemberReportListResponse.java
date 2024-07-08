package com.WalkiePaw.presentation.domain.report.memberReportDto;

import com.WalkiePaw.domain.report.entity.MemberReport;
import com.WalkiePaw.domain.report.entity.MemberReportCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MemberReportListResponse {

    private Integer memberReportId;
    private String title;
    private MemberReportCategory reason;
    private String reportingMemberName;
    private String reportingMemberNickname;
    private String reportedMemberName;
    private String reportedMemberNickname;
    private LocalDateTime createdDate;

    /**
     * Entity를 DTO로 변환하는 메서드
     */
    public static MemberReportListResponse from(MemberReport memberReport) {
        return new MemberReportListResponse(
                memberReport.getId(),
                memberReport.getTitle(),
                memberReport.getReason(),
                memberReport.getReportingMember().getName(), // reportingMemberName
                memberReport.getReportingMember().getNickname(), // reportingMemberNickname
                memberReport.getReportedMember().getName(), // reportedMemberName
                memberReport.getReportedMember().getNickname(), // reportedMemberNickname
                memberReport.getCreatedDate()
        );
    }
}
