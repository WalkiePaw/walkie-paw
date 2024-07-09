package com.WalkiePaw.domain.report.repository.MemberReport;

import com.WalkiePaw.domain.report.entity.MemberReport;

import java.util.List;

public interface MemberReportRepositoryOverride {

    List<MemberReport> findAllByCond(String status);
}
