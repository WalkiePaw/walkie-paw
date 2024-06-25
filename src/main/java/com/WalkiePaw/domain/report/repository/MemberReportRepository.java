package com.WalkiePaw.domain.report.repository;

import com.WalkiePaw.domain.report.entity.BoardReport;
import com.WalkiePaw.domain.report.entity.MemberReport;
import com.WalkiePaw.presentation.domain.report.memberReportDto.MemberReportRequest;
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

    public Integer save(final MemberReport memberReport) {
        em.persist(memberReport);
        return memberReport.getId();
    }

    public MemberReport findOne(final Integer mrId) {
        return em.find(MemberReport.class, mrId);
    }

    public List<MemberReport> findAll() {
        return em.createQuery("select mr from MemberReport mr", MemberReport.class)
                .getResultList();
    }

    public MemberReport findById(final Integer memberReportId) {
        return em.find(MemberReport.class, memberReportId);
    }

    public void update(final Integer memberReportId, final MemberReportRequest request) {
        MemberReport memberReport = em.find(MemberReport.class, memberReportId);
    }
}
