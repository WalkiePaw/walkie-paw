package com.WalkiePaw.domain.chatroom.service;

import com.WalkiePaw.domain.board.entity.Board;
import com.WalkiePaw.domain.board.repository.BoardRepository;
import com.WalkiePaw.domain.chatroom.entity.Chatroom;
import com.WalkiePaw.domain.chatroom.entity.ChatroomCategory;
import com.WalkiePaw.domain.chatroom.repository.ChatroomRepository;
import com.WalkiePaw.domain.member.Repository.MemberRepository;
import com.WalkiePaw.domain.member.entity.Member;
import com.WalkiePaw.presentation.domain.chatroom.dto.ChatroomListResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ChatroomServiceTest {

    @Autowired
    private ChatroomRepository chatroomRepository;
    @PersistenceContext
    EntityManager em;

//    @Test
//    void 채팅방_리스트_테스트() {
//        Member member1 = Member.builder().build();
//        Member member2 = Member.builder().build();
//        em.persist(member1);
//        em.persist(member2);
//
//        Board board = Board.builder().member(member1).build();
//        em.persist(board);
//
//        Chatroom chatroom = new Chatroom(board, member2);
//        em.persist(chatroom);
//
//        System.out.println("chatroom.getModifiedDate() = " + chatroom.getModifiedDate());
//        em.flush();
//        em.clear();
//
//        List<ChatroomListResponse> byMemberId = chatroomRepository.findByMemberId(member1.getId());
//
//        System.out.println("byMemberId = " + byMemberId);
//    }
}