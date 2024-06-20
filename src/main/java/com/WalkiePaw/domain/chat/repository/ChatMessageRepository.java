package com.WalkiePaw.domain.chat.repository;

import com.WalkiePaw.domain.chat.chatV2.entity.ChatMessage;
import com.WalkiePaw.domain.chatroom.entity.Chatroom;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ChatMessageRepository {
    private final EntityManager em;

    public void save(ChatMessage chatMessage) {
        em.persist(chatMessage);
    }

    public ChatMessage findOne(Integer chatmsgId) {
        return em.find(ChatMessage.class, chatmsgId);
    }

    public List<ChatMessage> findAll() {
        return em.createQuery("select cm from ChatMessage cm", ChatMessage.class)
                .getResultList();
    }
}
