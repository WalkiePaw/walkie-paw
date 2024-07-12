package com.WalkiePaw.domain.member.Repository;

import com.WalkiePaw.domain.member.entity.Member;
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
public class JpaMemberRepositoryImpl {

    @PersistenceContext
    private final EntityManager em;

    public Member save(final Member member) {
        em.persist(member);
        return member;
    }

    public Optional<Member> findById(final Integer memberId) {
        return Optional.ofNullable(em.find(Member.class, memberId));
    }

    public List<Member> findByName(final String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

//    @Override
    public Optional<Member> findByEmail(final String email) {
        return Optional.of(em.createQuery("select m from Member m where m.email = :email", Member.class)
                .setParameter("email", email)
                .getSingleResult());
    }

    //    public void delete(final Integer memberId) {
//        em.remove(em.find(Member.class, memberId));
//    }
}
