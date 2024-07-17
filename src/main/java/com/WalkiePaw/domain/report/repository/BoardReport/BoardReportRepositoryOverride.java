package com.WalkiePaw.domain.report.repository.BoardReport;

import com.WalkiePaw.domain.report.entity.BoardReport;

import com.WalkiePaw.presentation.domain.report.boardReportDto.BoardReportListResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardReportRepositoryOverride {

    Page<BoardReportListResponse> findAllByResolvedCond(String status, Pageable pageable);
}
