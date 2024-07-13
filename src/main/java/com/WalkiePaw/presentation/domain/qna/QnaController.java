package com.WalkiePaw.presentation.domain.qna;

import com.WalkiePaw.domain.qna.service.QnaService;
import com.WalkiePaw.presentation.domain.qna.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/qna")
@RequiredArgsConstructor
public class QnaController {

    private final QnaService qnaService;
    private static final String QNA_URL = "/qna/";

    @GetMapping
    public ResponseEntity<List<QnaListResponse>> qnaList() {
        List<QnaListResponse> responses = qnaService.findAll();
        return ResponseEntity.ok()
                .body(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QnaGetResponse> getQna(@PathVariable("id") final Integer qnaId) {
        QnaGetResponse qnaGetResponse = qnaService.findById(qnaId);
        return ResponseEntity.ok()
                .body(qnaGetResponse);
    }

    @PostMapping
    public ResponseEntity<Void> addQna(@Validated @RequestBody final QnaAddRequest request) {
        Integer qnaId = qnaService.save(request);
        return ResponseEntity.created(URI.create(QNA_URL + qnaId)).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateQna(@PathVariable("id") final Integer qnaId, @Validated @RequestBody final QnaUpdateRequest request) {
        qnaService.update(qnaId, request);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/reply")
    public ResponseEntity<Void> replyQna(@PathVariable("id") final Integer qnaId, @Validated @RequestBody final replyUpdateRequest request) {
        qnaService.updateReply(qnaId, request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<QnaListResponse>> list(
            @RequestParam(required = false) final String status // RESOLVED, UNRESOLVED
    ) {
        List<QnaListResponse> list = qnaService.findAllByCond(status);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}/list")
    public ResponseEntity<List<QnaListResponse>> mypageList(@PathVariable("id") final Integer memberId) {
        List<QnaListResponse> list = qnaService.findMyQnaByMemberId(memberId);
        return ResponseEntity.ok(list);
    }

}
