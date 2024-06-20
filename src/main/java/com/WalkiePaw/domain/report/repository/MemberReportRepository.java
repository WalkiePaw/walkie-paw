package com.WalkiePaw.domain.report.repository;

import com.WalkiePaw.domain.report.entity.BoardReport;
import com.WalkiePaw.domain.report.entity.MemberReport;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MemberReportRepository {

    private final EntityManager em;

    public void save(MemberReport memberReport) {
        em.persist(memberReport);
    }

    public MemberReport findOne(Integer mrId) {
        return em.find(MemberReport.class, mrId);
    }

    public List<MemberReport> findAll() {
        return em.createQuery("select mr from MemberReport mr", MemberReport.class)
                .getResultList();
    }
}
