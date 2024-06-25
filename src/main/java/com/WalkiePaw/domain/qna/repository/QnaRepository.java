package com.WalkiePaw.domain.qna.repository;

import com.WalkiePaw.domain.qna.entity.Qna;
import com.WalkiePaw.presentation.domain.qna.dto.QnaRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QnaRepository {

    @PersistenceContext
    private final EntityManager em;

    public Integer save(final Qna qna) {
        em.persist(qna);
        return qna.getId();
    }

    public List<Qna> findAll() {
        return em.createQuery("select qna from Qna qna join fetch qna.member m", Qna.class)
                .getResultList();
    }
    public Qna findById(final Integer qnaId) {
        return em.find(Qna.class, qnaId);
    }

    public void update(final Integer qnaId, final QnaRequest request) {
        Qna qna = em.find(Qna.class, qnaId);
    }
}
