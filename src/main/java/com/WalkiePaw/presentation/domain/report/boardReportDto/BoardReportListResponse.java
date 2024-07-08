package com.WalkiePaw.presentation.domain.report.boardReportDto;

import com.WalkiePaw.domain.report.entity.BoardReport;
import com.WalkiePaw.domain.report.entity.BoardReportCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class BoardReportListResponse {
    private Integer boardReportId;
    private BoardReportCategory reason;
    private String writerName;
    private String boardTitle;
    private String boardWriterName;
    private String boardWriterNickname;
    private LocalDateTime boardWriterCreatedDate;

    /**
     * Entity -> DTO
     * @param boardReport Entity
     * @return DTO
     */
    public static BoardReportListResponse from(BoardReport boardReport) {
        return new BoardReportListResponse(
                boardReport.getId(),
                boardReport.getReason(),
                boardReport.getMember().getName(), // writerName
                boardReport.getBoard().getTitle(), // boardTitle
                boardReport.getBoard().getMember().getName(), // boardWriterName
                boardReport.getBoard().getMember().getNickname(), // boardWriterNickname
                boardReport.getBoard().getMember().getCreatedDate() // boardWriterCreatedDate
        );
    }
}
