package com.WalkiePaw.domain.report.repository.MemberReport;

import com.WalkiePaw.domain.report.entity.MemberReport;
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
public class JpaMemberReportRepositoryImpl {

    private final EntityManager em;

    public MemberReport save(final MemberReport memberReport) {
        em.persist(memberReport);
        return memberReport;
    }

    public MemberReport findOne(final Integer mrId) {
        return em.find(MemberReport.class, mrId);
    }

    public List<MemberReport> findAll() {
        return em.createQuery("select mr from MemberReport mr join fetch mr.reportingMember join fetch mr.reportedMember", MemberReport.class)
                .getResultList();
    }

    public Optional<MemberReport> findById(final Integer memberReportId) {
        return Optional.ofNullable(em.createQuery(
                    "select mr from MemberReport mr join fetch mr.reportingMember " +
                            "join fetch mr.reportedMember where mr.id = :id", MemberReport.class)
                .setParameter("id", memberReportId)
                .getSingleResult());
    }
}
