package com.WalkiePaw.presentation.domain.qna;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/qna")
public class QnaController {

    private static final String QNA_URL = "/qna/";

    @GetMapping
    public ResponseEntity<List<QnaResponse>> getQnas() {
        List<QnaResponse> qnaResponses = null;
        return ResponseEntity.ok()
                .body(qnaResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QnaResponse> getQna(@PathVariable Long id) {
        QnaResponse qnaResponse = null;
        return ResponseEntity.ok()
                .body(qnaResponse);
    }

    @PostMapping
    public ResponseEntity<QnaResponse> createQna(@Validated @RequestBody QnaRequest qnaRequest) {
        /**
         * QnaRequest를 저장하는 서비스
         */
        return ResponseEntity.created(URI.create(QNA_URL)/* + qnaId*/).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<QnaResponse> deleteQna(@PathVariable Long id) {
        /**
         * id에 해당하는 데이터를 삭제하는 서비스
         */
        return ResponseEntity.noContent().build();
    }

    @PatchMapping
    public ResponseEntity<QnaResponse> updateQna(@Validated @RequestBody QnaRequest qnaRequest) {
        /**
         * qnaRequest로 수정하는 서비스
         */
        return ResponseEntity.noContent().build();
    }

}
