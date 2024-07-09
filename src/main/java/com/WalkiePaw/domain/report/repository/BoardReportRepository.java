package com.WalkiePaw.domain.report.repository;

import com.WalkiePaw.domain.report.entity.BoardReport;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Profile("spring-data-jpa")
public interface BoardReportRepository extends JpaRepository<BoardReport, Integer>, BoardReportRepositoryOverride {

    @Override
    @EntityGraph(attributePaths = {"member", "board"})
    Optional<BoardReport> findById(final Integer boardReportId);

    @Override
    @EntityGraph(attributePaths = {"member", "board"})
    List<BoardReport> findAll();
}
