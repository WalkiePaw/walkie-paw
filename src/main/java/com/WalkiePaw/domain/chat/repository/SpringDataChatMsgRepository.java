package com.WalkiePaw.domain.chat.repository;

import com.WalkiePaw.domain.chat.entity.ChatMessage;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Profile("spring-data-jpa")
public interface SpringDataChatMsgRepository extends JpaRepository<ChatMessage, Integer>, ChatMsgRepository{

    @Override
    @Modifying(clearAutomatically = true)
    @Query("update ChatMessage cm set cm.isRead = true where cm.chatroom.id = :chatroomId")
    void bulkIsRead(Integer chatroomId);

}
