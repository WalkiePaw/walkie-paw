package com.WalkiePaw.domain.report.repository;

import com.WalkiePaw.domain.report.entity.BoardReport;
import com.WalkiePaw.domain.report.entity.BoardReportStatus;
import com.WalkiePaw.global.util.Querydsl4RepositorySupport;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.List;

import static com.WalkiePaw.domain.report.entity.QBoardReport.boardReport;
import static org.springframework.util.StringUtils.hasText;

public class BoardReportRepositoryOverrideImpl extends Querydsl4RepositorySupport implements BoardReportRepositoryOverride {

    public BoardReportRepositoryOverrideImpl() {
        super(BoardReport.class);
    }

    @Override
    public List<BoardReport> findAllByResolvedCond(String status) {
        return selectFrom(boardReport)
                .where(statusCond(status))
                .fetch();
    }

    private BooleanExpression statusCond(final String status) {
        if (hasText(status)) {
            System.out.println("not null");
            if (status.equals("RESOLVED")) {
                return boardReport.status.eq(BoardReportStatus.BLINDED)
                        .or(boardReport.status.eq(BoardReportStatus.IGNORE));
            } else if (status.equals("UNRESOLVED")) {
                return boardReport.status.eq(BoardReportStatus.UNRESOLVED);
            }
        }
        System.out.println("null");
        return null;
    }

}
