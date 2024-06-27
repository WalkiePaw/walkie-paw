package com.WalkiePaw.presentation.domain.qna;

import com.WalkiePaw.domain.qna.repository.QnaRepository;
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
    public ResponseEntity<QnaGetResponse> getQna(@PathVariable("id") Integer qnaId) {
        QnaGetResponse qnaGetResponse = qnaService.findById(qnaId);
        return ResponseEntity.ok()
                .body(qnaGetResponse);
    }

    @PostMapping
    public ResponseEntity<Void> addQna(@Validated @RequestBody QnaAddRequest request) {
        Integer qnaId = qnaService.save(request);
        return ResponseEntity.created(URI.create(QNA_URL + qnaId)).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateQna(@PathVariable("id") Integer qnaId, @Validated @RequestBody QnaUpdateRequest request) {
        qnaService.update(qnaId, request);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Void> deleteQna(@PathVariable Integer id) {
        /**
         * id에 해당하는 데이터를 삭제하는 서비스
         */
        return ResponseEntity.noContent().build();
    }

}
