package com.WalkiePaw.presentation.domain.report.boardReportDto;

import com.WalkiePaw.domain.board.entity.Board;
import com.WalkiePaw.domain.member.entity.Member;
import com.WalkiePaw.domain.report.entity.BoardReport;
import com.WalkiePaw.domain.report.entity.BoardReportCategory;
import com.WalkiePaw.presentation.domain.board.dto.BoardAddRequest;
import com.WalkiePaw.presentation.domain.member.dto.MemberRequest;
import lombok.Getter;

@Getter
public class BoardReportRequest {
    private String title;
    private String content;
    private BoardReportCategory reason;
    private Integer memberId;
    private Integer boardId;

    public BoardReport toEntity(Member member, Board board) {
        return BoardReport.builder()
                .title(this.title)
                .content(this.content)
                .reason(this.reason)
                .member(member)
                .board(board)
                .build();
    }
}
