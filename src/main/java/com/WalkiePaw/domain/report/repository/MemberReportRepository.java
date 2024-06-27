package com.WalkiePaw.domain.report.repository;

import com.WalkiePaw.domain.report.entity.MemberReport;

import java.util.List;
import java.util.Optional;

public interface MemberReportRepository {

    MemberReport save(final MemberReport memberReport);

    Optional<MemberReport> findById(final Integer memberReportId);

    List<MemberReport> findAll();

//    void update(final Integer memberReportId, final MemberReportRequest request);
}
