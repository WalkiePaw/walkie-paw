package com.WalkiePaw.domain.report.repository.BoardReport;

import com.WalkiePaw.domain.report.entity.BoardReport;

import java.util.List;

public interface BoardReportRepositoryOverride {

    List<BoardReport> findAllByResolvedCond(String status);
}
