package com.WalkiePaw.domain.qna.repository;

import com.WalkiePaw.domain.qna.entity.Qna;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

@Profile("spring-data-jpa")
public interface SpringDataQnaRepository extends JpaRepository<Qna, Integer>, QnaRepository {
}
