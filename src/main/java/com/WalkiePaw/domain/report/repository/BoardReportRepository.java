package com.WalkiePaw.domain.report.repository;

import com.WalkiePaw.domain.report.entity.BoardReport;
import com.WalkiePaw.presentation.domain.report.boardReportDto.BoardReportRequest;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class BoardReportRepository {

    private final EntityManager em;

    public Integer save(final BoardReport boardReport) {
        em.persist(boardReport);
        return boardReport.getId();
    }

    public BoardReport findById(final Integer brId) {
        return em.find(BoardReport.class, brId);
    }

    public List<BoardReport> findAll() {
        return em.createQuery("select br from BoardReport br", BoardReport.class)
                .getResultList();
    }

    public void update(final Integer boardReportId, final BoardReportRequest request) {
        BoardReport boardReport = em.find(BoardReport.class, boardReportId);
    }
}
