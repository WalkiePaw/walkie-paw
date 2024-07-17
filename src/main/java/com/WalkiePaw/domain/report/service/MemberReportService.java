package com.WalkiePaw.domain.report.service;

import com.WalkiePaw.domain.member.Repository.MemberRepository;
import com.WalkiePaw.domain.member.entity.Member;
import com.WalkiePaw.domain.report.entity.MemberReport;
import com.WalkiePaw.domain.report.repository.MemberReport.MemberReportRepository;
import com.WalkiePaw.domain.report.repository.MemberReport.MemberReportRepositoryOverride;
import com.WalkiePaw.global.exception.BadRequestException;
import com.WalkiePaw.presentation.domain.report.memberReportDto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.WalkiePaw.global.exception.ExceptionCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberReportService {

    private final MemberReportRepository memberReportRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public MemberReportGetResponse findById(final Integer memberReportId) {
        return MemberReportGetResponse.from(memberReportRepository.findById(memberReportId).orElseThrow(
                () -> new BadRequestException(NOT_FOUND_MEMBER_REPORT_ID)
        ));
    }

    @Transactional(readOnly = true)
    public List<MemberReportListResponse> findAll() {
        return memberReportRepository.findAll().stream()
                .map(MemberReportListResponse::from)
                .toList();
    }

    public Integer save(final MemberReportAddRequest request) {
        Member reportingMember = memberRepository.findById(request.getReportedMemberId()).orElseThrow(
                () -> new BadRequestException(NOT_FOUND_MEMBER_ID)
        );
        Member reportedMember = memberRepository.findById(request.getReportedMemberId()).orElseThrow(
                () -> new BadRequestException(NOT_FOUND_MEMBER_ID)
        );
        return memberReportRepository.save(request.toEntity(reportingMember, reportedMember)).getId();
    }

    public void update(final Integer memberReportId, final MemberReportUpdateRequest request) {
        Member reportingMember = memberRepository.findById(request.getReportingMemberId()).orElseThrow(
                () -> new BadRequestException(NOT_FOUND_MEMBER_ID)
        );
        Member reportedMember = memberRepository.findById(request.getReportedMemberId()).orElseThrow(
                () -> new BadRequestException(NOT_FOUND_MEMBER_ID)
        );
        MemberReport memberReport = memberReportRepository.findById(memberReportId).orElseThrow(
                () -> new BadRequestException(NOT_FOUND_MEMBER_REPORT_ID)
        );
        memberReport.update(request, reportingMember, reportedMember);
    }

    public void ban(final Integer memberReportId) {
        MemberReport memberReport = memberReportRepository.findById(memberReportId).orElseThrow(
                () -> new BadRequestException(NOT_FOUND_MEMBER_REPORT_ID)
        );
        memberReport.getReportedMember().ban();
        memberReport.ban();
    }

    public void ignore(final Integer memberReportId) {
        MemberReport memberReport = memberReportRepository.findById(memberReportId).orElseThrow(
                () -> new BadRequestException(NOT_FOUND_MEMBER_REPORT_ID)
        );
        memberReport.ignore();
    }

    public Page<MemberReportListResponse> findAllByCond(final String status, Pageable pageable) {
        return memberReportRepository.findAllByCond(status, pageable);
    }
}
