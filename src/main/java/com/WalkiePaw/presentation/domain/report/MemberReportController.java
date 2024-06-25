package com.WalkiePaw.presentation.domain.report;

import com.WalkiePaw.domain.report.service.MemberReportService;
import com.WalkiePaw.presentation.domain.report.memberReportDto.MemberReportRequest;
import com.WalkiePaw.presentation.domain.report.memberReportDto.MemberReportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/memberReports")
@RequiredArgsConstructor
public class MemberReportController {

    private final MemberReportService memberReportService;
    private static final String MEMBER_REPORT_URL = "/memberReports/";

    @GetMapping
    public ResponseEntity<List<MemberReportResponse>> getMemberReports() {
        List<MemberReportResponse> responses = memberReportService.findAll();
        return ResponseEntity.ok()
                .body(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberReportResponse> getMemberReport(final @PathVariable("id") Integer memberReportId) {
        MemberReportResponse response = memberReportService.findById(memberReportId);
        return ResponseEntity.ok()
                .body(response);
    }

    @PostMapping
    ResponseEntity<MemberReportResponse> addMemberReport(final @Validated @RequestBody MemberReportRequest request) {
        Integer memberReportId = memberReportService.save(request);
        return ResponseEntity.created(URI.create(MEMBER_REPORT_URL + memberReportId)).build();
    }

    @PatchMapping("/{id}")
    ResponseEntity<MemberReportResponse> updateMemberReport(final @PathVariable("id") Integer memberReportId, final @Validated @RequestBody MemberReportRequest request) {
        memberReportService.update(memberReportId, request);
        return ResponseEntity.noContent().build();
    }

    ResponseEntity<MemberReportResponse> deleteMemberReport(final @PathVariable("id") Integer memberReportId) {
        /**
         * id에 해당하는 데이터를 삭제하는 서비스
         */
        return ResponseEntity.noContent().build();
    }

}
