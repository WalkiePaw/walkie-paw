package com.WalkiePaw.domain.report.repository;

import com.WalkiePaw.domain.report.entity.BoardReport;
import com.WalkiePaw.presentation.domain.report.boardReportDto.BoardReportListResponse;

import java.util.List;
import java.util.Optional;

public interface BoardReportRepositoryOverride {

    List<BoardReport> findAllByResolvedCond(String status);
}
