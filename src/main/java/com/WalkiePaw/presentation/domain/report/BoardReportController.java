package com.WalkiePaw.presentation.domain.report;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/boardReports")
public class BoardReportController {

    private static final String BOARD_REPORT_URL = "/boardReports/";

    @GetMapping
    public ResponseEntity<List<BoardReportResponse>> getBoardReports() {
        List<BoardReportResponse> responses = null;
        return ResponseEntity.ok()
                .body(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardReportResponse> getBoardReport(@PathVariable Long id) {
        BoardReportResponse response = null;
        return ResponseEntity.ok()
                .body(response);
    }

    @PostMapping
    public ResponseEntity<BoardReportResponse> createBoardReport(@Validated @RequestBody BoardReportRequest request) {
        /**
         * boardReportRequest를 저장하는 서비스
         */
        return ResponseEntity.created(URI.create(BOARD_REPORT_URL/* + boardReportId*/)).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BoardReportResponse> deleteBoardReport(@PathVariable Long id) {
        /**
         * id에 해당하는 데이터를 삭제하는 서비스
         */
        return ResponseEntity.noContent().build();
    }

    @PatchMapping
    public ResponseEntity<BoardReportResponse> updateBoardReport(@Validated @RequestBody BoardReportRequest request) {
        /**
         * boardReportRequest로 수정하는 서비스
         */
        return ResponseEntity.noContent().build();
    }
}
