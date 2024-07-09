package com.WalkiePaw.domain.qna.repository;

import com.WalkiePaw.domain.qna.entity.Qna;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Profile("spring-data-jpa")
public interface QnaRepository extends JpaRepository<Qna, Integer>, QnaRepositoryOverride {

    @Override
    @EntityGraph(attributePaths = "member")
    Optional<Qna> findById(final Integer qnaId);

    @Override
    @EntityGraph(attributePaths = "member")
    List<Qna> findAll();
}
