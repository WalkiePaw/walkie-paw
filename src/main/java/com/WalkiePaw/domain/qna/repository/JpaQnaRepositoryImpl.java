package com.WalkiePaw.domain.qna.repository;

import com.WalkiePaw.domain.qna.entity.Qna;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
@Profile("jpa")
public class JpaQnaRepositoryImpl implements QnaRepository {

    @PersistenceContext
    private final EntityManager em;

    public Qna save(final Qna qna) {
        em.persist(qna);
        return qna;
    }

    public List<Qna> findAll() {
        return em.createQuery("select qna from Qna qna join fetch qna.member m", Qna.class)
                .getResultList();
    }
    public Optional<Qna> findById(final Integer qnaId) {
        return Optional.ofNullable(em.find(Qna.class, qnaId));
    }
}
