package com.WalkiePaw.presentation.domain.qna;

import com.WalkiePaw.domain.qna.repository.QnaRepository;
import com.WalkiePaw.domain.qna.service.QnaService;
import com.WalkiePaw.presentation.domain.qna.dto.QnaRequest;
import com.WalkiePaw.presentation.domain.qna.dto.QnaResponse;
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
    public ResponseEntity<List<QnaResponse>> qnaList() {
        List<QnaResponse> responses = qnaService.findAll();
        return ResponseEntity.ok()
                .body(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QnaResponse> getQna(@PathVariable("id") Integer qnaId) {
        QnaResponse qnaResponse = qnaService.findById(qnaId);
        return ResponseEntity.ok()
                .body(qnaResponse);
    }

    @PostMapping
    public ResponseEntity<QnaResponse> addQna(@Validated @RequestBody QnaRequest request) {
        Integer qnaId = qnaService.save(request);
        return ResponseEntity.created(URI.create(QNA_URL + qnaId)).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<QnaResponse> updateQna(@PathVariable("id") Integer qnaId, @Validated @RequestBody QnaRequest request) {
        qnaService.update(qnaId, request);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<QnaResponse> deleteQna(@PathVariable Integer id) {
        /**
         * id에 해당하는 데이터를 삭제하는 서비스
         */
        return ResponseEntity.noContent().build();
    }

}
