package com.WalkiePaw.domain.chatroom.repository;

import com.WalkiePaw.domain.chatroom.entity.Chatroom;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

@Profile("spring-data-jpa")
public interface ChatroomRepository extends JpaRepository<Chatroom, Integer>, ChatroomRepositoryOverride {
}
