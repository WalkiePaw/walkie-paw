package com.WalkiePaw.domain.qna.repository;

import com.WalkiePaw.domain.qna.entity.Qna;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Profile("spring-data-jpa")
public interface SpringDataQnaRepository extends JpaRepository<Qna, Integer>, QnaRepository {

    @Override
    @EntityGraph(attributePaths = "member")
    Optional<Qna> findById(@Param("id") final Integer qnaId);

    @Override
    @EntityGraph(attributePaths = "member")
    List<Qna> findAll();
}
