package com.WalkiePaw.domain.report.repository.MemberReport;

import com.WalkiePaw.domain.report.entity.MemberReport;
import com.WalkiePaw.presentation.domain.report.memberReportDto.MemberReportListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberReportRepositoryOverride {

    Page<MemberReportListResponse> findAllByCond(String status, Pageable pageable);
}
