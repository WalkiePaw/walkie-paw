package com.WalkiePaw.domain.chatroom.repository;

import com.WalkiePaw.domain.chatroom.entity.Chatroom;

import java.util.List;
import java.util.Optional;

public interface ChatroomRepository {
    Chatroom save(Chatroom chatroom);

    Optional<Chatroom> findById(Integer chatroomId);

    Optional<Chatroom> findChatroomById(Integer chatroomId);

    List<Chatroom> findAllByMemberId(Integer memberId);
}
