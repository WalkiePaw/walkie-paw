package com.WalkiePaw.presentation.domain.report;

import com.WalkiePaw.domain.report.service.BoardReportService;
import com.WalkiePaw.presentation.domain.report.boardReportDto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/boardReports")
@RequiredArgsConstructor
public class BoardReportController {

    private final BoardReportService boardReportService;
    private static final String BOARD_REPORT_URL = "/boardReports/";

    @GetMapping
    public ResponseEntity<List<BoardReportListResponse>> boardReportList() {
        List<BoardReportListResponse> responses = boardReportService.findAll();
        return ResponseEntity.ok()
                .body(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardReportGetResponse> getBoardReport(final @PathVariable("id") Integer boardReportId) {
        BoardReportGetResponse response = boardReportService.findById(boardReportId);
        return ResponseEntity.ok()
                .body(response);
    }

    @PostMapping
    public ResponseEntity<Void> addBoardReport(final @Validated @RequestBody BoardReportAddRequest request) {
        System.out.println("request = " + request);
        Integer boardReportId = boardReportService.save(request);
        return ResponseEntity.created(URI.create(BOARD_REPORT_URL + boardReportId)).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateBoardReport(final @PathVariable("id") Integer boardReportId, final @Validated @RequestBody BoardReportUpdateRequest request) {
        /**
         * boardReportRequest로 수정하는 서비스
         */
        boardReportService.update(boardReportId, request);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Void> deleteBoardReport(@PathVariable Integer boardReportId) {
        /**
         * id에 해당하는 데이터를 삭제하는 서비스
         */
        return ResponseEntity.noContent().build();
    }
}
