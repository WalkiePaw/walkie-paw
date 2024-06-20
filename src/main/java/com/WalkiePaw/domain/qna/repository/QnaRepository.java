package com.WalkiePaw.domain.qna.repository;

import com.WalkiePaw.domain.qna.entity.Qna;
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

    public void save(Qna qna) {
        em.persist(qna);
    }

    public List<Qna> findAll() {
        return em.createQuery("select qna from Qna qna", Qna.class)
                .getResultList();
    }
}
