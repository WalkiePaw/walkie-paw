package com.WalkiePaw.domain.chatroom.repository;

import com.WalkiePaw.domain.chatroom.entity.Chatroom;
import jakarta.persistence.EntityManager;
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
public class JpaChatroomRepositoryImpl implements ChatroomRepository{

    private final EntityManager em;

    public Chatroom save(final Chatroom chatroom) {
        em.persist(chatroom);
        return chatroom;
    }

    @Override
    public Optional<Chatroom> findById(final Integer chatroomId) {
        return Optional.ofNullable(em.find(Chatroom.class, chatroomId));
    }

    @Override
    public Optional<Chatroom> findChatroomById(final Integer chatroomId) {
        Chatroom chatroom  = (Chatroom) em.createQuery("select cr from Chatroom cr" +
                        " join fetch cr.member m" +
                        " join fetch cr.board b" +
                        " where cr.id = :id")
                .setParameter("id", chatroomId)
                .getSingleResult();
        return Optional.ofNullable(chatroom);
    }

    @Override
    public List<Chatroom> findAllByMemberId(Integer memberId) {
        return em.createQuery("select cr from Chatroom cr", Chatroom.class)
                .getResultList();
    }
}
