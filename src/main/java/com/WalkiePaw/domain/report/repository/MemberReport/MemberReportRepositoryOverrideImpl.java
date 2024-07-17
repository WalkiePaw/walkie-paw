package com.WalkiePaw.domain.report.repository.MemberReport;

import com.WalkiePaw.domain.report.entity.MemberReport;
import com.WalkiePaw.domain.report.entity.MemberReportStatus;
import com.WalkiePaw.global.util.Querydsl4RepositorySupport;
import com.WalkiePaw.presentation.domain.report.memberReportDto.MemberReportListResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.WalkiePaw.domain.report.entity.QMemberReport.memberReport;
import static org.springframework.util.StringUtils.hasText;

public class MemberReportRepositoryOverrideImpl extends Querydsl4RepositorySupport implements MemberReportRepositoryOverride {

    public MemberReportRepositoryOverrideImpl() {
        super(MemberReport.class);
    }

    @Override
    public Page<MemberReportListResponse> findAllByCond(final String status, Pageable pageable) {
        return page(pageable, page -> page.select(Projections.fields(MemberReportListResponse.class,
                memberReport.id.as("memberReportId"),
                memberReport.title,
                memberReport.reason,
                memberReport.reportingMember.name.as("reportingMemberName"),
                memberReport.reportingMember.nickname.as("reportingMemberNickname"),
                memberReport.reportedMember.name.as("reportedMemberName"),
                memberReport.reportedMember.nickname.as("reportedMemberNickname"),
                memberReport.createdDate
                )).from(memberReport).
                where(statusCond(status))
        );
    }

    private BooleanExpression statusCond(final String status) {
        if (hasText(status)) {
            if (status.equals("RESOLVED")) {
                return memberReport.status.eq(MemberReportStatus.BANNED)
                        .or(memberReport.status.eq(MemberReportStatus.IGNORE));
            } else if (status.equals("UNRESOLVED")) {
                return memberReport.status.eq(MemberReportStatus.UNRESOLVED);
            }
        }
        return null;
    }
}
