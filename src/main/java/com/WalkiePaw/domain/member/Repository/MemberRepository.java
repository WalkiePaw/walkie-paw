package com.WalkiePaw.domain.member.Repository;

import com.WalkiePaw.domain.member.entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    @PersistenceContext
    private final EntityManager em;

    public Integer save(final Member member) {
        em.persist(member);
        return member.getId();
    }

    public Member findById(final Integer memberId) {
        return em.find(Member.class, memberId);
    }

    public List<Member> findByName(final String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public void update(final Integer memberId, final Member member) {

    }

    public void delete(final Integer memberId) {
        em.remove(em.find(Member.class, memberId));
    }
}
