package com.WalkiePaw.domain.chatroom.repository;

import com.WalkiePaw.domain.chatroom.entity.Chatroom;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ChatroomRepository {

    private final EntityManager em;


    public Integer save(final Chatroom chatroom) {
        em.persist(chatroom);
        return chatroom.getId();
    }

    public Optional<Chatroom> findById(final Integer chatroomId) {
        return Optional.ofNullable(em.find(Chatroom.class, chatroomId));
    }

    public Optional<Chatroom> findChatroomWithMemberAndBoardById(final Integer chatroomId) {
        return (Optional<Chatroom>) em.createQuery("select cr from Chatroom cr" +
                        " join fetch cr.member m" +
                        " join fetch cr.board b" +
                        " where cr.id = :id")
                .setParameter("id", chatroomId)
                .getSingleResult();
    }

    public List<Chatroom> findAll() {
        return em.createQuery("select cr from Chatroom cr", Chatroom.class)
                .getResultList();
    }
}
