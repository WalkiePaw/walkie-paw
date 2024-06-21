package com.WalkiePaw.presentation.domain.report;

import com.WalkiePaw.domain.board.entity.Board;
import com.WalkiePaw.domain.member.entity.Member;
import com.WalkiePaw.domain.report.entity.BoardReportCategory;
import lombok.Getter;

@Getter
public class BoardReportResponse {
    private String title;
    private String content;
    private BoardReportCategory reason;
    private Member member;
    private Board board;
}
