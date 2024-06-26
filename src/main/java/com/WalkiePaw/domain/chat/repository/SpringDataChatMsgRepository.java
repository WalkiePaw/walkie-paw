package com.WalkiePaw.domain.chat.repository;

import com.WalkiePaw.domain.chat.chatV1.entity.ChatMessage;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@Profile("spring-data-jpa")
public interface SpringDataChatMsgRepository extends JpaRepository<ChatMessage, Integer>, ChatMsgRepository{
}
