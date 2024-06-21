package com.WalkiePaw.presentation.domain.report;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/memberReports")
public class MemberReportController {

    private static final String MEMBER_REPORT_URL = "/memberReports/";

    @GetMapping
    public ResponseEntity<List<MemberReportResponse>> getMemberReports() {
        List<MemberReportResponse> responses = null;
        return ResponseEntity.ok()
                .body(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberReportResponse> getMemberReport(@PathVariable Long id) {
        MemberReportResponse response = null;
        return ResponseEntity.ok()
                .body(response);
    }

    @PostMapping
    ResponseEntity<MemberReportResponse> createMemberReport(@Validated @RequestBody MemberReportRequest request) {
        /**
         * request를 저장하는 서비스
         */
        return ResponseEntity.created(URI.create(MEMBER_REPORT_URL/* + memberReportId*/)).build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<MemberReportResponse> deleteMemberReport(@PathVariable Long id) {
        /**
         * id에 해당하는 데이터를 삭제하는 서비스
         */
        return ResponseEntity.noContent().build();
    }

    @PatchMapping
    ResponseEntity<MemberReportResponse> updateMemberReport(@Validated @RequestBody MemberReportRequest request) {
        /**
         * request로 수정하는 서비스
         */
        return ResponseEntity.noContent().build();
    }

}
