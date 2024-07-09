package com.WalkiePaw.domain.report.repository.MemberReport;

import com.WalkiePaw.domain.report.entity.MemberReport;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Profile("spring-data-jpa")
public interface MemberReportRepository extends JpaRepository<MemberReport, Integer>, MemberReportRepositoryOverride {

    @Override
    @Query("select mr from MemberReport mr join fetch mr.reportingMember join fetch mr.reportedMember where mr.id = :id")
    Optional<MemberReport> findById(@Param("id") final Integer memberReportId);

    @Override
    @Query("select mr from MemberReport mr join fetch mr.reportingMember join fetch mr.reportedMember")
    List<MemberReport> findAll();
}
