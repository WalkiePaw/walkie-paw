package com.WalkiePaw.domain.report.repository;

import com.WalkiePaw.domain.report.entity.MemberReport;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Profile("spring-data-jpa")
public interface SpringDataMemberReportRepository extends JpaRepository<MemberReport, Integer>, MemberReportRepository {
}
