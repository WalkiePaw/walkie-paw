package com.WalkiePaw.domain.chat.repository;

import com.WalkiePaw.domain.chat.entity.ChatMessage;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

@Profile("spring-data-jpa")
public interface SpringDataChatMsgRepository extends JpaRepository<ChatMessage, Integer>, ChatMsgRepository{
}
