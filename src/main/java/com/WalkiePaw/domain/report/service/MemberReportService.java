package com.WalkiePaw.domain.report.service;

import com.WalkiePaw.domain.member.Repository.MemberRepository;
import com.WalkiePaw.domain.member.entity.Member;
import com.WalkiePaw.domain.report.entity.MemberReport;
import com.WalkiePaw.domain.report.repository.MemberReportRepository;
import com.WalkiePaw.presentation.domain.report.memberReportDto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberReportService {

    private final MemberReportRepository memberReportRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public MemberReportGetResponse findById(final Integer memberReportId) {
        return MemberReportGetResponse.from(memberReportRepository.findById(memberReportId).orElseThrow());
    }

    @Transactional(readOnly = true)
    public List<MemberReportListResponse> findAll() {
        return memberReportRepository.findAll().stream()
                .map(MemberReportListResponse::from)
                .toList();
    }

    public Integer save(final MemberReportAddRequest request) {
        Member reportingMember = memberRepository.findById(request.getReportedMemberId()).orElseThrow();
        Member reportedMember = memberRepository.findById(request.getReportedMemberId()).orElseThrow();
        return memberReportRepository.save(request.toEntity(reportingMember, reportedMember)).getId();
    }

    public void update(final Integer memberReportId, final MemberReportUpdateRequest request) {
        Member reportingMember = memberRepository.findById(request.getReportingMemberId()).orElseThrow();
        Member reportedMember = memberRepository.findById(request.getReportedMemberId()).orElseThrow();
        MemberReport memberReport = memberReportRepository.findById(memberReportId).orElseThrow();
        memberReport.update(request, reportingMember, reportedMember);
    }
}
