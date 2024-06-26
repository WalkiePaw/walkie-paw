package com.WalkiePaw.domain.chatroom.repository;

import com.WalkiePaw.domain.chatroom.entity.Chatroom;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@Profile("spring-data-jpa")
public interface SpringDataChatroomRepository extends JpaRepository<Chatroom, Integer>, ChatroomRepository {
}
