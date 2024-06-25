package com.WalkiePaw.domain.report.service;

import com.WalkiePaw.domain.member.Repository.MemberRepository;
import com.WalkiePaw.domain.member.entity.Member;
import com.WalkiePaw.domain.report.repository.MemberReportRepository;
import com.WalkiePaw.presentation.domain.report.memberReportDto.MemberReportRequest;
import com.WalkiePaw.presentation.domain.report.memberReportDto.MemberReportResponse;
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
    public MemberReportResponse findById(final Integer memberReportId) {
        return MemberReportResponse.from(memberReportRepository.findById(memberReportId));
    }
    @Transactional(readOnly = true)
    public List<MemberReportResponse> findAll() {
        return memberReportRepository.findAll().stream()
                .map(MemberReportResponse::from)
                .toList();
    }

    public Integer save(final MemberReportRequest request) {
        Member reportingMember = memberRepository.findById(request.getReportedMemberId());
        Member reportedMember = memberRepository.findById(request.getReportedMemberId());
        return memberReportRepository.save(request.toEntity(reportingMember, reportedMember));
    }

    public void update(final Integer memberReportId, final MemberReportRequest request) {
        memberReportRepository.update(memberReportId, request);
    }
}
