package com.WalkiePaw.domain.qna.service;

import com.WalkiePaw.domain.qna.repository.QnaRepository;
import com.WalkiePaw.presentation.domain.qna.dto.QnaRequest;
import com.WalkiePaw.presentation.domain.qna.dto.QnaResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QnaService {

    private QnaRepository qnaRepository;

    public List<QnaResponse> findAll() {
        return qnaRepository.findAll().stream()
                .map(QnaResponse::from)
                .collect(Collectors.toList());
    }

    public QnaResponse findById(Integer qnaId) {
        return QnaResponse.from(qnaRepository.findById(qnaId));
    }

    public Integer save(QnaRequest request) {
        return qnaRepository.save(request.toEntity());
    }

    public void update(Integer qnaId, QnaRequest request) {
        qnaRepository.update(qnaId, request);
    }
}
