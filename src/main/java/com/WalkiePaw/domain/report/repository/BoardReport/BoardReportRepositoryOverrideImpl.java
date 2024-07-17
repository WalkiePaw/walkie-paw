package com.WalkiePaw.domain.report.repository.BoardReport;

import com.WalkiePaw.domain.report.entity.BoardReport;
import com.WalkiePaw.domain.report.entity.BoardReportStatus;
import com.WalkiePaw.global.util.Querydsl4RepositorySupport;
import com.WalkiePaw.presentation.domain.report.boardReportDto.BoardReportListResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static com.WalkiePaw.domain.report.entity.QBoardReport.boardReport;
import static org.springframework.util.StringUtils.hasText;

public class BoardReportRepositoryOverrideImpl extends Querydsl4RepositorySupport implements BoardReportRepositoryOverride {

    public BoardReportRepositoryOverrideImpl() {
        super(BoardReport.class);
    }

    @Override
    public Page<BoardReportListResponse> findAllByResolvedCond(String status, Pageable pageable) {
        return page(pageable, page -> page.select(Projections.fields(BoardReportListResponse.class,
            boardReport.id.as("boardReportId"),
            boardReport.reason,
            boardReport.member.name.as("writerName"),
            boardReport.board.title.as("boardTitle"),
            boardReport.board.member.name.as("boardWriterName"),
            boardReport.board.member.nickname.as("boardWriterNickname"),
            boardReport.board.member.createdDate.as("boardWriterCreatedDate")
            )).from(boardReport).
            where(
                statusCond(status)
        ));
    }

    private BooleanExpression statusCond(final String status) {
        if (hasText(status)) {
            if (status.equals("RESOLVED")) {
                return boardReport.status.eq(BoardReportStatus.BLINDED)
                        .or(boardReport.status.eq(BoardReportStatus.IGNORE));
            } else if (status.equals("UNRESOLVED")) {
                return boardReport.status.eq(BoardReportStatus.UNRESOLVED);
            }
        }
        return null;
    }

}
