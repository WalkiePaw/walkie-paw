package com.WalkiePaw.domain.chatroom;

import com.WalkiePaw.domain.chatroom.entity.Chatroom;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ChatroomRepository {

    private final EntityManager em;


    public void save(Chatroom chatroom) {
        em.persist(chatroom);
    }

    public Chatroom findOne(Integer chatroomId) {
        return em.find(Chatroom.class, chatroomId);
    }

    public List<Chatroom> findAll() {
        return em.createQuery("select cr from Chatroom cr", Chatroom.class)
                .getResultList();
    }
}
