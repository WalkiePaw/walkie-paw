package com.WalkiePaw.presentation.domain.report.boardReportDto;

import com.WalkiePaw.domain.report.entity.BoardReport;
import com.WalkiePaw.domain.report.entity.BoardReportCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardReportGetResponse {
    private String content;
    private BoardReportCategory reason;
    private String reporterName;
    private String boardTitle;
    private String boardWriterName;

    /**
     * Entity -> DTO
     */
    public static BoardReportGetResponse from(BoardReport boardReport) {
        return new BoardReportGetResponse(
                boardReport.getContent(),
                boardReport.getReason(),
                boardReport.getMember().getName(), // reporterName
                boardReport.getBoard().getMember().getName(), // boardTitle
                boardReport.getBoard().getTitle() // boardWriterName
        );
    }
}
