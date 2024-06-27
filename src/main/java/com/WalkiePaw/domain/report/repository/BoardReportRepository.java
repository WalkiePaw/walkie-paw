package com.WalkiePaw.domain.report.repository;

import com.WalkiePaw.domain.report.entity.BoardReport;

import java.util.List;
import java.util.Optional;

public interface BoardReportRepository {

    BoardReport save(final BoardReport boardReport);

    Optional<BoardReport> findById(final Integer brId);

    List<BoardReport> findAll();
}
