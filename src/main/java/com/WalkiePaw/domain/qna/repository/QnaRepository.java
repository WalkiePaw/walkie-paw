package com.WalkiePaw.domain.qna.repository;

import com.WalkiePaw.domain.qna.entity.Qna;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QnaRepository {

    Qna save(final Qna qna);

    List<Qna> findAll();

    Optional<Qna> findById(final Integer qnaId);
}
