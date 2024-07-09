package com.WalkiePaw.domain.report.repository.MemberReport;

import com.WalkiePaw.domain.report.entity.MemberReport;
import com.WalkiePaw.domain.report.entity.MemberReportStatus;
import com.WalkiePaw.global.util.Querydsl4RepositorySupport;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.List;

import static com.WalkiePaw.domain.report.entity.QMemberReport.memberReport;
import static org.springframework.util.StringUtils.hasText;

public class MemberReportRepositoryOverrideImpl extends Querydsl4RepositorySupport implements MemberReportRepositoryOverride {

    public MemberReportRepositoryOverrideImpl() {
        super(MemberReport.class);
    }

    @Override
    public List<MemberReport> findAllByCond(final String status) {
        return selectFrom(memberReport)
                .where(statusCond(status))
                .fetch();
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
