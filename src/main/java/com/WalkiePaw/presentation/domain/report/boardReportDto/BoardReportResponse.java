package com.WalkiePaw.presentation.domain.report.boardReportDto;

import com.WalkiePaw.domain.report.entity.BoardReport;
import com.WalkiePaw.domain.report.entity.BoardReportCategory;
import com.WalkiePaw.presentation.domain.board.dto.BoardGetResponse;
import com.WalkiePaw.presentation.domain.member.dto.MemberResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardReportResponse {
    private String title;
    private String content;
    private BoardReportCategory reason;
    private MemberResponse member;
    private BoardGetResponse board;

    public static BoardReportResponse from(BoardReport boardReport) {
        return new BoardReportResponse(boardReport.getTitle(), boardReport.getContent(), boardReport.getReason(),
                MemberResponse.from(boardReport.getMember()), BoardGetResponse.from(boardReport.getBoard()));
    }
}
