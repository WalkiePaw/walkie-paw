package com.WalkiePaw.domain.report.repository;

import com.WalkiePaw.domain.report.entity.BoardReport;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
@Profile("jpa")
public class JpaBoardReportRepositoryImpl implements BoardReportRepository {

    private final EntityManager em;

    public BoardReport save(final BoardReport boardReport) {
        em.persist(boardReport);
        return boardReport;
    }

    public Optional<BoardReport> findById(final Integer brId) {
        return Optional.ofNullable(em.find(BoardReport.class, brId));
    }

    public List<BoardReport> findAll() {
        return em.createQuery("select br from BoardReport br", BoardReport.class)
                .getResultList();
    }
}
