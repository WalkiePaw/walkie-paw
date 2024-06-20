package com.WalkiePaw.domain.report.repository;

import com.WalkiePaw.domain.report.entity.BoardReport;
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

    public void save(BoardReport boardReport) {
        em.persist(boardReport);
    }

    public BoardReport findOne(Integer brId) {
        return em.find(BoardReport.class, brId);
    }

    public List<BoardReport> findAll() {
        return em.createQuery("select br from BoardReport br", BoardReport.class)
                .getResultList();
    }
}
