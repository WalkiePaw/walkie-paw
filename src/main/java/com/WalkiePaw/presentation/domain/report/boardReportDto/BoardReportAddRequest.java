package com.WalkiePaw.presentation.domain.report.boardReportDto;

import com.WalkiePaw.domain.board.entity.Board;
import com.WalkiePaw.domain.member.entity.Member;
import com.WalkiePaw.domain.report.entity.BoardReport;
import com.WalkiePaw.domain.report.entity.BoardReportCategory;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BoardReportAddRequest {
    private BoardReportCategory reason;
    private String content;
    private Integer memberId;
    private Integer boardId;

    /**
     * DTO -> Entity
     */
    public BoardReport toEntity(Member member, Board board) {
        return BoardReport.builder()
                .content(this.content)
                .reason(this.reason)
                .member(member)
                .board(board)
                .build();
    }
}
