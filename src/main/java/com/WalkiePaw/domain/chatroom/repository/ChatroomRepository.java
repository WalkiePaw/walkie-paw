package com.WalkiePaw.domain.chatroom.repository;

import com.WalkiePaw.domain.chatroom.entity.Chatroom;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Profile("spring-data-jpa")
public interface ChatroomRepository extends JpaRepository<Chatroom, Integer>, ChatroomRepositoryOverride {

    List<Chatroom> findByMemberId(final Integer memberId);

    @EntityGraph(attributePaths = {"board"})
    Optional<Chatroom> findChatroomAndBoardById(Integer chatroomId);

}
