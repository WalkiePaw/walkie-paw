package com.WalkiePaw.presentation.domain.report.boardReportDto;

import com.WalkiePaw.domain.report.entity.BoardReportCategory;
import lombok.Getter;

@Getter
public class BoardReportUpdateRequest {
    private BoardReportCategory reason;
    private String content;
    private Integer memberId;
    private Integer boardId;
}
