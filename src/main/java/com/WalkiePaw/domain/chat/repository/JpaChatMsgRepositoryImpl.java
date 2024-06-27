package com.WalkiePaw.domain.chat.repository;

import com.WalkiePaw.domain.chat.entity.ChatMessage;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
@Profile("jpa")
public class JpaChatMsgRepositoryImpl implements ChatMsgRepository {
    private final EntityManager em;



//    public ChatMessage findOne(Integer chatMsgId) {
//        return em.find(ChatMessage.class, chatMsgId);
//    }
//
    @Override
    public List<ChatMessage> findByChatroomId(final Integer chatroomId) {
        return em.createQuery("select cm from ChatMessage cm where cm.chatroom.id = :chatroomId", ChatMessage.class)
                .setParameter("chatroomId", chatroomId)
                .getResultList();
    }

    @Override
    public ChatMessage save(final ChatMessage message) {
        return null;
    }
}
